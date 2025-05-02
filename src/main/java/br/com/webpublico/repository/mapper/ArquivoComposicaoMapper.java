package br.com.webpublico.repository.mapper;

import br.com.webpublico.domain.ArquivoComposicaoDTO;
import br.com.webpublico.domain.ArquivoDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ArquivoComposicaoMapper implements RowMapper<ArquivoComposicaoDTO> {
    @Override
    public ArquivoComposicaoDTO mapRow(ResultSet resultSet, int i) throws SQLException {
        ArquivoComposicaoDTO dto = new ArquivoComposicaoDTO();
        dto.setId(resultSet.getLong("ID"));
        dto.setArquivo(new ArquivoDTO());
        dto.getArquivo().setId(resultSet.getLong("ARQUIVO_ID"));
        dto.setDataUpload(resultSet.getDate("DATAUPLOAD"));
        return dto;
    }
}
