'use strict';

angular.module('nfseApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('regulamento', {
                parent: 'out',
                url: '/regulamento',
                data: {
                    roles: [],
                    pageTitle: 'Regulamento'
                },
                ncyBreadcrumb: {
                    label: 'Regulamento'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/campanha/regulamento.html',
                        controller: 'RegulamentoController'
                    }
                },
                resolve: {}
            });
    });
