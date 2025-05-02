'use strict';

angular.module('nfseApp')
    .controller('MainController', function ($scope, $filter, Principal, localStorageService, $state,
                                            $modal, CupomService, CampanhaService) {

        $scope.competencias = [];
        $scope.cupons = [];
        $scope.competenciaAtual = {};
        $scope.compenteciaAnterior = {};
        $scope.hoje = new Date();
        $scope.sorteios = [];
        $scope.sorteioSelecionado;
        $scope.buscarCuponsDoSorteio = buscarCuponsDoSorteio;


        Principal.identity().then(function (account) {
            $scope.account = account;
            if ($scope.account) {
                $scope.isAuthenticated = Principal.isAuthenticated;
                $scope.prestadorPrincipal = localStorageService.get("prestadorPrincipal");
                CampanhaService.buscarSorteiosPorUsuario({
                    login: $scope.account.login,
                    page: 0,
                    per_page: 9999
                }, function (result) {
                    $scope.sorteios = result;

                    buscarCuponsDoSorteio($scope.sorteios[0]);
                });
            }
        });

        function buscarCuponsDoSorteio(sorteio) {
            $scope.sorteioSelecionado = sorteio;
            if ($scope.sorteioSelecionado) {
                CupomService.buscarCuponsPorSorteioAndUsuario({
                    page: 0,
                    per_page: 9999,
                    idSorteio: $scope.sorteioSelecionado.sorteio.id,
                    login: $scope.account.login
                }, function (result) {
                    $scope.cupons = result
                });
            }
        }

        $scope.role = function (role) {
            return Principal.isInRole(role);
        };

        $scope.verNotasDoSorteio = function (sorteio) {
            $modal.open({
                size: 'lg',
                animation: true,
                templateUrl: 'notasPorSorteio.html',
                controller: 'NotaSorteioController',
                controllerAs: 'vm',
                backdrop: 'static',
                resolve: {
                    sorteio: function () {
                        return sorteio.sorteio;
                    }
                }
            });
        }


        $scope.verPremiosDoSorteio = function (sorteio) {
            $modal.open({
                size: 'lg',
                animation: true,
                templateUrl: 'premioPorSorteio.html',
                controller: 'PremioSorteioController',
                controllerAs: 'vm',
                backdrop: 'static',
                resolve: {
                    sorteio: function () {
                        return sorteio.sorteio;
                    }
                }
            });
        }

        $scope.carregarCuponsPremio = function (premio) {
            premio.isCollapsed = !premio.isCollapsed;
            CupomService.buscarCuponsUsuarioPremio({
                    login: $scope.account.login,
                    idPremio: premio.idPremio
                }, function (result) {
                    premio.cupons = result;
                }
            );
        };

        $scope.buscarClassStatusPessoa = function (sorteio) {
            if (sorteio.statusPessoa == 'CUPOM_PREMIADO') {
                return "cupom-premiado";
            } else if (sorteio.statusPessoa == 'NAO_FOI_DESSA_VEZ') {
                return "nao-foi-dessa-vez";
            } else {
                return "proximo-sorteio";
            }
        };

        $scope.verCupom = function (cupom) {
            $modal.open({
                templateUrl: 'app/externo/cupom/cupom-detalhe.html',
                controller: 'CupomDetalheController',
                size: 'lg',
                resolve: {
                    cupom: ['CupomService', function (CupomService) {
                        return CupomService.get({id: cupom.id}).$promise;
                    }],
                    sorteio: function () {
                        return $scope.sorteioSelecionado.sorteio;
                    }
                }
            });
        };
    });

angular.module('nfseApp')
    .controller('NotaSorteioController', function ($scope, $filter, NotaFiscal, $modalInstance, sorteio, Principal, ParseLinks) {
        $scope.notaFiscals = [];
        $scope.per_page = 20;
        $scope.page = 0;
        $scope.sorteio = sorteio;

        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

        Principal.identity().then(function (account) {
            $scope.account = account;
            $scope.loadAll();
        });

        $scope.loadAll = function () {
            NotaFiscal.buscarNotasFiscaisPorSorteio({
                    idSorteio: $scope.sorteio.id,
                    login: $scope.account.login,
                    page: $scope.page,
                    per_page: $scope.per_page,
                },
                function (result, headers) {
                    if (headers)
                        $scope.links = ParseLinks.parse(headers('link'));
                    $scope.notas = result;
                });
        };

        $scope.loadPage = function (page) {
            $scope.page = page;
            $scope.loadAll();
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };
    });


angular.module('nfseApp')
    .controller('PremioSorteioController', function ($scope, $filter, PremioService, $modalInstance, sorteio, Principal) {
        $scope.premios = [];
        $scope.per_page = 20;
        $scope.page = 0;
        $scope.sorteio = sorteio;

        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };

        Principal.identity().then(function (account) {
            $scope.account = account;
            $scope.loadAll();
        });

        $scope.loadAll = function () {
            PremioService.premiosPorSorteio({
                    idSorteio: $scope.sorteio.id
                },
                function (result) {
                    $scope.premios = result;
                });
        };

        $scope.loadPage = function (page) {
            $scope.page = page;
            $scope.loadAll();
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };
    });
