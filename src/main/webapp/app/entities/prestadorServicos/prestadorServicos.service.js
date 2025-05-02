'use strict';

angular.module('nfseApp')
    .factory('PrestadorServicos', function ($resource) {
        return $resource('api/prestadorServicos/:id', {}, {
            'query': {method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'trocarChaveAcesso': {
                url: '/api/prestadorServicos/gerar-chave-acesso/:id',
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'fromUser': {
                method: 'GET',
                url: 'api/prestadores/usuario/',
                isArray: true,
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'getPorCpfCnpj': {
                method: 'GET',
                url: "api/prestadorServicos_por_cpfCnpj/:cpfCnpj",
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        return data;
                    }
                }
            },
            'buscarPorCpfCnpj': {
                method: 'GET',
                url: "api/buscar_prestadorServicos_por_cpfCnpj/:cpfCnpj",
                isArray: true,
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'getNaReceitaPorCpfCnpj': {
                method: 'GET',
                url: "api/prestadorServicos_receita_por_cpfCnpj/:cpfCnpj",
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': {method: 'PUT'},
            'enviarEmailSolicitandoCadastro': {
                method: 'POST',
                url: 'api/prestador-servico/solicitacao-cadastro-usuario'
            },
            'vincularUsuarioEmpresa': {
                method: 'POST',
                url: 'api/prestador-servico/vincular-usuario-empresa'
            },
            'vincularNovoUsuarioEmpresa': {
                method: 'GET',
                url: 'api/prestador-servico/vincular-novo-usuario-empresa/:login'
            },
            'ativarOuDesativarUsuarioEmpresa': {
                method: 'GET',
                url: 'api/prestador-servico/ativar-desativar-usuario/:login'
            },
            'permitirAcessoAoUsuario': {
                method: 'POST',
                url: 'api/prestador-servico/adionar-remover-permissoes-usuario'
            },
            'removerUsuarioEmpresa': {
                method: 'DELETE',
                url: 'api/remove-usuario-empresa/:id',
            },
            'getDebitos': {
                method: 'POST',
                url: 'api/debitos-prestador/',
                isArray: true,
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'buscarDamDaParcela': {
                method: 'POST',
                url: 'api/dam-parcela/',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'enviarDamDaParcela': {
                method: 'POST',
                url: 'api/enviar-dam'
            },
            'salvar': {
                method: 'POST',
                url: 'api/prestador-servico/salvar-novo'
            },
            'getUsuariosPrestador': {
                method: 'GET',
                url: 'api/prestador-servico/usuarios',
                isArray: true,
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'salvarTributosFederais': {
                method: 'POST',
                url: 'api/prestador-servico/tributosFederais',
            },
            'getTributosFederais': {
                method: 'GET',
                url: "api/cadastro-economico/buscar-tributos-federais",
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'getPrestadoresUsuario': {
                method: 'GET',
                url: 'api/prestador-servico/por-login/:login',
                isArray: true,
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'trocarEmpresa': {
                method: 'GET',
                url: 'api/prestador-servico/trocar-empresa'
            },
            'getAnexosLei1232006': {
                method: 'GET',
                url: 'api/anexos-lei-123-2006',
                isArray: true,
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
        });
    });
