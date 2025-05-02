'use strict';

angular.module('nfseApp')
    .controller('RegulamentoController', function ($scope, $state, $sce, CampanhaService, $http) {
        $scope.legislacoes = [];
        $scope.buscarArquivo = buscarArquivo;
        $scope.baixarAnexo = baixarAnexo;

        function buscarLegislacoesParaExibicao() {
            CampanhaService.legislacoesParaExibicao(function (data) {
                $scope.legislacoes = data;
                if ($scope.legislacoes && $scope.legislacoes.length > 0) {
                    buscarArquivo($scope.legislacoes[0]);
                }
            });
        }

        function buscarArquivo(legislacao) {
            $scope.legislacaoSelecionada = legislacao;
            $http.get("/api/externo/arquivo/" + legislacao.id, {responseType: 'arraybuffer'}).then(function (data) {
                $scope.arquivo = data;
                $scope.file = new Blob([$scope.arquivo.data], {type: 'application/pdf'});
                $scope.fileURL = window.URL.createObjectURL($scope.file);
                $scope.conteudo = $sce.trustAsResourceUrl($scope.fileURL);
            });
        }

        function baixarAnexo() {
            var a = document.createElement('a');
            a.href = $scope.fileURL;
            a.target = '_blank';
            a.download = 'download-iss-onlione.pdf';
            document.body.appendChild(a);
            a.click();
        }

        buscarLegislacoesParaExibicao();
    });
