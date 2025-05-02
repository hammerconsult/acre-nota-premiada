package br.com.webpublico.web.rest;

import br.com.webpublico.config.ConnectionUtil;
import com.codahale.metrics.annotation.Timed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;

@RestController
@RequestMapping("/api/externo")
public class ArquivoResource {

    private final Logger logger = LoggerFactory.getLogger(ArquivoResource.class);
    protected String urlWebpublico = "";
    @Autowired
    private RestTemplate restTemplate;

    @PostConstruct
    public void init() {
        this.urlWebpublico = ConnectionUtil.getURLNfse();
    }


    @GetMapping("/arquivo/{id}")
    @Timed
    public HttpEntity<byte[]> downloadArquivo(@PathVariable Long id) {
        HttpEntity<byte[]> response = null;
        try {
            String url = urlWebpublico + "/arquivo/" + id.toString();
            response = restTemplate.exchange(url, HttpMethod.GET, null, byte[].class);
        } catch (HttpClientErrorException ex) {
            logger.error("Erro ao fazer o download do arquivo. {}", ex.getMessage());
            logger.debug("Detalhes do erro ao fazer o download do arquivo. ", ex);
        }
        return response;
    }
}
