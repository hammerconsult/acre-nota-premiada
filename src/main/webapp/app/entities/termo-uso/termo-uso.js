'use strict';

angular.module('nfseApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('termoUso', {
                parent: 'clean',
                url: '/termo-uso',
                data: {
                    roles: ['ROLE_USER'],
                    pageTitle: 'Regulamento'
                },
                ncyBreadcrumb: {
                    label: 'Regulamento'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/termo-uso/termo-uso.html',
                        controller: 'TermoUsoController'
                    }
                },
                resolve: {
                    termo: ['TermoUso', function (TermoUso) {
                        return TermoUso.termoUsoVigente().$promise;
                    }],
                    usuario: ['Principal', function (Principal) {
                        return Principal.identity();
                    }]
                }
            });
    });
