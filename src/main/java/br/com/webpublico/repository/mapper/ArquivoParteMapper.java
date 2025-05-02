package br.com.webpublico.repository.mapper;

import br.com.webpublico.domain.ArquivoParteDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ArquivoParteMapper implements RowMapper<ArquivoParteDTO> {
    @Override
    public ArquivoParteDTO mapRow(ResultSet resultSet, int i) throws SQLException {
        ArquivoParteDTO dto = new ArquivoParteDTO();
        dto.setId(resultSet.getLong("ID"));
        dto.setDados(resultSet.getBytes("DADOS"));
        return dto;
    }
}
