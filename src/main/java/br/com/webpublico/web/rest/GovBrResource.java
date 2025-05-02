package br.com.webpublico.web.rest;

import br.com.webpublico.domain.UsuarioNotaPremiadaDTO;
import br.com.webpublico.service.GovBrService;
import com.codahale.metrics.annotation.Timed;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/externo")
public class GovBrResource {

    private final GovBrService govBrService;

    public GovBrResource(GovBrService govBrService) {
        this.govBrService = govBrService;
    }

    @RequestMapping(value = "/gov-br/autenticar/{code}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<OAuth2AccessToken> autenticar(@PathVariable String code) {
        UsuarioNotaPremiadaDTO usuarioNotaPremiadaDTO = govBrService.getUsuarioViaGovBr(code);
        if (usuarioNotaPremiadaDTO != null) {
            return ResponseEntity.ok(govBrService.generateToken(usuarioNotaPremiadaDTO));
        }
        return ResponseEntity.ok(null);
    }
}
