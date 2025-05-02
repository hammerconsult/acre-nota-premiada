'use strict';

angular.module('nfseApp')
    .controller('CalendarioSorteioController', function ($scope, $http, $sce, CalendarioSorteioService, ImpressaoPdf) {
        $scope.sorteios = [];
        $scope.hoje = new Date();

        function buscarTodosSorteios() {
            CalendarioSorteioService.buscarTodosSorteios(function (data) {
                $scope.sorteios = data;
            });
        }

        buscarTodosSorteios();

        $scope.downloadRelatorioBilhetesSorteio = function (id) {
            ImpressaoPdf.imprimirPdfViaUrl('/api/externo/relatorio-cupons-sorteio/' + id);
        }
    });

