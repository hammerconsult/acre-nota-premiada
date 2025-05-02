package br.com.webpublico.service;

public enum TipoAtualizacaoDTO {
    INFORMACAO_GERAL(12, 0),
    PROXIMO_SORTEIO(0, 30);

    private Integer horas;
    private Integer minutos;

    TipoAtualizacaoDTO(Integer horas, Integer minutos) {
        this.horas = horas;
        this.minutos = minutos;
    }

    public Integer getHoras() {
        return horas;
    }

    public Integer getMinutos() {
        return minutos;
    }
}
