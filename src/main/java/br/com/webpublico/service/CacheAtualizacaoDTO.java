package br.com.webpublico.service;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class CacheAtualizacaoDTO implements Serializable {

    private Date ultimaAtualizacao;
    private Object object;
    private List<Object> objects;

    public CacheAtualizacaoDTO() {
        ultimaAtualizacao = new Date();
    }

    public CacheAtualizacaoDTO(Object object) {
        this();
        this.object = object;
    }

    public CacheAtualizacaoDTO(List<Object> objects) {
        this();
        this.objects = objects;
    }

    public Date getUltimaAtualizacao() {
        return ultimaAtualizacao;
    }

    public void setUltimaAtualizacao(Date ultimaAtualizacao) {
        this.ultimaAtualizacao = ultimaAtualizacao;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public List<Object> getObjects() {
        return objects;
    }

    public void setObjects(List<Object> objects) {
        this.objects = objects;
    }
}
