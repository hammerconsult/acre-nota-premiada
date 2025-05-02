(function () {
    'use strict';

    angular.module('nfseApp')
        .controller('FaleConoscoController',
            function ($scope, $state, $http, $translate, $timeout, FaleConoscoService, Notificacao, Pessoa, Principal, $sanitize) {

                $scope.tipos = ['NAO_EMITIDA', 'CANCELADA', 'INCORRETO'];
                $scope.tipo;
                $scope.account = null;

                $scope.loadAll = function () {
                    $scope.isAuthenticated = Principal.isAuthenticated();
                    if ($scope.isAuthenticated) {
                        Principal.identity().then(function (account) {
                            if (account) {
                                $scope.account = account;
                                $scope.reclamacoes = FaleConoscoService.getByCpf({cpf: $scope.account.login})
                            }
                        });
                    } else {
                        $scope.novaReclamacao = true;
                    }
                }

                $scope.loadAll();

                $scope.trustAsHtml = function (value) {
                    return $sanitize(value);
                };

                $scope.preLoadPrestador = function () {
                    Pessoa.getPorCpfCnpj({cpfCnpj: $scope.faleConosco.dadosReclamado.cpfCnpj}, function (result) {
                        $scope.faleConosco.dadosReclamado = result;
                        $scope.faleConosco.dadosReclamado.id = null;
                    })
                }

                $scope.preLoadTomador = function () {
                    Pessoa.getPorCpfCnpj({cpfCnpj: $scope.faleConosco.cpf.replace(/[^\w\s]/gi, '')}, function (result) {
                        $scope.faleConosco.dadosReclamante = result;
                        $scope.faleConosco.dadosReclamante.id = null;
                    })
                }

                $scope.ecolherTipo = function (tipo) {
                    $scope.tipo = tipo;
                    $scope.faleConosco = {tipo: 'RECLAMACAO'};
                    $scope.faleConosco.dadosReclamado = {};
                    if ($scope.isAuthenticated) {
                        $scope.faleConosco.dadosReclamante = $scope.account.dadosPessoais;
                        $scope.faleConosco.dadosReclamante.id = null;
                    }
                    $scope.definirAssunto();
                }

                $scope.definirAssunto = function () {
                    switch ($scope.tipo) {
                        case 'NAO_EMITIDA':
                            $scope.faleConosco.assunto = 'Nota Fiscal Não Emitida';
                            break;
                        case 'CANCELADA':
                            $scope.faleConosco.assunto = 'Nota Fiscal Cancelada';
                            break;
                        case 'INCORRETO':
                            $scope.faleConosco.assunto = 'Nota Fiscal com Dados Incorretos';
                            break;
                    }
                }

                $scope.save = function () {
                    FaleConoscoService.save($scope.faleConosco, function (data) {
                        if (data.id) {
                            $state.reload();
                            Notificacao.info("Operação Realizada", "Sua solicitação foi encaminhada para o departamento responsável. Entraremos em contato o mais breve possível.");
                        } else {
                            Notificacao.error("Algo inesperado aconteceu!", "Entre em contato com o suporte da prefeitura.");
                        }
                    });
                }

                $scope.atribuirTipo = function (tipo) {
                    $scope.faleConosco.tipo = tipo;
                }
            });
})();
