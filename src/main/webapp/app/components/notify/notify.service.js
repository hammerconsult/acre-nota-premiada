'use strict';

angular.module('nfseApp')
    .factory('Notificacao', function ($resource, notify, SweetAlert) {
        var template = './scripts/components/notify/notify.html';
        return {
            priority: function (title, message) {
                SweetAlert.swal({title: title, text: message, type: "success", html: true});
            },
            error: function (title, message) {
                SweetAlert.swal({title: title, text: message, type: "error", html: true});
            },
            info: function (title, message) {
                SweetAlert.swal({title: title, text: message, type: "info", html: true});
            },
            warn: function (title, message) {
                SweetAlert.swal({title: title, text: message, type: "warning", html: true});
            },
            confirmDelete: function (callBack) {
                SweetAlert.swal({
                        title: "Confirme a exclusão",
                        text: "Você tem certeza que quer excluir o registro selecionado?",
                        type: "warning",
                        showCancelButton: true,
                        confirmButtonColor: "#DD6B55", confirmButtonText: "Sim, Excluir",
                        cancelButtonText: "Não, Cancelar",
                        closeOnConfirm: false,
                        closeOnCancel: false
                    },
                    function (isConfirm) {
                        if (isConfirm) {
                            callBack();
                            SweetAlert.swal("Removido!", "O registro foi removido com sucesso.", "success");
                        } else {
                            SweetAlert.swal("Cancelado", "O registro não foi removido :)", "error");
                        }
                    });
            },
            confirm: function (title, text, type, callBack) {
                SweetAlert.swal({
                        title: title,
                        text: text,
                        type: type,
                        showCancelButton: true,
                        confirmButtonColor: "#DD6B55",
                        confirmButtonText: "Sim",
                        cancelButtonText: "Não",
                        closeOnConfirm: true,
                        closeOnCancel: true
                    },
                    function (isConfirm) {
                        if (isConfirm) {
                            callBack();
                        }
                    });
            },

            camposObrigatorios: function (mensagem, tipoMensagem) {

                var conteudo = "";
                angular.forEach(mensagem, function (msg) {
                    conteudo += '<div class="alert alert-warning" align="left">' + msg + '</div>';
                });
                SweetAlert.swal({
                        title: "Atenção!",
                        type: tipoMensagem,
                        showCancelButton: false,
                        text: conteudo,
                        confirmButtonText: "OK",
                        closeOnConfirm: true,
                        html: true
                    }
                )
            },
        }
    })
;
