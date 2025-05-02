'use strict';

angular.module('nfseApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('premio', {
                parent: 'out',
                url: '/notas-premiadas',
                ncyBreadcrumb: {
                    label: 'Informações'
                },
                data: {
                    pageTitle: 'Prêmios'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/externo/premio/premio.html',
                        controller: 'NotaPremiadaController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('global');
                        return $translate.refresh();
                    }]
                }
            });
    });
