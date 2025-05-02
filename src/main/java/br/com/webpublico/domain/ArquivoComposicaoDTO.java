package br.com.webpublico.domain;

import java.io.Serializable;
import java.util.Date;

public class ArquivoComposicaoDTO implements Serializable {

    private Long id;
    private ArquivoDTO arquivo;
    private Date dataUpload;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ArquivoDTO getArquivo() {
        return arquivo;
    }

    public void setArquivo(ArquivoDTO arquivo) {
        this.arquivo = arquivo;
    }

    public Date getDataUpload() {
        return dataUpload;
    }

    public void setDataUpload(Date dataUpload) {
        this.dataUpload = dataUpload;
    }
}
