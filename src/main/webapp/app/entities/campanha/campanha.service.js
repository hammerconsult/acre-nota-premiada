'use strict';

angular.module('nfseApp')
    .factory('CampanhaService', function ($resource) {
        return $resource('', {}, {
            'buscarSorteiosPorUsuario': {
                url: 'api/sorteios-por-usuario',
                method: 'GET',
                isArray: true
            },
            'buscarCampanhas': {
                url: 'api/externo/campanhas',
                method: 'GET',
                isArray: true
            },
            'legislacoesParaExibicao': {
                url: 'api/externo/legislacoes-para-exibicao',
                method: 'GET',
                isArray: true
            }
        });
    });
