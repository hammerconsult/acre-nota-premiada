package br.com.webpublico.service;

import br.com.webpublico.domain.ArquivoDTO;
import br.com.webpublico.repository.ArquivoJDBCRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class ArquivoService implements Serializable {

    @Autowired
    private ArquivoJDBCRepository arquivoJDBCRepository;

    public ArquivoDTO findById(Long id) {
        ArquivoDTO dto = arquivoJDBCRepository.findById(id);
        dto.setPartes(arquivoJDBCRepository.findPartes(dto.getId()));
        return dto;
    }
}
