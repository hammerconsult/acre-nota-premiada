package br.com.webpublico.repository;

import br.com.webpublico.domain.FaleConoscoNfseDTO;
import br.com.webpublico.domain.TermoUsoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.Serializable;
import java.util.List;

@Repository
public class FaleConoscoJDBCRepository implements Serializable {

    @Autowired
    JdbcTemplate jdbcTemplate;
    @Autowired
    IdJDBCRepository idJDBCRepository;

    public FaleConoscoNfseDTO inserir(FaleConoscoNfseDTO dto) {
        dto.setId(idJDBCRepository.getId());
        jdbcTemplate.batchUpdate(" INSERT INTO FALECONOSCONFSE (ID, SISTEMA, SITUACAO, TIPO, ASSUNTO, DADOSRECLAMANTE_ID," +
                " DADOSRECLAMADO_ID, DATAENVIO, MENSAGEM, DATASERVICO, DESCRICAOSERVICO, VALORSERVICO, NUMERONOTASERVICO) " +
                " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)  ", dto);
        return dto;
    }

    public List<FaleConoscoNfseDTO> buscarReclamacoesPorCpf(String cpf) {
        return jdbcTemplate.query(" SELECT " +
                        " F.ID, " +
                        " F.SITUACAO," +
                        " F.TIPO," +
                        " F.ASSUNTO," +
                        " F.DADOSRECLAMANTE_ID," +
                        " F.DADOSRECLAMADO_ID," +
                        " F.DATAENVIO, " +
                        " F.MENSAGEM, " +
                        " F.DATASERVICO, " +
                        " F.DESCRICAOSERVICO, " +
                        " F.VALORSERVICO, " +
                        " F.NUMERONOTASERVICO, " +
                        " F.RESPOSTA " +
                        " FROM FALECONOSCONFSE F " +
                        " INNER JOIN DADOSPESSOAISNFSE DP ON DP.ID = F.DADOSRECLAMANTE_ID " +
                        " WHERE F.SISTEMA = 'NOTA_PREMIADA' " +
                        "   AND DP.CPFCNPJ = ? ",
                new FaleConoscoNfseDTO(), cpf);

    }

}
