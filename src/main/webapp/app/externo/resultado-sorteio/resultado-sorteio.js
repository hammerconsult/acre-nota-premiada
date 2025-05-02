'use strict';

angular.module('nfseApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('resultado-sorteio', {
                parent: 'out',
                url: '/resultado-sorteio',
                ncyBreadcrumb: {
                    label: 'Informações'
                },
                data: {
                    pageTitle: 'Resultados dos Sorteios'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/externo/resultado-sorteio/resultado-sorteio.html',
                        controller: 'ResultadoSorteioController'
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
