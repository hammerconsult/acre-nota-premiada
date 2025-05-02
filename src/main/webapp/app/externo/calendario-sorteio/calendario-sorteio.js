'use strict';

angular.module('nfseApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('calendario-sorteio', {
                parent: 'out',
                url: '/calendarios-sorteios',
                ncyBreadcrumb: {
                    label: 'Informações'
                },
                data: {
                    pageTitle: 'Calendário de Sorteios'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/externo/calendario-sorteio/calendario-sorteio.html',
                        controller: 'CalendarioSorteioController'
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
