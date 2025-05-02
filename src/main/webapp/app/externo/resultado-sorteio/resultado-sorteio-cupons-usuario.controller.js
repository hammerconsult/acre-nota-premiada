'use strict';

angular.module('nfseApp')
    .controller('ResultadoSorteioCuponsUsuarioController',
        function ($scope, $modalInstance, campanha, CupomService, Principal) {

            $scope.campanha = campanha;

            $scope.cupons = [];

            $scope.account = {};

            function buscarUsuarioLogado() {
                Principal.identity().then(function (account) {
                    if (account) {
                        $scope.account = account;
                        buscarCupons();
                    }
                });
            }

            buscarUsuarioLogado();

            function buscarCupons() {
                CupomService.buscarCuponsUsuarioCampanha({
                        login: $scope.account.login,
                        idCampanha: $scope.campanha.id
                    }, function (result) {
                        $scope.cupons = result;
                    }
                );
            }

            $scope.ok = function (nota) {
                $modalInstance.close(nota);
            };

            $scope.cancel = function () {
                $modalInstance.dismiss('cancel');
            };

        });
