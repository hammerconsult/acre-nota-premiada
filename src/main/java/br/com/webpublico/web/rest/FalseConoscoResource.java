package br.com.webpublico.web.rest;

import br.com.webpublico.domain.FaleConoscoNfseDTO;
import br.com.webpublico.service.FaleConoscoService;
import com.codahale.metrics.annotation.Timed;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/externo")
public class FalseConoscoResource {

    @Inject
    FaleConoscoService faleConoscoService;


    @RequestMapping(value = "/fale-conosco",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FaleConoscoNfseDTO> createFaleconosco(@RequestBody FaleConoscoNfseDTO dto) throws URISyntaxException {
        FaleConoscoNfseDTO result = faleConoscoService.inserir(dto);
        return ResponseEntity.created(new URI("/api/fale-conosco/")).body(result);
    }


    @RequestMapping(value = "/fale-conosco-por-cpf/{cpf}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<FaleConoscoNfseDTO>> getPorCpfCnpj(@PathVariable String cpf) {
        return new ResponseEntity<>(
                faleConoscoService.buscarReclamacoesPorCpf(cpf),
                HttpStatus.OK);
    }


}
