'use strict';

angular.module('nfseApp')
    .factory('MunicipioSearch', function ($resource) {
        return $resource('api/_search/municipios/:query', {}, {
            'query': { method: 'GET', isArray: true}
        });
    });
