'use strict';

angular.module('nfseApp')
    .factory('TermoUso', function ($resource) {
        return $resource('api/termo-uso/:id', {}, {
            'termoUsoVigente': {
                url: 'api/termo-uso/vigente',
                method: 'GET',
            },
            'termoUsoParaAceite': {
                url: 'api/termo-uso/para-aceite',
                method: 'POST',
            },
            'aceitarTermoUso': {
                url: 'api/termo-uso/aceitar',
                method: 'POST',
            }
        });
    });