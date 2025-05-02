package br.com.webpublico.service;

import br.com.webpublico.domain.BilheteDTO;
import br.com.webpublico.domain.RelatorioBilhetesSorteio;
import br.com.webpublico.domain.SorteioDTO;
import br.com.webpublico.repository.BilheteJDBCRepository;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

@Service
public class CupomCampanhaNfseService implements Serializable {

    @Autowired
    BilheteJDBCRepository repository;
    @Autowired
    ReportService reportService;
    @Autowired
    PremioService premioService;
    @Autowired
    SorteioNfseService sorteioNfseService;
    @Autowired
    CampanhaNfseService campanhaNfseService;

    @Autowired

    public SorteioNfseService getSorteioNfseService() {
        return sorteioNfseService;
    }

    public Page<BilheteDTO> buscarCuponsPorSorteioAndLogin(Pageable pageable,
                                                           SorteioDTO sorteio,
                                                           String login) throws Exception {
        return repository.buscarBilhetesPorSorteioAndUsuario(pageable, sorteio, login, null);
    }

    public List<BilheteDTO> buscarCuponsPremiadosPorSorteio(SorteioDTO sorteio) throws Exception {
        Page<BilheteDTO> page = repository.buscarBilhetesPorSorteioAndUsuario(null, sorteio, null, Boolean.TRUE);
        return page.getContent();
    }

    public BilheteDTO findById(Long id) {
        return repository.findById(id);
    }

    public byte[] gerarRelatorioBilhetesSorteio(Long idSorteio) throws Exception {
        RelatorioBilhetesSorteio relatorio = new RelatorioBilhetesSorteio();
        relatorio.setSorteio(sorteioNfseService.findById(idSorteio));
        relatorio.setCampanha(campanhaNfseService.findById(relatorio.getSorteio().getIdCampanha()));
        relatorio.setPremios(premioService.buscarPremiosPorSorteio(relatorio.getSorteio().getId()));
        relatorio.setBilhetes(repository.buscarBilhetesSorteio(relatorio.getSorteio()));
        HashMap<String, Object> parametros = Maps.newHashMap();
        parametros.put("SUBREPORT_PREMIOS", reportService.compilarJrxml("report/RelatorioBilhetesSorteio_Premios.jrxml"));
        parametros.put("SUBREPORT_BILHETES", reportService.compilarJrxml("report/RelatorioBilhetesSorteio_Bilhetes.jrxml"));
        return reportService.gerarRelatorio("report/RelatorioBilhetesSorteio.jrxml",
                parametros, Lists.newArrayList(relatorio), false);
    }

}
