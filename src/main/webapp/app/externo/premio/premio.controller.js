'use strict';

angular.module('nfseApp')
    .controller('NotaPremiadaController', function ($scope, PremioService, $modal, SweetAlert, Principal) {

        $scope.notas = [];
        $scope.premios = [];
        $scope.account = {};
        $scope.premiosPorSorteio = [];

        function buscarUsuarioLogado() {
            Principal.identity().then(function (account) {
                if (account) {
                    $scope.account = account;
                }
            });
        }

        buscarUsuarioLogado();

        function buscarTodosPremios() {
            PremioService.buscarTodosPremios(function (data) {
                $scope.premios = data;

                angular.forEach($scope.premios, function (premio) {
                    var indexSorteio = -1;
                    angular.forEach($scope.premiosPorSorteio, function (registro, index) {
                        if (registro.sorteio.id == premio.sorteio.id) {
                            indexSorteio = index;
                        }
                    });
                    if (indexSorteio > -1) {
                        $scope.premiosPorSorteio[indexSorteio].premios.push(premio);
                    } else {
                        $scope.premiosPorSorteio.push({
                            sorteio: premio.sorteio,
                            premios: [premio]
                        });

                    }
                });
            });

        }

        buscarTodosPremios();

    });
