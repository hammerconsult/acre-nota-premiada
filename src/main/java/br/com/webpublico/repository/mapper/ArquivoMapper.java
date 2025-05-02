package br.com.webpublico.repository.mapper;

import br.com.webpublico.domain.ArquivoDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ArquivoMapper implements RowMapper<ArquivoDTO> {
    @Override
    public ArquivoDTO mapRow(ResultSet resultSet, int i) throws SQLException {
        ArquivoDTO dto = new ArquivoDTO();
        dto.setId(resultSet.getLong("ID"));
        dto.setDescricao(resultSet.getString("DESCRICAO"));
        dto.setMimeType(resultSet.getString("MIMETYPE"));
        dto.setNome(resultSet.getString("NOME"));
        dto.setTamanho(resultSet.getLong("TAMANHO"));
        return dto;
    }
}
