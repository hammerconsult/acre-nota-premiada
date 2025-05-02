package br.com.webpublico.domain;

import br.com.webpublico.util.trocatag.TipoTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TemplateDTO implements Serializable, RowMapper<TemplateDTO> {

    private Long id;
    private TipoTemplate tipoTemplate;
    private String conteudo;

    public TemplateDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TipoTemplate getTipoTemplate() {
        return tipoTemplate;
    }

    public void setTipoTemplate(TipoTemplate tipoTemplate) {
        this.tipoTemplate = tipoTemplate;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    @Override
    public TemplateDTO mapRow(ResultSet resultSet, int i) throws SQLException {
        TemplateDTO dto = new TemplateDTO();
        dto.setId(resultSet.getLong("ID"));
        dto.setConteudo(resultSet.getString("CONTEUDO"));
        return dto;
    }
}
