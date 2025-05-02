package br.com.webpublico.domain;

import java.io.Serializable;

public class PerguntasRespostasDTO implements Serializable {

    private Long id;
    private AssuntoDTO assunto;
    private String pergunta;
    private String resposta;
    private Integer ordem;
    private Boolean habilitarExibicao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public AssuntoDTO getAssunto() {
        return assunto;
    }

    public void setAssunto(AssuntoDTO assunto) {
        this.assunto = assunto;
    }

    public String getPergunta() {
        return pergunta;
    }

    public void setPergunta(String pergunta) {
        this.pergunta = pergunta;
    }

    public String getResposta() {
        return resposta;
    }

    public void setResposta(String resposta) {
        this.resposta = resposta;
    }

    public Integer getOrdem() {
        return ordem;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }

    public Boolean getHabilitarExibicao() {
        return habilitarExibicao;
    }

    public void setHabilitarExibicao(Boolean habilitarExibicao) {
        this.habilitarExibicao = habilitarExibicao;
    }
}
