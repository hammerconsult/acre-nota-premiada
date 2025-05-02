package br.com.webpublico.repository;

import br.com.webpublico.domain.ParametroQuery;
import br.com.webpublico.domain.PremioSorteioDTO;
import br.com.webpublico.util.QueryUtil;
import com.google.common.base.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public class PremioJDBCRepository implements Serializable {

    private static final String SELECT = "  select " +
            "        row_number() over (${order_by}) as linha," +
            "        p.id, " +
            "        p.sorteio_id, " +
            "        p.sequencia, " +
            "        p.descricao, " +
            "        p.quantidade, " +
            "        p.valor ";
    private static final String FROM = "   from premiosorteionfse p " +
            "  inner join sorteionfse s on s.id = p.sorteio_id " +
            "  inner join CAMPANHANFSE c on c.id = s.CAMPANHA_ID and c.FIM >= current_date";

    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<PremioSorteioDTO> buscarPremios(Pageable pageable,
                                                List<ParametroQuery> parametros,
                                                String orderBy) throws Exception {
        if (Strings.isNullOrEmpty(orderBy))
            orderBy = " order by s.datasorteio, p.sequencia ";

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
        return namedParameterJdbcTemplate.query(sql.toString(), parameters, new PremioSorteioDTO());
    }
}
