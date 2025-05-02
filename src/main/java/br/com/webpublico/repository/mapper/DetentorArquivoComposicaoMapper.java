package br.com.webpublico.repository.mapper;

import br.com.webpublico.domain.ArquivoComposicaoDTO;
import br.com.webpublico.domain.DetentorArquivoComposicaoDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class DetentorArquivoComposicaoMapper implements RowMapper<DetentorArquivoComposicaoDTO> {
    @Override
    public DetentorArquivoComposicaoDTO mapRow(ResultSet resultSet, int i) throws SQLException {
        DetentorArquivoComposicaoDTO dto = new DetentorArquivoComposicaoDTO();
        dto.setId(resultSet.getLong("ID"));
        if (resultSet.getLong("ARQUIVOCOMPOSICAO_ID") != 0) {
            dto.setArquivo(new ArquivoComposicaoDTO());
            dto.getArquivo().setId(resultSet.getLong("ARQUIVOCOMPOSICAO_ID"));
        }
        return dto;
    }
}
