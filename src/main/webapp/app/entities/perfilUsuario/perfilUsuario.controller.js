'use strict';

angular.module('nfseApp')
    .controller('SettingsController', function ($state, $scope, $modal, $timeout,
                                                Principal, Account, CEP, Municipio, Notificacao,
                                                Pessoa, Util) {


        $scope.myImage = "";
        $scope.myCroppedImage = "";

        Principal.identity(true).then(function (data) {
            $scope.usuario = data;
            $scope.account = data;
            $scope.isAuthenticated = Principal.isAuthenticated;
            if ($scope.usuario.dadosPessoais.dataNascimento)
                $scope.usuario.dadosPessoais.dataNascimento = new Date($scope.usuario.dadosPessoais.dataNascimento);
            $scope.usuario.dadosPessoais.telefone = Util.apenasNumeros($scope.usuario.dadosPessoais.telefone);
            $scope.usuario.dadosPessoais.cep = Util.apenasNumeros($scope.usuario.dadosPessoais.cep);
        });

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

        $scope.save = function () {
            if ($scope.myCroppedImage && $scope.myImage) {
                $scope.usuario.imagem = $scope.myCroppedImage;
            }
            Account.save($scope.usuario, onSuccess);

            function onSuccess(data) {
                $scope.usuario = data;
                $state.reload();
                $scope.$emit('nfseApp:navbarUpdate');
                Notificacao.info("Informação", "Perfil salvo com sucesso!");
            }
        };

        $scope.inverterParticiparPrograma = function () {
            var text = "Deseja realmente " + ($scope.account.participandoPrograma ? "deixar o" : "aderir ao") + " programa da nota premiada??";

            Notificacao.confirm("Atenção", text, "warning",
                function () {
                    $scope.account.participandoPrograma = !$scope.account.participandoPrograma;
                    Account.save($scope.account, function () {
                        if($scope.account.participandoPrograma){
                            $state.go('termoUso');
                        }else{
                            Notificacao.info("Informação", "Você " +
                                (!$scope.account.participandoPrograma ? "deixou o" : "aderiu ao") + " programa da nota premiada.");
                        }

                    });
                });
        }
    });
