package br.com.webpublico.domain;

public enum SituacaoFaleConoscoNfseDTO {
    ABERTO("Aberto"), ENCERRADO("Encerrado");

    private String descricao;

    SituacaoFaleConoscoNfseDTO(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
