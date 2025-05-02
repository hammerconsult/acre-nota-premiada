package br.com.webpublico.domain;

import br.com.webpublico.util.Util;
import org.springframework.jdbc.core.RowMapper;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

public class TermoUsoDTO implements Serializable, RowMapper<TermoUsoDTO> {

    private Long id;
    private String conteudo;

    public TermoUsoDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getConteudo() {
        return conteudo;
    }

    public void setConteudo(String conteudo) {
        this.conteudo = conteudo;
    }

    @Override
    public TermoUsoDTO mapRow(ResultSet resultSet, int i) throws SQLException {
        TermoUsoDTO dto = new TermoUsoDTO();
        dto.setId(resultSet.getLong("ID"));
        dto.setConteudo(Util.fromClob(resultSet.getClob("CONTEUDO")));
        return dto;
    }
}
