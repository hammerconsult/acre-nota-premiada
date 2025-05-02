'use strict';

angular.module('nfseApp')
    .service('ImpressaoPdf', function ($modal, $http) {

        this.imprimirPdfViaUrl = function (url) {

            $http.get(url, {responseType: 'arraybuffer'}).then(function (data) {
                var file = new Blob([data.data], {type: 'application/pdf'});
                var fileURL = window.URL.createObjectURL(file);
                var a = document.createElement('a');
                a.href = fileURL;
                a.target = '_blank';
                a.download = 'documento.pdf';
                document.body.appendChild(a);
                a.click();
            });
        };

        this.imprimirPdfViaPost = function (url, data) {
            $http.post(url, data, {responseType: 'arraybuffer'}).then(function (data) {
                var file = new Blob([data.data], {type: 'application/pdf'});
                var fileURL = window.URL.createObjectURL(file);
                var a = document.createElement('a');
                a.href = fileURL;
                a.target = '_blank';
                a.download = 'documento.pdf';
                document.body.appendChild(a);
                a.click();
            });
        };
    });
