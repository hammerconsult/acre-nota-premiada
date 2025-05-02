package br.com.webpublico.repository;

import br.com.webpublico.domain.TemplateDTO;
import br.com.webpublico.util.trocatag.TipoTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public class TemplateJDBCRepository implements Serializable {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public TemplateDTO findByTipo(TipoTemplate tipo) {
        List<TemplateDTO> query = jdbcTemplate.query(" SELECT id, conteudo from TemplateNfse temp where temp.tipoTemplate = ?", new Object[]{tipo.name()},
                new TemplateDTO());
        if(!query.isEmpty()){
            return query.get(0);
        }
        return null;
    }
}
