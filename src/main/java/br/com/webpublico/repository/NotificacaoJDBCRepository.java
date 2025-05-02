package br.com.webpublico.repository;

import br.com.webpublico.service.NotificacaoDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

@Repository
public class NotificacaoJDBCRepository implements Serializable {

    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private IdJDBCRepository idJDBCRepository;

    public void inserir(NotificacaoDTO notificacao) {
        notificacao.setId(idJDBCRepository.getId());
        jdbcTemplate.batchUpdate("INSERT INTO NOTIFICACAO " +
                " (ID, TITULO, DESCRICAO, LINK, GRAVIDADE, VISUALIZADO, TIPONOTIFICACAO, " +
                " CRIADOEM) " +
                " VALUES(?, ?, ?, ?, ?, ?, ?, ?) ", notificacao);
    }
}
