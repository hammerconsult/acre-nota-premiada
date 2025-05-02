package br.com.webpublico.service;

import br.com.webpublico.domain.ArquivoDTO;
import br.com.webpublico.domain.CampanhaDTO;
import br.com.webpublico.repository.CampanhaNfseJDBCRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Service
public class CampanhaNfseService implements Serializable {
    private static Logger log = LoggerFactory.getLogger(CampanhaNfseService.class);

    @Autowired
    CampanhaNfseJDBCRepository repository;

    public CampanhaDTO findById(Long id) throws Exception {
        return repository.findById(id);
    }

    public List<CampanhaDTO> buscarTodasCampanhas() throws Exception {
        return repository.buscarCampanhas(null, new ArrayList<>(), "");
    }

    public List<ArquivoDTO> getAllArquivos() {
        return repository.findAllArquvivos();
    }
}
