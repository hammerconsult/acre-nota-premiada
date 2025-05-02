package br.com.webpublico.repository.mapper;

import br.com.webpublico.domain.AssuntoDTO;
import br.com.webpublico.domain.PerguntasRespostasDTO;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PerguntasRespostasMapper implements RowMapper<PerguntasRespostasDTO> {

    @Override
    public PerguntasRespostasDTO mapRow(ResultSet resultSet, int i) throws SQLException {
        PerguntasRespostasDTO dto = new PerguntasRespostasDTO();
        dto.setId(resultSet.getLong("ID"));
        dto.setAssunto(new AssuntoDTO());
        dto.getAssunto().setId(resultSet.getLong("ASSUNTO_ID"));
        dto.getAssunto().setDescricao(resultSet.getString("ASSUNTO_DESCRICAO"));
        dto.getAssunto().setHabilitarExibicao(resultSet.getBoolean("ASSUNTO_HABILITAR_EXIBICAO"));
        dto.getAssunto().setOrdem(resultSet.getInt("ASSUNTO_ORDEM"));
        dto.setPergunta(resultSet.getString("PERGUNTA"));
        dto.setResposta(resultSet.getString("RESPOSTA"));
        dto.setHabilitarExibicao(resultSet.getBoolean("HABILITAR_EXIBICAO"));
        dto.setOrdem(resultSet.getInt("ORDEM"));
        return dto;
    }
}
