'use strict';

angular.module('nfseApp')
    .controller('MunicipioSearchController', function ($scope, Municipio, MunicipioSearch, ParseLinks, SweetAlert, $modalInstance) {
        $scope.municipios = [];
        $scope.page = 1;
        $scope.loadAll = function () {
            Municipio.query({page: $scope.page, per_page: 5}, function (result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.municipios = result;
            });
        };
        $scope.loadPage = function (page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.search = function () {
            MunicipioSearch.query({query: $scope.searchQuery}, function (result) {
                $scope.municipios = result;
            }, function (response) {
                if (response.status === 404) {
                    $scope.loadAll();
                }
            });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.municipio = {codigo: null, descricao: null, ativo: null, id: null};
        };

        $scope.ok = function (municipio) {
            $modalInstance.close(municipio);
        };

        $scope.cancel = function () {
            $modalInstance.dismiss('cancel');
        };
    });
