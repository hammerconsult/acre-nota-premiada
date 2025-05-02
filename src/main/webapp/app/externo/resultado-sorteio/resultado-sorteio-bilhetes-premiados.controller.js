'use strict';

angular.module('nfseApp')
    .controller('ResultadoSorteioBilhetesPremiadosController',
        function ($scope, $modalInstance, sorteio, CupomService) {

            $scope.sorteio = sorteio;

            $scope.bilhetes = [];

            function buscarBilhetesPremiados() {
                CupomService.buscarBilhetesPremiadosSorteio({idSorteio: $scope.sorteio.id}, function (result) {
                        $scope.bilhetes = result;
                    }
                );
            }

            buscarBilhetesPremiados();

            $scope.ok = function (nota) {
                $modalInstance.close(nota);
            };

            $scope.cancel = function () {
                $modalInstance.dismiss('cancel');
            };

        });
