package br.com.webpublico.web.rest;

import br.com.webpublico.domain.ImagemUsuarioDTO;
import br.com.webpublico.security.SecurityUtils;
import br.com.webpublico.service.ImagemService;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/api")
public class ImagemResource {

    private final Logger log = LoggerFactory.getLogger(ImagemResource.class);
    @Inject
    private ImagemService imagemService;

    @RequestMapping(value = "/imagem",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<String> updateImagem(@RequestBody ImagemUsuarioDTO imagemDTO) throws URISyntaxException {
        try {
            imagemService.updateImagemPessoa(imagemDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("Ocorreu um erro ao inserir uma logo: {}", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @RequestMapping(value = "/imagem-pessoa/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ImagemUsuarioDTO> getImagemFromPessoa(@PathVariable Long id) {
        return imagemService.getImagemPessoa(id);

    }

    @RequestMapping(value = "/imagem-usuario",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ImagemUsuarioDTO> getImagemFromuser() {
        return imagemService.getImagemUsuario(SecurityUtils.getCurrentLogin());
    }

    @RequestMapping(value = "/imagem-usuario",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<String> updateImagemUsuario(@RequestBody ImagemUsuarioDTO imagemDTO) throws URISyntaxException {
        try {
            imagemService.updateImagemUsuario(imagemDTO);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (Exception e) {
            log.error("Ocorreu um erro ao inserir uma logo: {}", e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
