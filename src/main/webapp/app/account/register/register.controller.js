'use strict';

angular.module('nfseApp')
    .controller('RegisterController', function ($scope, $translate, $timeout,
                                                Auth, Notificacao, $state, Register, CEP, Pessoa,
                                                TermoUso, $sanitize) {

        $scope.usuario = {};
        $scope.usuario.dadosPessoais = {};
        $scope.myCroppedImage = "";
        $scope.passo = 1;

        $scope.termo;
        $scope.aceito = false;

        $timeout(function () {
            angular.element('[ng-model="usuario.login"]').focus();
        });

        $scope.register = function () {
            $scope.usuario.login = $scope.usuario.dadosPessoais.cpfCnpj;
            if ($scope.myImage && $scope.myCroppedImage) {
                $scope.usuario.imagem = $scope.myCroppedImage;
            }
            Auth.createAccount($scope.usuario).then(function () {
                Notificacao.priority('Cadastro salvo com sucesso! Você já pode efetuar o login.');
                $state.go('login');
            });
        };

        $scope.loadEnderecoByCEP = function (cep) {
            if (cep) {
                CEP.getByCep({cep: cep}, function (data) {
                    $scope.usuario.dadosPessoais.cep = data.cep;
                    $scope.usuario.dadosPessoais.bairro = data.bairro;
                    $scope.usuario.dadosPessoais.logradouro = data.logradouro;
                    $scope.usuario.dadosPessoais.complemento = data.complemento;
                    $scope.usuario.dadosPessoais.municipio = data.localidade;
                    $scope.usuario.dadosPessoais.uf = data.uf;
                });
            }
        };

        $scope.loadByCpfCnpj = function () {
            Pessoa.getPorCpfCnpj(
                {cpfCnpj: $scope.usuario.dadosPessoais.cpfCnpj}, function (result) {
                    if (result.cpfCnpj) {
                        $scope.usuario.dadosPessoais = result;
                    }
                });
        };

        $scope.upload = function (file) {
            var reader = new FileReader();
            reader.onload = function (evt) {
                $scope.$apply(function ($scope) {
                    $scope.myImage = evt.target.result;
                });
            };
            reader.readAsDataURL(file);
        }

        $scope.trustAsHtml = function (value) {
            return $sanitize(value);
        };

        $scope.voltar = function () {
            if ($scope.passo == 1) {
                $scope.go('login');
            } else {
                $scope.passo = $scope.passo - 1;
            }
        }

        $scope.continuar = function (form) {
            if (form.$invalid) {
                Notificacao.error('Erro', 'Por favor preencha todos os campos obrigatórios.');
            } else {
                if ($scope.passo == 2) {
                    if (!$scope.aceito) {
                        Notificacao.error('Erro', 'O aceite do termo de uso é obrigatório para se registrar.');
                    } else {
                        $scope.register();
                    }
                } else {
                    $scope.passo = $scope.passo + 1;
                }
            }
        }

        $scope.init = function () {
            TermoUso.termoUsoVigente(null, function (data) {
                $scope.termo = data;
            });
        }

        $scope.init();
    });
