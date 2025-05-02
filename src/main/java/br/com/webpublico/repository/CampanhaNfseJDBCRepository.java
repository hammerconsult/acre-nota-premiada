package br.com.webpublico.repository;

import br.com.webpublico.domain.ArquivoDTO;
import br.com.webpublico.domain.CampanhaDTO;
import br.com.webpublico.domain.ParametroQuery;
import br.com.webpublico.util.QueryUtil;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public class CampanhaNfseJDBCRepository implements Serializable {

    private static final String SELECT = " select " +
            "       row_number() over (${order_by}) as linha, " +
            "       c.id,  " +
            "       c.descricao,  " +
            "       c.inicio,  " +
            "       c.fim ";
    private static final String FROM = "   from campanhanfse c  ";
    private static final String DEFAULT_ORDER_BY = " order by c.id desc ";

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    IdJDBCRepository idJDBCRepository;
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<CampanhaDTO> buscarCampanhas(Pageable pageable,
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
        sql = new StringBuilder().append(" select * from (").append(sql.toString()).append(") dados ");
        if (pageable != null)
            sql.append(" where dados.linha between :offset and :limit ");
        return namedParameterJdbcTemplate.query(sql.toString(), parameters, new CampanhaDTO());
    }

    public CampanhaDTO findById(Long id) throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append(SELECT.replace("${order_by}", DEFAULT_ORDER_BY)).append(FROM);
        sql.append(" where c.id = :id ");

        MapSqlParameterSource parameterSource = new MapSqlParameterSource();
        parameterSource.addValue("id", id);
        List<CampanhaDTO> query = namedParameterJdbcTemplate.query(sql.toString(), parameterSource, new CampanhaDTO());
        if (query != null && !query.isEmpty()) {
            return query.get(0);
        }
        return null;
    }

    public List<ArquivoDTO> findAllArquvivos() {
        return jdbcTemplate.query(" select arqc.DATAUPLOAD, arq.NOME, arq.DESCRICAO, arq.id from CAMPANHANFSE camp " +
                        "inner join DETENTORARQUIVOCOMPOSICAO den on den.id = camp.DETENTORARQUIVOCOMPOSICAO_ID " +
                        "inner join ARQUIVOCOMPOSICAO arqc on arqc.DETENTORARQUIVOCOMPOSICAO_ID =den.id " +
                        "inner join arquivo arq on arq.id = arqc.ARQUIVO_ID order by arqc.id desc ",
                new ArquivoDTO());
    }
}
