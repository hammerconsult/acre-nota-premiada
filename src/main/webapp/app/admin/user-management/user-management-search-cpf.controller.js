'use strict';

angular.module('nfseApp')
    .controller('UserManagementSearchCpfController', function ($scope, $modalInstance, User, ParseLinks,
                                                               Language, UserSearch, PrestadorServicos, Notificacao,
                                                               localStorageService) {
        $scope.users;
        $scope.authorities = ["ROLE_USER", "ROLE_ADMIN"];
        $scope.solicitacao = {dadosPessoais: {}};
        Language.getAll().then(function (languages) {
            $scope.languages = languages;
        });

        $scope.search = function () {
            console.error('buscar por cpf ' + $scope.searchQuery)
            UserSearch.getByCpf({query: $scope.searchQuery}, function (result) {
                $scope.users = result;
                console.error('result >> ' + result)
            }, function (response) {
                if (response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.ok = function (user) {
            $modalInstance.close(user);
        };

        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

        $scope.novo = function () {
            $scope.users = undefined;
            $scope.searchQuery = '';
        };

        $scope.emailSolicitandoCadastro = function () {
            $scope.solicitacao.prestadorServico = localStorageService.get("prestadorPrincipal").prestador;
            console.log('solicitacao', $scope.solicitacao);
            PrestadorServicos.enviarEmailSolicitandoCadastro($scope.solicitacao, onSuccessEmailSolicitandoCadastro);
        };

        function onSuccessEmailSolicitandoCadastro() {
            Notificacao.info("Informação", "E-mail enviado com sucesso.");
            $scope.cancel();
        }
    });
