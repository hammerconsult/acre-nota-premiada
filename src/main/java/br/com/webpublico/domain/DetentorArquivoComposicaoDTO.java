package br.com.webpublico.domain;

import java.io.Serializable;
import java.util.List;

public class DetentorArquivoComposicaoDTO implements Serializable {

    private Long id;
    private List<ArquivoComposicaoDTO> arquivos;
    private ArquivoComposicaoDTO arquivo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ArquivoComposicaoDTO> getArquivos() {
        return arquivos;
    }

    public void setArquivos(List<ArquivoComposicaoDTO> arquivos) {
        this.arquivos = arquivos;
    }

    public ArquivoComposicaoDTO getArquivo() {
        return arquivo;
    }

    public void setArquivo(ArquivoComposicaoDTO arquivo) {
        this.arquivo = arquivo;
    }
}
