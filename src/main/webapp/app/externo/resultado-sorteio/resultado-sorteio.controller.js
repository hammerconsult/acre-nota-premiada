'use strict';

angular.module('nfseApp')
    .controller('ResultadoSorteioController', function ($scope, CalendarioSorteioService, $modal) {

        $scope.campanhas = [];
        $scope.premios = [];
        $scope.hoje = new Date();

        function buscarTodosSorteios() {
            CalendarioSorteioService.buscarSorteiosRealizados(function (data) {
                $scope.sorteios = data;
            });
        }

        buscarTodosSorteios();


        $scope.verDetalhes = function (campanha) {
            $modal.open({
                templateUrl: 'app/externo/resultado-sorteio/resultado-sorteio-detalhes.html',
                controller: 'ResultadoSorteioDetalhesController',
                size: 'lg',
                resolve: {
                    campanha: function () {
                        return campanha;
                    }
                }
            });

        };

        $scope.verCuponsUsuario = function (campanha) {
            $modal.open({
                templateUrl: 'app/externo/resultado-sorteio/resultado-sorteio-cupons-usuario.html',
                controller: 'ResultadoSorteioCuponsUsuarioController',
                size: 'lg',
                resolve: {
                    campanha: function () {
                        return campanha;
                    }
                }
            });
        };

        $scope.verBilhetesPremiados = function (sorteio) {
            $modal.open({
                templateUrl: 'app/externo/resultado-sorteio/resultado-sorteio-bilhetes-premiados.html',
                controller: 'ResultadoSorteioBilhetesPremiadosController',
                size: 'lg',
                resolve: {
                    sorteio: function () {
                        return sorteio;
                    }
                }
            });

        };
    });
