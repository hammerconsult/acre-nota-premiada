package br.com.webpublico.domain;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class CampanhaDTO implements RowMapper<CampanhaDTO> {

    private Long id;
    private String descricao;
    private Date inicio;
    private Date fim;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Date getFim() {
        return fim;
    }

    public void setFim(Date fim) {
        this.fim = fim;
    }

    @Override
    public CampanhaDTO mapRow(ResultSet resultSet, int i) throws SQLException {
        CampanhaDTO dto = new CampanhaDTO();
        dto.setId(resultSet.getLong("id"));
        dto.setDescricao(resultSet.getString("descricao"));
        dto.setInicio(resultSet.getDate("inicio"));
        dto.setFim(resultSet.getDate("fim"));
        return dto;
    }
}
