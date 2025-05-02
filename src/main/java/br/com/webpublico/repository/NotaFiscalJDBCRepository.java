package br.com.webpublico.repository;

import br.com.webpublico.domain.CompetenciaNotaFiscalDTO;
import br.com.webpublico.domain.NotaFiscalSearchDTO;
import br.com.webpublico.domain.ParametroQuery;
import br.com.webpublico.repository.mapper.IntegerMapper;
import br.com.webpublico.util.QueryUtil;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public class NotaFiscalJDBCRepository implements Serializable {
    private final static String SELECT_NOTA_FISCAL_SEARCH = "select " +
            "    ROW_NUMBER() OVER (${ORDER_BY}) AS LINHA," +
            "    nf.id as id_nota," +
            "    coalesce(nf.numero, sd.numero) as numero_nota," +
            "    coalesce(nf.emissao, sd.emissao) as emissao_nota," +
            "    dptnf.nomerazaosocial as nome_tomador," +
            "    dppnf.nomerazaosocial as nome_prestador," +
            "    dptnf.cpfcnpj as cpfcnpj_tomador," +
            "    dppnf.cpfcnpj as cpfcnpj_prestador," +
            "    dps.situacao as situacao," +
            "    dps.totalservicos as totalservicos ";
    private final static String FROM_NOTA_FISCAL_SEARCH =
            "   from declaracaoprestacaoservico dps " +
                    "  left join notafiscal nf on dps.id = nf.declaracaoprestacaoservico_id " +
                    "  left join servicodeclarado sd on dps.id = sd.declaracaoprestacaoservico_id " +
                    "  left join intermediarioservico i on i.id = dps.intermediario_id " +
                    "  left join rps rps on rps.id = nf.rps_id " +
                    "  left join tomadorserviconfse tnf on tnf.id = nf.tomador_id " +
                    "  left join dadospessoaisnfse dptnf on dptnf.id = dps.dadospessoaistomador_id " +
                    "  left join dadospessoaisnfse dppnf on dppnf.id = dps.dadospessoaisprestador_id " +
                    "  left join cadastroeconomico cep on cep.id = nf.prestador_id ";

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<NotaFiscalSearchDTO> consultarNotasFiscais(Pageable pageable,
                                                           List<ParametroQuery> parametros,
                                                           String orderBy) throws Exception {
        if (Strings.isNullOrEmpty(orderBy))
            orderBy = " ORDER BY NF.NUMERO DESC ";

        StringBuilder sql = new StringBuilder();
        sql.append(SELECT_NOTA_FISCAL_SEARCH.replace("${ORDER_BY}", !Strings.isNullOrEmpty(orderBy) ? orderBy : " order by nf.id "))
                .append(FROM_NOTA_FISCAL_SEARCH);

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValues(QueryUtil.montarParametroString(sql, parametros));
        if (pageable != null) {
            parameters.addValue("OFFSET", pageable.getOffset() + 1);
            parameters.addValue("LIMIT", (pageable.getPageNumber() + 1) * pageable.getPageSize());
        }
        sql = new StringBuilder().append(" SELECT * FROM (").append(sql.toString()).append(") dados ");
        if (pageable != null)
            sql.append(" where dados.linha between :OFFSET and :LIMIT ");
        return namedParameterJdbcTemplate.query(sql.toString(), parameters, new NotaFiscalSearchDTO());
    }

    public Integer contarNotasFiscais(List<ParametroQuery> parametros) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append(" select count(nf.id) as value ").append(FROM_NOTA_FISCAL_SEARCH);

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValues(QueryUtil.montarParametroString(sql, parametros));

        List<Integer> query = namedParameterJdbcTemplate.query(sql.toString(),
                parameters, new IntegerMapper());
        if (query != null && !query.isEmpty()) {
            return query.stream().findFirst().get();
        }
        return 0;
    }

    public Page<CompetenciaNotaFiscalDTO> consultarCompetenciasNotasFiscaisPorUsuario(Pageable pageable, String cpfCnpjTomador) {
        String sql = " with competencias (data, data_fim) as ( " +
                "    select inicio, fim from campanhanfse " +
                "    union all " +
                "    select add_months(data, 1), data_fim from competencias " +
                "    where add_months(data, 1) < data_fim " +
                ") " +
                "select " +
                "       row_number() over (order by data) as linha, " +
                "       extract(year from data) as ano, " +
                "       extract(month from data) as mes, " +
                "       coalesce((select count(1) " +
                "              from declaracaoprestacaoservico dec " +
                "                       inner join dadospessoaisnfse dpt on dpt.id = dec.dadospessoaistomador_id " +
                "                       left join notafiscal nf on nf.declaracaoprestacaoservico_id = dec.id " +
                "                       left join servicodeclarado sd on sd.declaracaoprestacaoservico_id = dec.id " +
                "              where dec.situacao != :cancelada " +
                "                and dpt.cpfcnpj = :cpfcnpj_tomador " +
                "                and extract(year from coalesce(nf.emissao, sd.emissao)) = " +
                "                    extract(year from data) " +
                "                and extract(month from coalesce(nf.emissao, sd.emissao)) = " +
                "                    extract(month from data)), 0) as quantidade, " +
                "        coalesce((select sum(dec.totalservicos) " +
                "                  from declaracaoprestacaoservico dec " +
                "                           inner join dadospessoaisnfse dpt on dpt.id = dec.dadospessoaistomador_id " +
                "                           left join notafiscal nf on nf.declaracaoprestacaoservico_id = dec.id " +
                "                           left join servicodeclarado sd on sd.declaracaoprestacaoservico_id = dec.id " +
                "                   where dec.situacao != :cancelada " +
                "                    and dpt.cpfcnpj = :cpfcnpj_tomador " +
                "                    and extract(year from coalesce(nf.emissao, sd.emissao)) = " +
                "                        extract(year from data) " +
                "                    and extract(month from coalesce(nf.emissao, sd.emissao)) = " +
                "                        extract(month from data)), 0) as total_servicos  " +
                "   from competencias " +
                " order by data ";

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("offset", pageable.getOffset() + 1);
        parameters.addValue("limit", (pageable.getPageNumber() + 1) * pageable.getPageSize());
        parameters.addValue("cpfcnpj_tomador", cpfCnpjTomador);
        parameters.addValue("cancelada", "CANCELADA");

        List<CompetenciaNotaFiscalDTO> competencias = namedParameterJdbcTemplate.query(
                " select * from (" + sql + ") dados where dados.linha between :offset and :limit ", parameters, new CompetenciaNotaFiscalDTO());

        Integer count = 0;
        List<Integer> query = namedParameterJdbcTemplate.query("select count(1) as value from (" + sql + ") dados ", parameters,
                new IntegerMapper());
        if (query != null && !query.isEmpty()) {
            count = query.stream().findFirst().get();
        }
        return new PageImpl<>(competencias, pageable, count);
    }
}
