package br.com.webpublico.service;

import br.com.webpublico.domain.MunicipioDTO;
import br.com.webpublico.repository.MunicipioJDBCRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class MunicipioService implements Serializable {

    @Autowired
    private MunicipioJDBCRepository municipioJDBCRepository;

    public MunicipioDTO findById(Long id) {
        return municipioJDBCRepository.findById(id);
    }

    public MunicipioDTO findByCodigoIBGE(String codigoIBGE) {
        return municipioJDBCRepository.findByCodigoIBGE(codigoIBGE);
    }

    public MunicipioDTO buscarPorNomeAndEstado(String nome, String estado) {
        return municipioJDBCRepository.buscarPorNomeAndEstado(nome, estado);
    }
}
