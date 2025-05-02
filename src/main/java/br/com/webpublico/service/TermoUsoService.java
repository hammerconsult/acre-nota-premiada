package br.com.webpublico.service;

import br.com.webpublico.domain.BooleanDTO;
import br.com.webpublico.domain.TermoUsoDTO;
import br.com.webpublico.domain.UsuarioNotaPremiadaDTO;
import br.com.webpublico.repository.TermoUsoJDBCRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;

@Service
@Transactional
public class TermoUsoService implements Serializable {

    @Autowired
    TermoUsoJDBCRepository repository;

    public TermoUsoDTO buscarTermoUsoVigente() {
        return repository.buscarTermoUsoVigente();
    }

    public BooleanDTO hasTermoParaAceite(UsuarioNotaPremiadaDTO usuario) {
        return repository.hasTermoParaAceite(usuario);
    }

    public void aceitarTermoUso(TermoUsoDTO termoUso, UsuarioNotaPremiadaDTO usuario) {
        repository.aceitarTermoUso(termoUso, usuario);
    }

}
