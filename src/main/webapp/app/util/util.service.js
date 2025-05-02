(function () {
    'use strict';

    angular.module('nfseApp')
        .factory('Util', function ($resource, $window) {
            var service = this;
            service.apenasNumeros = function (valor) {
                return valor.replace(/[^0-9]+/g, '');
            };
            service.getMeses = function () {
                return [
                    {descricao: "Janeiro", numeroMes: 1},
                    {descricao: "Fevereiro", numeroMes: 2},
                    {descricao: "Março", numeroMes: 3},
                    {descricao: "Abril", numeroMes: 4},
                    {descricao: "Maio", numeroMes: 5},
                    {descricao: "Junho", numeroMes: 6},
                    {descricao: "Julho", numeroMes: 7},
                    {descricao: "Agosto", numeroMes: 8},
                    {descricao: "Setembro", numeroMes: 9},
                    {descricao: "Outubro", numeroMes: 10},
                    {descricao: "Novembro", numeroMes: 11},
                    {descricao: "Dezembro", numeroMes: 12}
                ];
            };
            service.getExercicios = function () {
                var primeiroExercicio = new Date().getFullYear();
                var exercicios = [];
                for (var i = primeiroExercicio; i >= (primeiroExercicio - 20); i--) {
                    exercicios.push(i);
                }
                return exercicios;
            };
            service.copyToClipboard = function (toCopy) {
                var body = angular.element($window.document.body);
                var textarea = angular.element('<textarea/>');
                textarea.css({
                    position: 'fixed',
                    opacity: '0'
                });

                textarea.val(toCopy);
                body.append(textarea);
                textarea[0].select();

                document.execCommand('copy');

                textarea.remove();
            };
            return service;
        });
})();
