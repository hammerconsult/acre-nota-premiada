package br.com.webpublico.domain;

public enum TipoFaleConoscoNfseDTO  {
    DENUNCIA("Denúncia"),
    RECLAMACAO("Reclamação"),
    SOLICITACAO("Solicitação"),
    SUGESTAO("Sugestão"),
    ELOGIO("Elogio");

    private String descricao;

    TipoFaleConoscoNfseDTO(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
