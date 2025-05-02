package br.com.webpublico.repository;

import br.com.webpublico.domain.ParametroQuery;
import br.com.webpublico.domain.SituacaoSorteioDTO;
import br.com.webpublico.domain.SorteioDTO;
import br.com.webpublico.domain.SorteioUsuarioDTO;
import br.com.webpublico.repository.mapper.IntegerMapper;
import br.com.webpublico.service.InformacaoGeralDTO;
import br.com.webpublico.util.QueryUtil;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public class SorteioNfseJDBCRepository implements Serializable {

    private static final String SELECT = " select " +
            "       row_number() over (${order_by}) as linha, " +
            "       s.id,  " +
            "       s.campanha_id,  " +
            "       s.situacao,  " +
            "       s.numero as numero,  " +
            "       s.descricao as descricao,  " +
            "       s.inicioemissaonotafiscal as inicio_emissao_nota_fiscal,  " +
            "       s.fimemissaonotafiscal as fim_emissao_nota_fiscal,  " +
            "       s.datasorteio as data_sorteio," +
            "       s.datadivulgacaosorteio as data_divulgacao_sorteio, " +
            "       s.datadivulgacaocupom as data_divulgacao_cupom ";
    private static final String FROM = "   from sorteionfse s inner join campanhanfse c on c.id = s.campanha_id and c.FIM >= current_date ";
    private static final String DEFAULT_ORDER_BY = "  order by s.datasorteio  ";

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    IdJDBCRepository idJDBCRepository;
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public Page<SorteioUsuarioDTO> buscarSorteiosPorUsuario(Pageable pageable,
                                                            String login) {
        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("login", login);
        parameterSource.addValue("offset", pageable.getOffset() + 1);
        parameterSource.addValue("limit", (pageable.getPageNumber() + 1) * pageable.getPageSize());

        String select = SELECT +
                "     , " +
                "     (select count(1) " +
                "        from cupomcampanhanfse cupom " +
                "       inner join usuarionotapremiada un on un.id = cupom.usuario_id " +
                "       inner join declaracaoprestacaoservico dec on dec.id = cupom.declaracao_id " +
                "       left join notafiscal nf on nf.declaracaoprestacaoservico_id = dec.id " +
                "       left join servicodeclarado sd on sd.declaracaoprestacaoservico_id = dec.id " +
                "       left join premiosorteionfse premio on premio.id = cupom.premio_id " +
                "     where cupom.campanha_id = s.campanha_id " +
                "       and coalesce(nf.emissao, sd.emissao) between s.inicioemissaonotafiscal " +
                "       and s.fimemissaonotafiscal " +
                "       and (premio.id is null or premio.sorteio_id = s.id) " +
                "       and un.login = :login) as quantidade_cupons, " +
                "    (select count(1) " +
                "     from cupomcampanhanfse cupom " +
                "              inner join usuarionotapremiada un on un.id = cupom.usuario_id " +
                "              inner join declaracaoprestacaoservico dec on dec.id = cupom.declaracao_id " +
                "              left join notafiscal nf on nf.declaracaoprestacaoservico_id = dec.id " +
                "              left join servicodeclarado sd on sd.declaracaoprestacaoservico_id = dec.id " +
                "              inner join premiosorteionfse premio on premio.id = cupom.premio_id " +
                "     where cupom.campanha_id = s.campanha_id " +
                "       and coalesce(nf.emissao, sd.emissao) between s.inicioemissaonotafiscal " +
                "       and s.fimemissaonotafiscal " +
                "       and premio.sorteio_id = s.id " +
                "       and un.login = :login) as quantidade_cupons_premiados ";

        StringBuilder sql = new StringBuilder();
        sql.append(select.replace("${order_by}", DEFAULT_ORDER_BY)).append(FROM).append(DEFAULT_ORDER_BY);

        List<SorteioUsuarioDTO> sorteios = namedParameterJdbcTemplate.query("select * from (" + sql.toString() + ") dados " +
                        " where dados.linha between :offset and :limit ",
                parameterSource,
                new SorteioUsuarioDTO());

        sql = new StringBuilder().append(" select count(1) as value ").append(FROM);
        Integer count = 0;
        List<Integer> query = namedParameterJdbcTemplate.query(sql.toString(),
                parameterSource, new IntegerMapper());
        if (query != null && !query.isEmpty()) {
            count = query.stream().findFirst().get();
        }
        return new PageImpl<>(sorteios, pageable, count);
    }

    public List<SorteioDTO> buscarSorteios(Pageable pageable,
                                           List<ParametroQuery> parametros,
                                           String orderBy) throws Exception {
        if (Strings.isNullOrEmpty(orderBy))
            orderBy = DEFAULT_ORDER_BY;

        StringBuilder sql = new StringBuilder();
        sql.append(SELECT.replace("${order_by}", orderBy)).append(FROM);

        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValues(QueryUtil.montarParametroString(sql, parametros));
        if (pageable != null) {
            parameters.addValue("offset", pageable.getOffset() + 1);
            parameters.addValue("limit", (pageable.getPageNumber() + 1) * pageable.getPageSize());
        }
        sql.append(orderBy);
        sql = new StringBuilder().append(" select * from (").append(sql).append(") dados ");
        if (pageable != null)
            sql.append(" where dados.linha between :offset and :limit ");
        return namedParameterJdbcTemplate.query(sql.toString(), parameters, new SorteioDTO());
    }

    public SorteioDTO findById(Long id) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append(SELECT.replace("${order_by}", DEFAULT_ORDER_BY)).append(FROM);
        sql.append(" where s.id = :id ");

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", id);
        List<SorteioDTO> query = namedParameterJdbcTemplate.query(sql.toString(), parameterSource, new SorteioDTO());
        if (query != null && !query.isEmpty()) {
            return query.get(0);
        }
        return null;
    }


    public InformacaoGeralDTO buscarInformacoesNotaPremiada() {
        List<InformacaoGeralDTO> query = jdbcTemplate.query("select count(distinct un.id) as quantidade_usuarios, " +
                        "       count(distinct ce.id) as quantidade_prestadores,   " +
                        "       count(1) as quantidade_notas_emitidas," +
                        "       sum(dec.totalservicos) as total_servicos   " +
                        "   from declaracaoprestacaoservico dec   " +
                        "  left join notafiscal nf on nf.declaracaoprestacaoservico_id = dec.id   " +
                        "  left join servicodeclarado sd on sd.declaracaoprestacaoservico_id = dec.id   " +
                        "  inner join cadastroeconomico ce on ce.id = nf.prestador_id   " +
                        "  inner join dadospessoaisnfse dpt on dpt.id = dec.dadospessoaistomador_id   " +
                        "  inner join usuarionotapremiada un on un.login = dpt.cpfcnpj   " +
                        "                                   and un.ativo = 1   " +
                        "                                   and un.participandoprograma = 1   " +
                        "where dec.situacao != 'CANCELADA'   " +
                        "  and exists (select 1   " +
                        "                 from sorteionfse s   " +
                        "              where coalesce(nf.emissao, sd.emissao) between s.inicioemissaonotafiscal " +
                        "                  and s.fimemissaonotafiscal) ",
                new InformacaoGeralDTO());
        if (query != null && !query.isEmpty()) {
            return query.get(0);
        }
        return new InformacaoGeralDTO();
    }

    public SorteioDTO buscarProximoSorteio() {
        String select = SELECT.replace("${order_by}", DEFAULT_ORDER_BY) +
                FROM +
                " where s.situacao = :situacao " +
                "   and s.numero = (select min(ss.numero) " +
                "                      from sorteionfse ss " +
                "                   where ss.situacao = :situacao) ";
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        parameters.addValue("situacao", SituacaoSorteioDTO.ABERTO.name());
        List<SorteioDTO> query = namedParameterJdbcTemplate.query(select,
                parameters, new SorteioDTO());
        if (query != null && !query.isEmpty()) {
            return query.stream().findFirst().get();
        }
        return null;
    }
}
