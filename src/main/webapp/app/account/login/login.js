'use strict';

angular.module('nfseApp')
    .config(function ($stateProvider) {
        $stateProvider
            .state('login', {
                parent: 'account',
                url: '/login',
                data: {
                    roles: [],
                    pageTitle: 'Bem vindo ao portal da Nota Rio Branco'
                },
                ncyBreadcrumb: {
                    label: 'Autênticação '
                },
                views: {
                    'content@': {
                        templateUrl: 'app/account/login/login.html',
                        controller: 'LoginController'
                    },
                    'header@': {

                    },
                }
            });
    });
