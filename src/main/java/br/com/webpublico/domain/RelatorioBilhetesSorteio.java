package br.com.webpublico.domain;

import java.io.Serializable;
import java.util.List;

public class RelatorioBilhetesSorteio implements Serializable {

    private CampanhaDTO campanha;
    private SorteioDTO sorteio;
    private List<PremioSorteioDTO> premios;
    private List<BilheteDTO> bilhetes;

    public RelatorioBilhetesSorteio() {
    }

    public CampanhaDTO getCampanha() {
        return campanha;
    }

    public void setCampanha(CampanhaDTO campanha) {
        this.campanha = campanha;
    }

    public SorteioDTO getSorteio() {
        return sorteio;
    }

    public void setSorteio(SorteioDTO sorteio) {
        this.sorteio = sorteio;
    }

    public List<PremioSorteioDTO> getPremios() {
        return premios;
    }

    public void setPremios(List<PremioSorteioDTO> premios) {
        this.premios = premios;
    }

    public List<BilheteDTO> getBilhetes() {
        return bilhetes;
    }

    public void setBilhetes(List<BilheteDTO> bilhetes) {
        this.bilhetes = bilhetes;
    }
}
