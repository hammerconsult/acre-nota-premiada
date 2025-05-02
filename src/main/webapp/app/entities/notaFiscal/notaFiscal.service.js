'use strict';

angular.module('nfseApp')
    .factory('NotaFiscal', function ($resource) {
        return $resource('api/nota-fiscal/:id', {}, {
            'buscarNotasFiscais': {
                method: 'POST',
                url: 'api/buscar-notas-fiscais',
                isArray: true
            },
            'buscarCompetenciasNotasFiscais': {
                method: 'GET',
                url: 'api/competencias-notas-fiscais',
                isArray: true
            },
            'buscarNotasFiscaisPorSorteio': {
                method: 'GET',
                url: 'api/notas-fiscais-por-sorteio',
                isArray: true
            },
            'buscarNotasFiscaisPorCompetencia': {
                method: 'GET',
                url: 'api/notas-fiscais-por-competencia',
                isArray: true
            },
            'buscarInformacoesGerais': {
                method: 'GET',
                url: 'api/externo/informacoes-nota-premiada'
            },
        });
    });
