'use strict';

angular.module('nfseApp')
    .factory('CanaisAtendimentoService', function ($resource) {
        return $resource('api/externo/nota-premiada/:id', {}, {
            'buscarTodosPremios': {
                url: 'api/externo/nota-premiada-premios',
                method: 'GET',
                isArray: true
            }
        });
    });