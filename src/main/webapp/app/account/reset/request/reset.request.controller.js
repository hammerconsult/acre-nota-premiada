'use strict';

angular.module('nfseApp')
    .controller('RequestResetController', function ($rootScope, $scope, $state, $timeout, Auth, SweetAlert, Notificacao, User, Configuracao) {


        $scope.resetAccount = {};

        $timeout(function () {
            angular.element('[ng-model="resetAccount.email"]').focus();
        });

        $scope.requestReset = function () {
            User.fromLogin({login: $scope.resetAccount.cpfCnpj}, function (data) {
                if (data && data.dadosPessoais.email) {
                    Notificacao.confirm('Atenção!', 'Tem certeza que deseja enviar os passos indicados para criar uma nova senha para o email ' +
                        data.dadosPessoais.email + '?', 'warning',
                        function () {
                            Auth.resetPasswordInit($scope.resetAccount.cpfCnpj)
                                .then(function (data) {
                                    $state.go("login");
                                    if (data.message) {
                                        SweetAlert.swal("Operação Realizada", data.message, "success");
                                    } else {
                                        SweetAlert.swal("Operação Realizada", "Verifique a caixa de entrada de seu email e siga os passos indicados para criar uma nova senha", "success");
                                    }
                                })
                                .catch(function (response) {
                                    console.log(response);
                                    SweetAlert.swal("Operação Não Realizada", response.data, "error");
                                });
                        })
                } else {
                    SweetAlert.swal("Operação Não Realizada", "Usuário não encontrado", "error");
                }
            });

        }
    });
