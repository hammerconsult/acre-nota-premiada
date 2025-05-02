package br.com.webpublico.web.rest;

import br.com.webpublico.domain.PremioSorteioDTO;
import br.com.webpublico.service.PremioService;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by William on 21/01/2019.
 */
@RestController
@RequestMapping("/api")
public class PremioResource {

    private final Logger log = LoggerFactory.getLogger(PremioResource.class);

    @Autowired
    PremioService premioService;

    @GetMapping("/externo/premios-por-sorteio")
    @Timed
    public ResponseEntity<List<PremioSorteioDTO>> buscarPremiosPorSorteio(@RequestParam Long idSorteio) throws Exception {
        return new ResponseEntity<>(premioService.buscarPremiosPorSorteio(idSorteio), HttpStatus.OK);
    }

    @GetMapping("/externo/premios")
    @Timed
    public ResponseEntity<List<PremioSorteioDTO>> buscarTodosPremios() throws Exception {
        return new ResponseEntity<>(premioService.buscarTodosPremios(), HttpStatus.OK);
    }
}
