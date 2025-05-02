package br.com.webpublico.service;


import br.com.webpublico.config.ConnectionUtil;
import br.com.webpublico.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import java.util.List;

public abstract class AbstractWPService<T> {

    private final Logger log = LoggerFactory.getLogger(AbstractWPService.class);
    protected String urlWebpublico = "";
    protected String urlWebpublicoTributario = "";
    RestTemplate restTemplate = new RestTemplate();

    @PostConstruct
    public void init() {
        this.urlWebpublico = ConnectionUtil.getURLNfse();
        this.urlWebpublicoTributario = ConnectionUtil.getURLTributario();
    }

    public ResponseEntity<List<T>> findAll(Pageable pageable) {
        return findByQuery("", pageable);
    }

    public ResponseEntity<List<T>> findByQuery(String query) {
        return findByQuery(query, null);
    }

    public ResponseEntity<List<T>> findByQuery(String query, Pageable pageable) {
        if (pageable == null) {
            pageable = PaginationUtil.generatePageRequest(0, 100);
        }
        String url = PaginationUtil.addParamsToUrl(urlWebpublico + "/pesquisa-generica",
            pageable, query, getTableName(), getDefaltSearchFields());
        return restTemplate.exchange(url, HttpMethod.GET, null, getResponseTypeList());
    }

    public ResponseEntity<T> findOne(Long id) {
        String url = urlWebpublico + "/pesquisa-generica/registro";
        url = UriComponentsBuilder.fromUriString(url).queryParam("table", getTableName())
            .queryParam("id", id).toUriString();
        return restTemplate.exchange(url, HttpMethod.GET, null, getResponseType());
    }

    public T findByAtribute(String name, Object value) {
        try {
            Pageable pageable = PaginationUtil.generatePageRequest(0, 1);
            String url = PaginationUtil.addParamsToUrl(urlWebpublico + "/pesquisa-generica/first",
                pageable, value.toString(), getTableName(), name);
            return restTemplate.exchange(url, HttpMethod.GET, null, getResponseType()).getBody();
        } catch (HttpClientErrorException e) {
            return null;
        }
    }

    public abstract String getTableName();

    public abstract String getDefaltSearchFields();


    public abstract ParameterizedTypeReference<List<T>> getResponseTypeList();

    public abstract ParameterizedTypeReference<T> getResponseType();


}
