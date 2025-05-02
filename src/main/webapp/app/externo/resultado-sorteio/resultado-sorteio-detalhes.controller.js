'use strict';

angular.module('nfseApp')
    .controller('ResultadoSorteioDetalhesController', function ($scope, $modalInstance, campanha,
                                                                PremioService) {
        $scope.campanha = campanha;

        $scope.premios = [];

        function buscarPremiosDaCampanha() {
            PremioService.buscarPremiosPorCampanha(
                {idCampanha: $scope.campanha.id}, function (result) {
                    $scope.premios = result;
                }
            );
        }

        buscarPremiosDaCampanha();

        $scope.ok = function (nota) {
            $modalInstance.close(nota);
        };

        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

    });
