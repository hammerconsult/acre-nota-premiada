package br.com.webpublico.domain;

import java.io.Serializable;

public class PessoaDTO implements Serializable {

    private Long id;
    private DadosPessoaisDTO dadosPessoais;


    public PessoaDTO() {
    }

    public PessoaDTO(Long id, DadosPessoaisDTO dadosPessoais) {
        this.id = id;
        this.dadosPessoais = dadosPessoais;
    }

    public DadosPessoaisDTO getDadosPessoais() {
        return dadosPessoais;
    }

    public void setDadosPessoais(DadosPessoaisDTO dadosPessoais) {
        this.dadosPessoais = dadosPessoais;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
