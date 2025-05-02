package br.com.webpublico.service;

import br.com.webpublico.domain.ArquivoParteDTO;
import br.com.webpublico.domain.DetentorArquivoComposicaoDTO;
import br.com.webpublico.repository.DetentorArquivoComposicaoJDBCRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.List;

@Service
public class DetentorArquivoComposicaoService implements Serializable {
    private final Logger log = LoggerFactory.getLogger(DetentorArquivoComposicaoService.class);

    @Autowired
    private DetentorArquivoComposicaoJDBCRepository detentorArquivoComposicaoJDBCRepository;
    @Autowired
    private ArquivoComposicaoService arquivoComposicaoService;

    public DetentorArquivoComposicaoDTO findById(Long id) {
        DetentorArquivoComposicaoDTO dto = detentorArquivoComposicaoJDBCRepository.findById(id);
        if (dto.getArquivo() != null) {
            dto.setArquivo(arquivoComposicaoService.findById(dto.getArquivo().getId()));
        }
        dto.setArquivos(arquivoComposicaoService.findByDetentor(dto.getId()));
        return dto;
    }

    public byte[] getByteArrayDosDados(Long idDetentor) {
        try {
            List<ArquivoParteDTO> partes = findById(idDetentor).getArquivo().getArquivo().getPartes();
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            for (ArquivoParteDTO a : partes) {
                buffer.write(a.getDados());
            }
            return buffer.toByteArray();
        } catch (Exception ex) {
            System.out.println("Erro ao recuperar o arquivo " + ex.getMessage());
        }
        return null;
    }
}
