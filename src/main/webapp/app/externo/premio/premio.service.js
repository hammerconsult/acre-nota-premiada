'use strict';

angular.module('nfseApp')
    .factory('PremioService', function ($resource) {
        return $resource('api/externo/nota-premiada/:id', {}, {
            'buscarTodosPremios': {
                url: 'api/externo/premios',
                method: 'GET',
                isArray: true
            },
            'buscarPremiosPorCampanha': {
                url: 'api/externo/premios-por-campanha',
                method: 'GET',
                isArray: true
            },
            'premiosPorSorteio': {
                url: 'api/externo/premios-por-sorteio',
                method: 'GET',
                isArray: true
            }
        });
    });
