package br.com.webpublico.service;

import br.com.webpublico.domain.ImagemUsuarioDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@Service
public class ImagemService extends AbstractWPService {

    private final Logger log = LoggerFactory.getLogger(ImagemService.class);


    @Override
    public String getTableName() {
        return "Arquivo";
    }

    @Override
    public String getDefaltSearchFields() {
        return "nome";
    }

    public void updateImagemUsuario(ImagemUsuarioDTO imagemDTO) {
        UriComponentsBuilder uri = UriComponentsBuilder.fromUriString(urlWebpublico + "/usuario/imagem");
        String url = uri.toUriString();
        restTemplate.postForEntity(url, imagemDTO, String.class);
    }


    public void updateImagemPessoa(ImagemUsuarioDTO imagemDTO) {
        UriComponentsBuilder uri = UriComponentsBuilder.fromUriString(urlWebpublico + "/pessoa/imagem");
        String url = uri.toUriString();
        restTemplate.postForEntity(url, imagemDTO, String.class);
    }

    public ResponseEntity<ImagemUsuarioDTO> getImagemUsuario(String login) {
        try {
            UriComponentsBuilder uri = UriComponentsBuilder.fromUriString(urlWebpublico + "/usuario/imagem")
                .queryParam("login", login);
            String url = uri.toUriString();
            return restTemplate.exchange(url, HttpMethod.GET, null, ImagemUsuarioDTO.class);
        } catch (Exception e) {
            log.error("Não foi possível recuperar a imagem do usuário {}", login);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    public ResponseEntity<ImagemUsuarioDTO> getImagemPessoa(Long id) {
        try {
            UriComponentsBuilder uri = UriComponentsBuilder.fromUriString(urlWebpublico + "/pessoa/imagem")
                .queryParam("id", id);
            String url = uri.toUriString();
            return restTemplate.exchange(url, HttpMethod.GET, null, ImagemUsuarioDTO.class);
        } catch (Exception e) {
            log.error("Não foi possível recuperar a imagem do usuário {}", id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @Override
    public ParameterizedTypeReference<List<ImagemUsuarioDTO>> getResponseTypeList() {
        return new ParameterizedTypeReference<List<ImagemUsuarioDTO>>() {
        };
    }

    @Override
    public ParameterizedTypeReference<ImagemUsuarioDTO> getResponseType() {
        return new ParameterizedTypeReference<ImagemUsuarioDTO>() {
        };
    }
}
