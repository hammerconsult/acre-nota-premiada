package br.com.webpublico.repository;

import br.com.webpublico.domain.ComunicadoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public class ComunicadoJDBCRepository implements Serializable {

    private static final String SELECT = " select " +
            " c.id, " +
            " row_number() over (order by c.inicio desc) as linha," +
            " c.titulo, " +
            " c.inicio, " +
            " c.fim, " +
            " c.detentorarquivocomposicao_id ";
    private static final String FROM = " from comunicadonotapremiada c ";

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ComunicadoDTO getUltimoComunicado() throws Exception {
        StringBuilder sql = new StringBuilder();
        sql.append(SELECT)
                .append(FROM)
                .append(" where current_date between c.inicio and c.fim ");

        MapSqlParameterSource parameters = new MapSqlParameterSource();

        sql = new StringBuilder().append(" select * from (").append(sql).append(") dados ")
                .append(" where dados.linha = 1 ");

        try {
            return namedParameterJdbcTemplate.queryForObject(sql.toString(),
                    parameters, new ComunicadoDTO());
        } catch (Exception e) {
            return null;
        }
    }
}
