package br.com.webpublico.web.rest;

import br.com.webpublico.domain.DadosPessoaisDTO;
import br.com.webpublico.service.DadosPessoaisService;
import br.com.webpublico.web.rest.dto.CepCorreio;
import br.com.webpublico.web.rest.dto.EnderecoDTO;
import com.codahale.metrics.annotation.Timed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class PessoaResource {

    @Autowired
    DadosPessoaisService dadosPessoaisService;

    @RequestMapping(value = "/pessoa_por_cpfCnpj/{cpfCnpj}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DadosPessoaisDTO> getPorCpfCnpj(@PathVariable String cpfCnpj) {
        return Optional.ofNullable(dadosPessoaisService.findPessoaByCpfCnpj(cpfCnpj))
                .map(data -> new ResponseEntity<>(
                        data,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.OK));
    }

    @RequestMapping(value = "/cep/{cep}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CepCorreio> get(@PathVariable String cep) {
        CepCorreio cepCorreio = new RestTemplate().getForObject("https://viacep.com.br/ws/" + cep + "/json/", CepCorreio.class);
        return Optional.ofNullable(cepCorreio)
                .map(end -> new ResponseEntity<>(
                        end,
                        HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.OK));
    }
}
