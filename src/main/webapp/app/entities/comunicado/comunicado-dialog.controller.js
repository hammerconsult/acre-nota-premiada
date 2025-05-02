(function () {
    'use strict';

    angular.module('nfseApp')
        .controller('ComunicadoDialogController', function ($scope, $state, $modalInstance, $sce, $http,
                                                            comunicado) {

            $scope.comunicado = comunicado;

            $http.get('/api/externo/documento-comunicado/' + $scope.comunicado.idDetentor, {responseType: 'arraybuffer'}).then(function (data) {
                var file = new Blob([data.data], {type: 'application/pdf'});
                var fileURL = window.URL.createObjectURL(file);
                $scope.conteudo = $sce.trustAsResourceUrl(fileURL);
            });

            $scope.baixar = function () {
                var a = document.createElement('a');
                a.href = fileURL;
                a.target = '_blank';
                a.download = 'comunicado.pdf';
                document.body.appendChild(a);
                a.click();
            }

            $scope.close = function () {
                $modalInstance.dismiss('cancel');
            }
        });
})();
