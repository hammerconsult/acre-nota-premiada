package br.com.webpublico.web.rest;

import br.com.webpublico.domain.UsuarioNotaPremiadaDTO;
import br.com.webpublico.service.UsuarioNotaPremiadaService;
import br.com.webpublico.web.rest.util.PaginationUtil;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URISyntaxException;
import java.util.List;

/**
 * REST controller for managing users.
 */
@RestController
@RequestMapping("/api")
public class UsuarioNotaPremiadaResource {

    private final Logger log = LoggerFactory.getLogger(UsuarioNotaPremiadaResource.class);

    @Inject
    private UsuarioNotaPremiadaService usuarioNotaPremiadaService;


    /**
     * GET  /users/:login -> get the "login" user.
     */
    @RequestMapping(value = "/users/{login}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    ResponseEntity<UsuarioNotaPremiadaDTO> getUser(@PathVariable String login) {
        log.debug("REST request to get User : {}", login);
        return new ResponseEntity<>(usuarioNotaPremiadaService.findByLogin(login), HttpStatus.OK);
    }

    @RequestMapping(value = "/user-from-login",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    ResponseEntity<UsuarioNotaPremiadaDTO> getUserFromLogin(@RequestParam String login) {
        log.debug("REST request to get User : {}", login);
        return new ResponseEntity<>(usuarioNotaPremiadaService.findByLogin(login), HttpStatus.OK);
    }

}
