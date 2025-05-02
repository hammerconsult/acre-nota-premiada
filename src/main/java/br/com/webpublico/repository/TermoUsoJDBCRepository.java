package br.com.webpublico.repository;

import br.com.webpublico.DateUtils;
import br.com.webpublico.domain.BooleanDTO;
import br.com.webpublico.domain.TermoUsoDTO;
import br.com.webpublico.domain.UsuarioNotaPremiadaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Repository
public class TermoUsoJDBCRepository implements Serializable {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    IdJDBCRepository idJDBCRepository;


    public TermoUsoDTO buscarTermoUsoVigente() {
        List<TermoUsoDTO> query = jdbcTemplate.query(" SELECT " +
                        " T.ID, " +
                        " T.CONTEUDO " +
                        " FROM TERMOUSO T " +
                        " WHERE T.SISTEMA = 'NOTA_PREMIADA' " +
                        "   AND T.INICIOVIGENCIA = (SELECT MAX(S.INICIOVIGENCIA) FROM TERMOUSO S " +
                        "                           WHERE S.SISTEMA = 'NOTA_PREMIADA') ",
                new TermoUsoDTO());
        if (!query.isEmpty()) {
            return query.stream().findFirst().get();
        }
        return null;
    }

    public BooleanDTO hasTermoParaAceite(UsuarioNotaPremiadaDTO usuario) {
        List<BooleanDTO> query = jdbcTemplate.query(" SELECT 1 AS VALUE " +
                        " FROM TERMOUSO T " +
                        " WHERE T.SISTEMA = 'NOTA_PREMIADA' " +
                        "   AND T.INICIOVIGENCIA = (SELECT MAX(S.INICIOVIGENCIA) FROM TERMOUSO S" +
                        "                               WHERE S.SISTEMA = 'NOTA_PREMIADA') " +
                        "   AND NOT EXISTS (SELECT 1 FROM TERMOUSONOTAPREMIADA TN " +
                        "                   WHERE TN.TERMOUSO_ID = T.ID " +
                        "                     AND TN.USUARIO_ID = ?) ",
                new Object[]{usuario.getId()}, new BooleanDTO());
        if (!query.isEmpty()) {
            return query.stream().findFirst().get();
        }
        return new BooleanDTO();
    }

    public void aceitarTermoUso(TermoUsoDTO termoUso, UsuarioNotaPremiadaDTO usuario) {
        jdbcTemplate.batchUpdate(" INSERT INTO TERMOUSONOTAPREMIADA (ID, DATAACEITE, TERMOUSO_ID, USUARIO_ID) " +
                        "SELECT ?, ?, ?, ? FROM DUAL " +
                        "WHERE NOT EXISTS (SELECT 1 FROM TERMOUSONOTAPREMIADA WHERE TERMOUSO_ID = ? AND USUARIO_ID = ?) ",
                new BatchPreparedStatementSetter() {
                    @Override
                    public void setValues(PreparedStatement ps, int i) throws SQLException {
                        ps.setLong(1, idJDBCRepository.getId());
                        ps.setDate(2, DateUtils.toSQLDate(new Date()));
                        ps.setLong(3, termoUso.getId());
                        ps.setLong(4, usuario.getId());
                        ps.setLong(5, termoUso.getId());
                        ps.setLong(6, usuario.getId());
                    }

                    @Override
                    public int getBatchSize() {
                        return 1;
                    }
                });
    }
}
