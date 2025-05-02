package br.com.webpublico.service;

import br.com.webpublico.domain.DadosPessoaisDTO;
import br.com.webpublico.repository.DadosPessoaisJDBCRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DadosPessoaisService {

    @Autowired
    DadosPessoaisJDBCRepository dadosPessoaisJDBCRepository;

    public DadosPessoaisDTO salvar(DadosPessoaisDTO dadosPessoais) {
        if (dadosPessoais.getId() == null) {
            dadosPessoaisJDBCRepository.inserir(dadosPessoais);
        } else {
            dadosPessoaisJDBCRepository.update(dadosPessoais);
        }
        return dadosPessoais;
    }

    public DadosPessoaisDTO findDadosPessoaisNfseById(Long id) {
        return dadosPessoaisJDBCRepository.findDadosPessoaisNfseById(id);
    }

    public DadosPessoaisDTO findPessoaByCpfCnpj(String cpfCnpj) {
        return dadosPessoaisJDBCRepository.findPessoaByCpfCnpj(cpfCnpj);
    }
}
