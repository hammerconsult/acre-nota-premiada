'use strict';

angular.module('nfseApp')
    .factory('CupomService', function ($resource) {
        return $resource('api/cupom/:id', {}, {
            'buscarBilhetesPremiadosSorteio': {
                url: 'api/externo/cupons-premiados-sorteio',
                method: 'GET',
                isArray: true
            },
            'buscarCuponsPorSorteioAndUsuario': {
                url: 'api/cupons-sorteio-usuario',
                method: 'GET',
                isArray: true
            },
        });
    });
