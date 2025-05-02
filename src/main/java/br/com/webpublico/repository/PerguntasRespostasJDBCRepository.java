package br.com.webpublico.repository;

import br.com.webpublico.domain.PerguntasRespostasDTO;
import br.com.webpublico.repository.mapper.PerguntasRespostasMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public class PerguntasRespostasJDBCRepository implements Serializable {
    
    @Autowired
    JdbcTemplate jdbcTemplate;
    
    public List<PerguntasRespostasDTO> buscarPerguntasRespostas() {
        return jdbcTemplate.query(" SELECT PR.ID, " +
                "       A.ID AS ASSUNTO_ID, " +
                "       A.DESCRICAO AS ASSUNTO_DESCRICAO, " +
                "       A.HABILITAREXIBICAO AS ASSUNTO_HABILITAR_EXIBICAO, " +
                "       A.ORDEM AS ASSUNTO_ORDEM, " +
                "       PR.PERGUNTA AS PERGUNTA, " +
                "       PR.RESPOSTA AS RESPOSTA, " +
                "       PR.ORDEM AS ORDEM, " +
                "       PR.HABILITAREXIBICAO AS HABILITAR_EXIBICAO " +
                "   FROM PERGUNTASRESPOSTAS PR " +
                "  INNER JOIN ASSUNTONFSE A ON A.ID = PR.ASSUNTO_ID " +
                " WHERE A.HABILITAREXIBICAO = 1 " +
                "   AND PR.HABILITAREXIBICAO = 1 " +
                "   AND PR.TIPOPERGUNTASRESPOSTAS = 'CAMPANHA' " +
                " ORDER BY A.ORDEM, PR.ORDEM ", new PerguntasRespostasMapper());
    }
}
