package br.com.webpublico.domain;

import java.io.Serializable;

public class ArquivoParteDTO implements Serializable {

    private Long id;
    private byte[] dados;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public byte[] getDados() {
        return dados;
    }

    public void setDados(byte[] dados) {
        this.dados = dados;
    }
}
