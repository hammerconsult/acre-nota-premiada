package br.com.webpublico.domain;

import java.io.Serializable;

public class AceiteTermoUsoDTO implements Serializable {

    private TermoUsoDTO termoUso;
    private UsuarioNotaPremiadaDTO usuario;

    public AceiteTermoUsoDTO() {
    }

    public TermoUsoDTO getTermoUso() {
        return termoUso;
    }

    public void setTermoUso(TermoUsoDTO termoUso) {
        this.termoUso = termoUso;
    }

    public UsuarioNotaPremiadaDTO getUsuario() {
        return usuario;
    }

    public void setUsuario(UsuarioNotaPremiadaDTO usuario) {
        this.usuario = usuario;
    }
}
