'use strict';

angular.module('nfseApp')
    .controller('ImpressaoPDFController',
        function ($scope, $rootScope, $http, $sce, data, $modalInstance) {

            $scope.carregando = true;

            $scope.cancel = function () {
                $modalInstance.dismiss('cancel');
            };
            var file = new Blob([data.data], {type: 'application/pdf'});
            $scope.pdfUrl = window.URL.createObjectURL(file);
            $scope.carregando = false;

            $scope.print = function () {
                $scope.canvas = document.getElementById('pdf');
                var win = window.open();
                win.document.write("<br><img src='" + $scope.canvas.toDataURL() + "'/>");
                win.print();
                win.location.reload();
            };

        });
