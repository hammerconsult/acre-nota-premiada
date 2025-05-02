package br.com.webpublico.repository;

import br.com.webpublico.domain.UsuarioNotaPremiadaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

@Repository
public class UsuarioNotaPremiadaJDBCRepository implements Serializable {

    private static final String SELECT = "  select " +
            "      u.id, " +
            "      u.login, " +
            "      u.password, " +
            "      u.ativo, " +
            "      u.dicasenha, " +
            "      u.imagem, " +
            "      u.resetkey, " +
            "      u.resetdate, " +
            "      dp.id as id_dadospessoais, " +
            "      dp.cpfcnpj, " +
            "      dp.nomerazaosocial, " +
            "      dp.inscricaoestadualrg, " +
            "      dp.apelido, " +
            "      dp.telefone, " +
            "      dp.celular, " +
            "      dp.cep, " +
            "      dp.logradouro, " +
            "      dp.bairro, " +
            "      dp.numero, " +
            "      dp.complemento, " +
            "      dp.municipio, " +
            "      dp.uf, " +
            "      dp.email, " +
            "      dp.datanascimento, " +
            "      (select listagg(up.permissao, ',') " +
            "                      within group (order by up.permissao) " +
            "          from usuarionotaprempermissao up " +
            "       where up.usuario_id = u.id) as permissoes, " +
            "      (select 1" +
            "          from termousonotapremiada tn" +
            "         inner join termouso t on t.id = tn.termouso_id " +
            "       where t.iniciovigencia = (select max(s.iniciovigencia) from termouso s) " +
            "         and tn.usuario_id = u.id) as termo_assinado ";
    private static final String FROM = "   from usuarionotapremiada u " +
            "  inner join dadospessoaisnfse dp on dp.id = u.dadospessoais_id ";

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    @Autowired
    IdJDBCRepository idJDBCRepository;

    public UsuarioNotaPremiadaDTO findByLogin(String login) {
        List<UsuarioNotaPremiadaDTO> query = jdbcTemplate.query(SELECT +
                FROM +
                " where u.login =  ? ", new Object[]{login}, new UsuarioNotaPremiadaDTO());
        if (query != null && !query.isEmpty()) {
            return query.stream().findFirst().get();
        }
        return null;
    }

    public UsuarioNotaPremiadaDTO findByKey(String key) {
        List<UsuarioNotaPremiadaDTO> query = jdbcTemplate.query(SELECT +
                FROM +
                " where u.resetkey =  ? ", new Object[]{key}, new UsuarioNotaPremiadaDTO());
        if (query != null && !query.isEmpty()) {
            return query.stream().findFirst().get();
        }
        return null;
    }

    public UsuarioNotaPremiadaDTO inserir(UsuarioNotaPremiadaDTO usuario) {
        usuario.setId(idJDBCRepository.getId());

        jdbcTemplate.batchUpdate(" insert into usuarionotapremiada (id, dadospessoais_id, login, password, " +
                " ativo, imagem, dicasenha, resetkey, resetdate, participandoprograma) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ", usuario);

        jdbcTemplate.batchUpdate(" insert into usuarionotaprempermissao (id, usuario_id, permissao)\n" +
                "values (?, ?, ?) ", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement preparedStatement, int i) throws SQLException {
                preparedStatement.setLong(1, idJDBCRepository.getId());
                preparedStatement.setLong(2, usuario.getId());
                preparedStatement.setString(3, "ROLE_USER");
            }

            @Override
            public int getBatchSize() {
                return 1;
            }
        });

        return usuario;
    }

    public UsuarioNotaPremiadaDTO update(UsuarioNotaPremiadaDTO usuario) {
        usuario.setAlteracao(Boolean.TRUE);
        jdbcTemplate.batchUpdate(" update usuarionotapremiada set imagem = ?, resetkey = ?, resetdate = ?," +
                " password = ?, participandoprograma = ? where id = ? ", usuario);

        return usuario;
    }
}
