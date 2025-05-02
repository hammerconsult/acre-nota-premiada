package br.com.webpublico.repository;

import br.com.webpublico.domain.ConfiguracaoEmailDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public class EmailJDBCRepository implements Serializable {


    @Autowired
    JdbcTemplate jdbcTemplate;

    public ConfiguracaoEmailDTO find() {
        List<ConfiguracaoEmailDTO> query = jdbcTemplate.query(" SELECT * FROM ConfiguracaoEmail e ", new ConfiguracaoEmailDTO());
        if (query != null && !query.isEmpty()) {
            return query.stream().findFirst().get();
        }
        return null;
    }
}
