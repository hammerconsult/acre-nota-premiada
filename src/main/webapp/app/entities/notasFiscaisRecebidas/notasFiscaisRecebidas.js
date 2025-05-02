'use strict';

angular.module('nfseApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('notasFiscaisRecebidas', {
                parent: 'entity',
                url: '/notas-fiscais-recebidas',
                data: {
                    roles: ['ROLE_USER', 'ROLE_TERMO_USO'],
                    pageTitle: 'Minhas Notas'
                },
                ncyBreadcrumb: {
                    label: 'Notas Fiscais'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/entities/notasFiscaisRecebidas/notasFiscaisRecebidas.html',
                        controller: 'NotasFiscaisRecebidasController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('situacaoNota');
                        return $translate.refresh();
                    }]
                }
            });
    });
