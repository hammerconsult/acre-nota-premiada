'use strict';

angular.module('nfseApp')
    .controller('ResetFinishController', function ($scope, $state, $stateParams, $timeout, Auth, Notificacao) {

        $scope.resetAccount = {};
        $timeout(function (){angular.element('[ng-model="resetAccount.password"]').focus();});

        $scope.finishReset = function() {
            if ($scope.resetAccount.password !== $scope.confirmPassword) {
                Notificacao.warn('Atenção', 'A Confirmação da Senha está incorreta.');
            } else {
                Auth.resetPasswordFinish({key: $stateParams.key, newPassword: $scope.resetAccount.password}).then(function () {
                    Notificacao.info('Informação', 'Senha alterada com sucesso.');
                    $state.go('login');
                });
            }

        };
    });
