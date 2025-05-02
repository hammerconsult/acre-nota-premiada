package br.com.webpublico.repository;

import br.com.webpublico.domain.ConfiguracaoGovBrDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ConfiguracaoGovBrJDBCRepository {

    @Value("${perfil.aplicacao}")
    private String perfilAplicacao;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public ConfiguracaoGovBrDTO getConfiguracaoGovBr() {
        List<ConfiguracaoGovBrDTO> query = jdbcTemplate.query(" select c.id, c.urlprovider, " +
                        " c.clientid, c.secret, c.redirecturi, c.codeverifier " +
                        "    from configuracaogovbr c " +
                        " where c.sistema = ? " +
                        "   and c.ambiente = ? ", new Object[]{"NOTA_PREMIADA", perfilAplicacao},
                new ConfiguracaoGovBrDTO());
        if (query != null && !query.isEmpty()) {
            return query.get(0);
        }
        return null;
    }
}
