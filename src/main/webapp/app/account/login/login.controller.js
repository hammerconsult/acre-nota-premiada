'use strict';

angular.module('nfseApp')
    .controller('LoginController', function ($rootScope, $scope, $state, $timeout, Auth, Account,
                                             PrestadorServicos, Principal, Idle, SweetAlert, User,
                                             Notificacao, TermoUso, NotaFiscal, CalendarioSorteioService,
                                             ComunicadoService, $modal, ConfiguracaoGovBrService) {
        $scope.user = {};
        $scope.errors = {};
        $scope.recaptcha = null;
        $scope.configuracaoGovBr;

        $scope.rememberMe = true;
        $timeout(function () {
            angular.element('[ng-model="username"]').focus();
            var heightBoxLogin = $('#box-login').height();
            if (heightBoxLogin > 0) {
                $('#primeiro-box').height(heightBoxLogin);
                $('#segundo-box').height(heightBoxLogin);
                $('#terceiro-box').height(heightBoxLogin);
                $('#box-info-sorteio').height(heightBoxLogin * 0.70);
                $('#box-peca-sua-nota').height(45);
                $('#box-valor-premio').height(heightBoxLogin - (heightBoxLogin * 0.70) - 45);
            }
        }, 1000);

        //manda p o home caso tente acessar o login e já está logado
        Principal.identity().then(function (account) {
            if (account) {
                //manda p o home caso tente acessar o login e já está logado
                $state.go('home', {reload: true});
            }
        });

        ConfiguracaoGovBrService.getConfiguracaoGovBr({}, function (data) {
            $scope.configuracaoGovBr = data;
        });

        Principal.config().then(function (configuracao) {
            $rootScope.configuracao = configuracao;
            $rootScope.textoTitulo = configuracao.tituloNotaPremiada;
            $rootScope.imagemNotaPremiada = configuracao.imagemNotaPremiada;
        });

        ComunicadoService.getUltimoComunicado({}, function (data) {
            if (data && data.id) {
                $modal.open({
                    templateUrl: 'app/entities/comunicado/comunicado-dialog.html',
                    controller: 'ComunicadoDialogController',
                    size: 'lg',
                    animation: true,
                    keyboard: false,
                    backdrop: 'static',
                    resolve: {
                        comunicado: function () {
                            return data;
                        }
                    }
                });
            }
        });

        CalendarioSorteioService.proximoSorteio({}, function (data) {
            $scope.proximoSorteio = data;
        });

        $scope.login = function (event) {
            // if (!$scope.recaptcha) {
            //     Notificacao.error('Erro', 'Captcha Inválido.');
            //     // return;
            // }
            event.preventDefault();
            Auth.login({
                username: $scope.username.replace(/\D/g, ""),
                password: $scope.password,
                rememberMe: $scope.rememberMe
            }).then(function () {
                $scope.authenticationError = false;
                $scope.$emit('nfseApp:navbarUpdate', event);
                Principal.identity().then(function (usuario) {
                    TermoUso.termoUsoParaAceite(usuario, function (data) {
                        if (data && data.value === true) {
                            $state.go('termoUso', {reload: true});
                        } else {
                            $state.go('home', {reload: true});
                        }
                    });

                    // start watching when the app runs. also starts the Keepalive service by default.
                    Idle.watch();
                    $rootScope.$on('IdleStart', function () {
                        SweetAlert.swal({
                                title: "Cadê você?",
                                text: "Já faz algum tempo que você não trabalha na aplicação, logo sua sessão irá expirar e será redirecionado para a tela de login",
                                type: "warning",
                                showCancelButton: false,
                                confirmButtonText: "Voltar!",
                                closeOnConfirm: true
                            },
                            function () {
                                Account.get();
                            });
                        // the user appears to have gone idle
                    });
                    $rootScope.$on('IdleTimeout', function () {
                        SweetAlert.swal("OPS!", "Sua sessão expirou, terá que fazer login novamente", "warning");
                        Auth.logout();
                        $state.go('login');
                        // the user has timed out (meaning idleDuration + timeout has passed without any activity)
                        // this is where you'd log them
                    });
                });
            }).catch(function (error) {
                var msg = [];
                if (!$scope.username) {
                    msg.push("Informe o CPF ou CNPJ");
                }
                if (!$scope.password) {
                    msg.push("Informe a senha");
                }
                if (error.data && error.data.error_description && error.data.error_description === 'UserNotActivated') {
                    msg.push(" Seu cadastro ainda não foi ativado. Por favor, verifique se recebeu um e-mail contendo instruções de ativação do cadastro. Caso contrário utilize a opção 'Esqueceu a senha?' para reenviar o e-mail de ativação do cadastro. Se mesmo assim não receber o e-mail verifique na sua caixa de Spam");
                }
                if (msg.length == 0) {
                    // User.acrescentarTentativaAcesso({login: $scope.username});
                    msg.push("Por favor verifique suas credenciais e tente novamente");
                }
                SweetAlert.swal({
                    title: "Problemas na autenticação?",
                    text: msg.join(", "),
                    type: "error",
                    showCancelButton: false,
                    confirmButtonText: "Voltar",
                    closeOnConfirm: true
                });
            });
        };
    });
