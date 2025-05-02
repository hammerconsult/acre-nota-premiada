(function () {
    'use strict';

    angular.module('nfseApp')
        .controller('NotasFiscaisRecebidasController',
            function ($scope, NotaFiscal, ParseLinks, DataUtil, $filter, Principal, Util) {
                $scope.notaFiscals = [];
                $scope.pageCompetencia = 0;
                $scope.page = 0;
                $scope.mostrarFiltros = false;

                Principal.identity().then(function (account) {
                    $scope.isAuthenticated = Principal.isAuthenticated;
                    $scope.account = account;
                    $scope.loadCompetencias();
                });

                $scope.loadPageCompetencias = function (page) {
                    $scope.pageCompetencia = page;
                    $scope.loadCompetencias();
                };

                $scope.loadCompetencias = function () {
                    NotaFiscal.buscarCompetenciasNotasFiscais({
                            page: $scope.pageCompetencia,
                            per_page: 5,
                            login: $scope.account.login
                        },
                        function (result, headers) {
                            if (headers) {
                                $scope.linksCompetencia = ParseLinks.parse(headers('link'));
                            }
                            $scope.competencias = result;
                            if ($scope.competencias) {
                                $scope.buscarNotasFiscaisPorCompetencia($scope.competencias[0]);
                            }
                        });
                };

                $scope.buscarNotasFiscaisPorCompetencia = function (competencia) {
                    $scope.competenciaSelecionada = competencia;
                    NotaFiscal.buscarNotasFiscaisPorCompetencia({
                            page: $scope.page,
                            per_page: 10,
                            login: $scope.account.login,
                            ano: $scope.competenciaSelecionada.ano,
                            mes: $scope.competenciaSelecionada.mes
                        },
                        function (result, headers) {
                            if (headers)
                                $scope.links = ParseLinks.parse(headers('link'));
                            $scope.notaFiscals = result;
                            var tamanhoPainel = $('#painel-resumo > .panel-body').height();
                            $("#painel-notas > .panel-body").height(tamanhoPainel);

                        });
                };

                $scope.loadPage = function (page) {
                    $scope.page = page;
                    $scope.buscarNotasFiscaisPorCompetencia($scope.competenciaSelecionada);
                };

                $scope.refresh = function () {
                    $scope.buscarNotasFiscaisPorCompetencia($scope.competenciaSelecionada);
                    $scope.clear();
                };

            });
})();
