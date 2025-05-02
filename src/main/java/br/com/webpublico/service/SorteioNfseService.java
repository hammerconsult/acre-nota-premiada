package br.com.webpublico.service;

import br.com.webpublico.domain.*;
import br.com.webpublico.repository.SorteioNfseJDBCRepository;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Service
public class SorteioNfseService implements Serializable {
    private static Logger log = LoggerFactory.getLogger(SorteioNfseService.class);

    @Autowired
    SorteioNfseJDBCRepository repository;
    @Autowired
    UsuarioNotaPremiadaService usuarioNotaPremiadaService;
    @Autowired
    UltimaAtualizacaoService ultimaAtualizacaoService;

    public SorteioDTO findById(Long id) throws Exception {
        return repository.findById(id);
    }

    public List<SorteioDTO> buscarTodosSorteios() throws Exception {
        return repository.buscarSorteios(null, new ArrayList<>(), "");
    }

    public List<SorteioDTO> buscarSorteiosRealizados() throws Exception {
        ParametroQuery parametro = new ParametroQuery(Lists.newArrayList(
                new ParametroQueryCampo("s.situacao", Operador.IGUAL, SituacaoSorteioDTO.REALIZADO.name())
        ));
        return repository.buscarSorteios(null, Lists.newArrayList(parametro), "");
    }

    public Page<SorteioUsuarioDTO> buscarSorteiosPorUsuario(Pageable pageable, String login) {
        return repository.buscarSorteiosPorUsuario(pageable, login);
    }

    public InformacaoGeralDTO buscarInformacoesNotaPremiada() {
        if (ultimaAtualizacaoService.devoAtualizar(TipoAtualizacaoDTO.INFORMACAO_GERAL)) {
            InformacaoGeralDTO informacaoGeral = repository.buscarInformacoesNotaPremiada();
            ultimaAtualizacaoService.setCache(TipoAtualizacaoDTO.INFORMACAO_GERAL,
                    new CacheAtualizacaoDTO(informacaoGeral));
            return informacaoGeral;
        }
        CacheAtualizacaoDTO cache = ultimaAtualizacaoService.getCache(TipoAtualizacaoDTO.INFORMACAO_GERAL);
        return (InformacaoGeralDTO) cache.getObject();
    }

    public SorteioDTO buscarProximoSorteio() {
        if (ultimaAtualizacaoService.devoAtualizar(TipoAtualizacaoDTO.PROXIMO_SORTEIO)) {
            SorteioDTO sorteio = repository.buscarProximoSorteio();
            ultimaAtualizacaoService.setCache(TipoAtualizacaoDTO.PROXIMO_SORTEIO,
                    new CacheAtualizacaoDTO(sorteio));
            return sorteio;
        }
        CacheAtualizacaoDTO cache = ultimaAtualizacaoService.getCache(TipoAtualizacaoDTO.PROXIMO_SORTEIO);
        return (SorteioDTO) cache.getObject();
    }
}
