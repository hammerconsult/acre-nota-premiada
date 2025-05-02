'use strict';

angular.module('nfseApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('home', {
                parent: 'site',
                url: '/',
                data: {
                    roles: ['ROLE_USER', 'ROLE_TERMO_USO'],
                    pageTitle: 'Bem vindo'
                },
                ncyBreadcrumb: {
                    label: 'Principal'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/main/main.html',
                        controller: 'MainController'
                    }
                },
                resolve: {
                    translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                        $translatePartialLoader.addPart('mes');
                        $translatePartialLoader.addPart('global');
                        $translatePartialLoader.addPart('situacaoSorteio');
                        $translatePartialLoader.addPart('situacaoPremioUsuario');
                        $translatePartialLoader.addPart('situacaoNota');
                        return $translate.refresh();
                    }]
                }
                });
    });
