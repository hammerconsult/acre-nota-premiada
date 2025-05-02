package br.com.webpublico.web.rest;

import br.com.webpublico.domain.SorteioDTO;
import br.com.webpublico.service.InformacaoGeralDTO;
import br.com.webpublico.service.SorteioNfseService;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/externo")
public class ExternoResource {
    private final Logger log = LoggerFactory.getLogger(ExternoResource.class);

    @Autowired
    SorteioNfseService service;

    @GetMapping("/informacoes-nota-premiada")
    @Timed
    public ResponseEntity<InformacaoGeralDTO> buscarInformacoesNotaPremiada() throws Exception {
        InformacaoGeralDTO info = service.buscarInformacoesNotaPremiada();
        return new ResponseEntity(info, HttpStatus.OK);
    }

    @GetMapping("/proximo-sorteio")
    @Timed
    public ResponseEntity<SorteioDTO> buscarProximoSorteio() {
        SorteioDTO proximoSorteio = service.buscarProximoSorteio();
        return new ResponseEntity<>(proximoSorteio, HttpStatus.OK);
    }

}
