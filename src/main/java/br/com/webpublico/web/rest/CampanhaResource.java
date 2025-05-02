package br.com.webpublico.web.rest;

import br.com.webpublico.domain.ArquivoDTO;
import br.com.webpublico.service.CampanhaNfseService;
import com.codahale.metrics.annotation.Timed;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.inject.Inject;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CampanhaResource {

    @Inject
    private CampanhaNfseService service;


    @GetMapping("/externo/legislacoes-para-exibicao")
    @Timed
    public ResponseEntity<List<ArquivoDTO>> buscarLegislacoesParaExibicao() {
        return new ResponseEntity<>(service.getAllArquivos(), HttpStatus.OK);
    }
}
