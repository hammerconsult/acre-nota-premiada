(function () {
    'use strict';

    angular.module('nfseApp').controller('CupomDetalheController',
        function ($scope, $state, $stateParams, cupom, sorteio, $modalInstance) {
            $scope.cupom = cupom;
            $scope.sorteio = sorteio;
            $scope.fechar = function () {
                $modalInstance.dismiss('cancel');
            };
        });
})();
