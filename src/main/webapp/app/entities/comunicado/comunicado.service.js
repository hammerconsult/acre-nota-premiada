(function () {
    'use strict';

angular.module('nfseApp')
    .factory('ComunicadoService', function ($resource) {
        return $resource('api/comunicado/:id', {}, {
            'getUltimoComunicado': {
                url: 'api/externo/ultimo-comunicado',
                method: 'GET'
            }
        });
    });
})();
