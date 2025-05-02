'use strict';

angular.module('nfseApp')
    .controller('NavbarController',
        function ($scope, $rootScope, $location, $state, $window, Auth, Principal, localStorageService,
                  Imagem, Configuracao, Notificacao, Account) {
            $scope.logout = logout;
            $scope.carregarDadosUsuario = carregarDadosUsuario;

            $scope.isAuthenticated = false;
            $scope.account = null;
            $rootScope.configuracao = null;
            $rootScope.localizacao = null;
            $rootScope.logoMunicipio = null;

            $rootScope.$on('nfseApp:navbarUpdate', function (event, account) {
                $scope.carregarDadosUsuario();
            });

            function carregarDadosUsuario() {
                $scope.isAuthenticated = Principal.isAuthenticated();
                if ($scope.isAuthenticated) {
                    Principal.identity().then(function (account) {
                        if (account) {
                            $scope.account = account;
                        }
                    });
                }

                Principal.config().then(function (configuracao) {
                    $rootScope.configuracao = configuracao;
                    $rootScope.localizacao = configuracao.municipio;
                    $rootScope.logoMunicipio = configuracao.logoMunicipio;
                });

                Configuracao.getPerfil({}, function (data) {
                    $scope.perfilApp = data.perfil;
                });
            }

            $scope.carregarDadosUsuario();

            function logout() {
                $scope.isAuthenticated = false;
                $scope.account = null;
                Auth.logout();
                $state.go('login');
            }
        })
;
