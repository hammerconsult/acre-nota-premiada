'use strict';

angular.module('nfseApp')
    .controller('TermoUsoController', function ($scope, $state, $sanitize, termo, usuario, TermoUso,
                                                Principal, Auth) {

        $scope.termo = termo;
        $scope.usuario = usuario;
        $scope.aceito = false;

        $scope.trustAsHtml = function (value) {
            return $sanitize(value);
        };

        $scope.continuar = function () {
            TermoUso.aceitarTermoUso({
                termoUso: $scope.termo,
                usuario: $scope.usuario
            }, function (data) {
                Principal.identity(true).then(function (data) {
                    $state.go('home', {reload: true});
                });
            })
        }

        $scope.logout = function () {
            Auth.logout();
            $state.go('login');
        }
    });
