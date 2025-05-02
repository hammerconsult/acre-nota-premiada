package br.com.webpublico.web.rest;

import br.com.webpublico.domain.AceiteTermoUsoDTO;
import br.com.webpublico.domain.BooleanDTO;
import br.com.webpublico.domain.TermoUsoDTO;
import br.com.webpublico.domain.UsuarioNotaPremiadaDTO;
import br.com.webpublico.service.TermoUsoService;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class TermoUsoResource {
    private final Logger log = LoggerFactory.getLogger(TermoUsoResource.class);

    @Autowired
    TermoUsoService service;

    @GetMapping("/termo-uso/vigente")
    public ResponseEntity<TermoUsoDTO> buscarTermoUsoVigente() {
        return new ResponseEntity<>(service.buscarTermoUsoVigente(), HttpStatus.OK);
    }

    @PostMapping("/termo-uso/para-aceite")
    public ResponseEntity<BooleanDTO> hasTermoUsoParaAceite(@RequestBody UsuarioNotaPremiadaDTO usuario) {
        return new ResponseEntity<>(service.hasTermoParaAceite(usuario), HttpStatus.OK);
    }

    @PostMapping("/termo-uso/aceitar")
    public ResponseEntity<Void> aceitarTermoUso(@RequestBody AceiteTermoUsoDTO aceite) {
        service.aceitarTermoUso(aceite.getTermoUso(), aceite.getUsuario());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
