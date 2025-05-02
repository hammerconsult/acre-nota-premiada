package br.com.webpublico.service;

import br.com.webpublico.domain.*;
import br.com.webpublico.repository.NotaFiscalJDBCRepository;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service
public class NotaFiscalService implements Serializable {

    @Autowired
    NotaFiscalJDBCRepository notaFiscalJDBCRepository;

    public Page<NotaFiscalSearchDTO> consultarNotasFiscais(Pageable pageable,
                                                           List<ParametroQuery> parametros,
                                                           String orderBy) throws Exception {
        List<NotaFiscalSearchDTO> dtos = notaFiscalJDBCRepository.consultarNotasFiscais(pageable, parametros,
                orderBy);

        Integer count = notaFiscalJDBCRepository.contarNotasFiscais(parametros);

        return new PageImpl<>(dtos, pageable, count);
    }

    public Page<CompetenciaNotaFiscalDTO> consultarCompetenciasNotasFiscaisPorUsuario(Pageable pageable,
                                                                                      String login) throws Exception {
        return notaFiscalJDBCRepository.consultarCompetenciasNotasFiscaisPorUsuario(pageable, login);
    }

    public Page<NotaFiscalSearchDTO> consultarNotasFiscaisPorCompetenciaUsuario(Pageable pageable,
                                                                                Integer ano,
                                                                                Integer mes,
                                                                                String login) throws Exception {
        List<ParametroQueryCampo> campos = Lists.newArrayList();
        campos.add(new ParametroQueryCampo("extract(year from coalesce(nf.emissao, sd.emissao))", Operador.IGUAL, ano));
        campos.add(new ParametroQueryCampo("extract(month from coalesce(nf.emissao, sd.emissao))", Operador.IGUAL, mes));
        campos.add(new ParametroQueryCampo("dptnf.cpfcnpj", Operador.IGUAL, login));
        ParametroQuery parametroQuery = new ParametroQuery(campos);
        List<ParametroQuery> parametros = Lists.newArrayList(parametroQuery);
        String orderBy = " order by coalesce(nf.emissao, sd.emissao) desc ";

        return consultarNotasFiscais(pageable, parametros, orderBy);
    }

    public Page<NotaFiscalSearchDTO> consultarNotasFiscaisPorSorteioUsuario(Pageable pageable,
                                                                            Long idSorteio,
                                                                            String login) throws Exception {
        List<ParametroQueryCampo> campos = Lists.newArrayList();
        campos.add(new ParametroQueryCampo("dptnf.cpfcnpj", Operador.IGUAL, login));
        campos.add(new ParametroQueryCampo("exists (select 1 from sorteionfse s " +
                "  where s.id = " + idSorteio + " and coalesce(nf.emissao, sd.emissao) between s.inicioemissaonotafiscal " +
                " and s.fimemissaonotafiscal)"));
        ParametroQuery parametroQuery = new ParametroQuery(campos);
        List<ParametroQuery> parametros = Lists.newArrayList(parametroQuery);
        String orderBy = " order by coalesce(nf.emissao, sd.emissao) desc ";

        return consultarNotasFiscais(pageable, parametros, orderBy);
    }
}
