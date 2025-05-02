package br.com.webpublico.domain;

import java.io.Serializable;
import java.util.Objects;

public class MunicipioDTO implements Serializable {

    private Long id;
    private String codigo;
    private String nome;
    private String estado;

    public MunicipioDTO() {
    }

    public MunicipioDTO(String nome, String estado) {
        this.nome = nome;
        this.estado = estado;
    }

    public MunicipioDTO(Long id, String codigo, String nome, String estado) {
        this.id = id;
        this.codigo = codigo;
        this.nome = nome;
        this.estado = estado;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MunicipioDTO that = (MunicipioDTO) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id);
    }
}
