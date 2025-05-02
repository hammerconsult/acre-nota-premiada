package br.com.webpublico.web.rest;

import br.com.webpublico.domain.UsuarioNotaPremiadaDTO;
import br.com.webpublico.service.UsuarioNotaPremiadaService;
import br.com.webpublico.web.rest.dto.TrocarSenhaDTO;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Optional;

/**
 * REST controller for managing the current user's account.
 */
@RestController
@RequestMapping("/api")
public class AccountResource {

    final Logger log = LoggerFactory.getLogger(AccountResource.class);

    @Inject
    UsuarioNotaPremiadaService usuarioNotaPremiadaService;

    @RequestMapping(value = "/register",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UsuarioNotaPremiadaDTO> registerAccount(@RequestBody UsuarioNotaPremiadaDTO usuario) {
        return new ResponseEntity<>(usuarioNotaPremiadaService.registrarUsuario(usuario), HttpStatus.OK);
    }

    @RequestMapping(value = "/authenticate",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public String isAuthenticated(HttpServletRequest request) {
        log.debug("REST request to check if the current user is authenticated");
        return request.getRemoteUser();
    }

    @RequestMapping(value = "/account",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UsuarioNotaPremiadaDTO> getAccount() {
        return Optional.ofNullable(usuarioNotaPremiadaService.getUserWithAuthorities())
                .map(user -> new ResponseEntity<>(user, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR));
    }

    @RequestMapping(value = "/account",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> saveAccount(@RequestBody UsuarioNotaPremiadaDTO usuario) {
        usuarioNotaPremiadaService.alterarUsuario(usuario);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/account/change_password",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> changePassword(@RequestBody TrocarSenhaDTO trocarSenhaDTO) {
        usuarioNotaPremiadaService.trocarSenha(trocarSenhaDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/account/reset_password/init",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<?> requestPasswordReset(@RequestBody String cpf) {
        usuarioNotaPremiadaService.requestPasswordReset(cpf);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/account/reset_password/finish",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<String> finishPasswordReset(@RequestParam(value = "key") String key,
                                                      @RequestParam(value = "newPassword") String newPassword) {
        usuarioNotaPremiadaService.completePasswordReset(key, newPassword);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
