package br.com.webpublico.service;

import br.com.webpublico.domain.*;
import br.com.webpublico.repository.PremioJDBCRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service
public class PremioService implements Serializable {

    @Autowired
    PremioJDBCRepository repository;
    @Autowired
    SorteioNfseService sorteioNfseService;

    public List<PremioSorteioDTO> buscarPremiosPorSorteio(Long idSorteio) throws Exception {
        ParametroQuery parametro = new ParametroQuery(Lists.newArrayList(
                new ParametroQueryCampo("s.id", Operador.IGUAL, idSorteio)
        ));
        List<PremioSorteioDTO> premios = repository.buscarPremios(null, Lists.newArrayList(parametro), "");
        recuperar(premios);
        return premios;
    }

    private void recuperar(List<PremioSorteioDTO> premios) throws Exception {
        if (premios != null) {
            for (PremioSorteioDTO premio : premios) {
                premio.setSorteio(sorteioNfseService.findById(premio.getSorteio().getId()));
            }
        }
    }

    public List<PremioSorteioDTO> buscarTodosPremios() throws Exception {
        ParametroQuery parametro = new ParametroQuery(Lists.newArrayList(
                new ParametroQueryCampo("s.situacao", Operador.IN, Lists.newArrayList(SituacaoSorteioDTO.ABERTO.name(), SituacaoSorteioDTO.REALIZADO.name()))
        ));
        List<PremioSorteioDTO> premios = repository.buscarPremios(null, Lists.newArrayList(parametro), "");
        recuperar(premios);
        return premios;
    }
}
