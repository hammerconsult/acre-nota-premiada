package br.com.webpublico.web.rest;

import br.com.webpublico.domain.ComunicadoDTO;
import br.com.webpublico.service.ComunicadoService;
import com.codahale.metrics.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping("/api")
public class ComunicadoResource {

    @Autowired
    private ComunicadoService service;

    @GetMapping("/externo/ultimo-comunicado")
    @Timed
    public ResponseEntity<ComunicadoDTO> getUltimoComunicado() throws Exception {
        return ResponseEntity.ok(service.getUltimoComunicado());
    }


    @GetMapping("/externo/documento-comunicado/{idDetentor}")
    @Timed
    public void getDocumentoComunicado(HttpServletResponse response, @PathVariable Long idDetentor) throws Exception {
        service.getDocumentoComunicado(response, idDetentor);
    }
}
