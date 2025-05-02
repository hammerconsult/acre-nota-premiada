package br.com.webpublico.service;

import br.com.webpublico.domain.PerguntasRespostasDTO;
import br.com.webpublico.repository.PerguntasRespostasJDBCRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service
public class PerguntasRespostasService implements Serializable {

    private final Logger log = LoggerFactory.getLogger(PerguntasRespostasService.class);

    @Autowired
    private PerguntasRespostasJDBCRepository repository;

    public List<PerguntasRespostasDTO> buscarPerguntasRespostasParaExibicao() {
        return repository.buscarPerguntasRespostas();
    }
}
