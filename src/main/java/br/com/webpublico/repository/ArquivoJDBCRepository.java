package br.com.webpublico.repository;

import br.com.webpublico.domain.ArquivoDTO;
import br.com.webpublico.domain.ArquivoParteDTO;
import br.com.webpublico.repository.mapper.ArquivoMapper;
import br.com.webpublico.repository.mapper.ArquivoParteMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public class ArquivoJDBCRepository implements Serializable {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public ArquivoDTO findById(Long id) {
        List<ArquivoDTO> query =
                jdbcTemplate.query(" SELECT ID, DESCRICAO, MIMETYPE, NOME, TAMANHO " +
                                " FROM ARQUIVO " +
                                " WHERE ID = ? ",
                        new Object[]{id},
                        new ArquivoMapper());
        if (query != null && !query.isEmpty()) {
            return query.stream().findFirst().get();
        }
        return null;
    }

    public List<ArquivoParteDTO> findPartes(Long idArquivo) {
        return jdbcTemplate.query(" SELECT ID, DADOS " +
                                " FROM ARQUIVOPARTE " +
                                " WHERE ARQUIVO_ID = ? ",
                        new Object[]{idArquivo},
                        new ArquivoParteMapper());
    }
}
