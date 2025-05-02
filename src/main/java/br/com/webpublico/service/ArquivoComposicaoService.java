package br.com.webpublico.service;

import br.com.webpublico.domain.ArquivoComposicaoDTO;
import br.com.webpublico.repository.ArquivoComposicaoJDBCRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service
public class ArquivoComposicaoService implements Serializable {

    @Autowired
    private ArquivoComposicaoJDBCRepository arquivoComposicaoJDBCRepository;
    @Autowired
    private ArquivoService arquivoService;

    public ArquivoComposicaoDTO findById(Long id) {
        ArquivoComposicaoDTO dto = arquivoComposicaoJDBCRepository.findById(id);
        if (dto.getArquivo() != null) {
            dto.setArquivo(arquivoService.findById(dto.getArquivo().getId()));
        }
        return dto;
    }

    public List<ArquivoComposicaoDTO> findByDetentor(Long idDetentor) {
        List<ArquivoComposicaoDTO> dtos = arquivoComposicaoJDBCRepository.findByIdDetentor(idDetentor);
        if (dtos != null) {
            for (ArquivoComposicaoDTO dto : dtos) {
                dto.setArquivo(arquivoService.findById(dto.getArquivo().getId()));
            }
        }
        return dtos;
    }

}
