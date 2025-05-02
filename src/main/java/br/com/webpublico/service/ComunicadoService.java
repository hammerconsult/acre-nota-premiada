package br.com.webpublico.service;

import br.com.webpublico.domain.ComunicadoDTO;
import br.com.webpublico.repository.ComunicadoJDBCRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Serializable;

@Service
public class ComunicadoService implements Serializable {

    @Autowired
    private ComunicadoJDBCRepository repository;
    @Autowired
    private DetentorArquivoComposicaoService detentorArquivoComposicaoService;

    public ComunicadoDTO getUltimoComunicado() throws Exception {
        return repository.getUltimoComunicado();
    }

    public void getDocumentoComunicado(HttpServletResponse response, Long idDetentor) throws IOException {
        byte[] bytes = detentorArquivoComposicaoService.getByteArrayDosDados(idDetentor);
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=comunicado.pdf");
        response.setContentLength(bytes.length);
        ServletOutputStream outputStream = response.getOutputStream();
        outputStream.write(bytes, 0, bytes.length);
        outputStream.flush();
        outputStream.close();
    }
}
