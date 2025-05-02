package br.com.webpublico.service;

import br.com.webpublico.repository.NotificacaoJDBCRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

@Service
@Transactional
public class NotificacaoService implements Serializable {

    @Autowired
    private NotificacaoJDBCRepository notificacaoJDBCRepository;

    public void inserir(NotificacaoDTO notificacao) {
        notificacaoJDBCRepository.inserir(notificacao);
    }
}
