package br.com.webpublico.repository.mapper;

import br.com.webpublico.domain.MunicipioDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class MunicipioMapper implements RowMapper<MunicipioDTO> {
    @Override
    public MunicipioDTO mapRow(ResultSet resultSet, int i) throws SQLException {
        MunicipioDTO dto = new MunicipioDTO();
        dto.setId(resultSet.getLong("ID"));
        dto.setCodigo(resultSet.getString("CODIGO"));
        dto.setNome(resultSet.getString("NOME"));
        dto.setEstado(resultSet.getString("ESTADO"));
        return dto;
    }
}
