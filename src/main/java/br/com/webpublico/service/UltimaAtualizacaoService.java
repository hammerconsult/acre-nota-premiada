package br.com.webpublico.service;

import com.google.common.collect.Maps;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Map;

@Service
@Scope("singleton")
public class UltimaAtualizacaoService implements Serializable {

    private Map<TipoAtualizacaoDTO, CacheAtualizacaoDTO> mapAtualizacao = Maps.newHashMap();

    public synchronized CacheAtualizacaoDTO getCache(TipoAtualizacaoDTO tipoAtualizacao) {
        return mapAtualizacao.get(tipoAtualizacao);
    }

    public synchronized void setCache(TipoAtualizacaoDTO tipoAtualizacao, CacheAtualizacaoDTO cache) {
        mapAtualizacao.put(tipoAtualizacao, cache);
    }

    public synchronized boolean devoAtualizar(TipoAtualizacaoDTO tipoAtualizacao) {
        CacheAtualizacaoDTO cache = getCache(tipoAtualizacao);
        if (cache == null) {
            return true;
        } else {
            Calendar proximaAtualizacao = Calendar.getInstance();
            proximaAtualizacao.setTime(cache.getUltimaAtualizacao());
            proximaAtualizacao.add(Calendar.MINUTE, tipoAtualizacao.getMinutos());

            Calendar now = Calendar.getInstance();
            return proximaAtualizacao.before(now);
        }
    }
}
