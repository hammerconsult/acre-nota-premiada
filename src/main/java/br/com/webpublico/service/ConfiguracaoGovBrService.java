package br.com.webpublico.service;

import br.com.webpublico.domain.ConfiguracaoGovBrDTO;
import br.com.webpublico.repository.ConfiguracaoGovBrJDBCRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ConfiguracaoGovBrService {

    @Autowired
    private ConfiguracaoGovBrJDBCRepository repository;
    private ConfiguracaoGovBrDTO configuracaoGovBr;

    public ConfiguracaoGovBrDTO getConfiguracaoGovBr() {
        if (configuracaoGovBr == null) {
            configuracaoGovBr = repository.getConfiguracaoGovBr();
        }
        return configuracaoGovBr;
    }
}
