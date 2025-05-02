'use strict';

angular.module('nfseApp')
    .factory('CalendarioSorteioService', function ($resource) {
        return $resource('api/externo/nota-premiada/:id', {}, {
            'buscarTodosPremios': {
                url: 'api/externo/nota-premiada-premios',
                method: 'GET',
                isArray: true
            },
            'buscarTodosSorteios': {
                url: 'api/externo/sorteios',
                method: 'GET',
                isArray: true
            },
            'buscarSorteiosRealizados': {
                url: 'api/externo/sorteios-realizados',
                method: 'GET',
                isArray: true
            },
            'proximoSorteio': {
                url: 'api/externo/proximo-sorteio',
                method: 'GET',
            }
        });
    });
