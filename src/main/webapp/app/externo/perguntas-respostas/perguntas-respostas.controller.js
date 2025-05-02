'use strict';

angular.module('nfseApp')
    .controller('PerguntasRespostasController', function ($rootScope, $scope,
                                                          PerguntasRespostasService,
                                                          Principal) {

        $scope.perguntasPorAssunto = [];
        $scope.perguntasAndRespostas = [];

        Principal.config().then(function (configuracao) {
            $rootScope.configuracao = configuracao;
        });

        function buscarPerguntasRespostasParaExibicao() {
            PerguntasRespostasService.perguntasRespostasParaExibicao(function (data) {
                $scope.perguntasAndRespostas = data;
            });
        }

        buscarPerguntasRespostasParaExibicao();
    });
