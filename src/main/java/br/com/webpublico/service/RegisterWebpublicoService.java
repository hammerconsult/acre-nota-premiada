package br.com.webpublico.service;

import br.com.webpublico.config.ConnectionUtil;
import org.apache.catalina.connector.Connector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.embedded.EmbeddedWebApplicationContext;
import org.springframework.boot.context.embedded.tomcat.TomcatEmbeddedServletContainer;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.net.InetAddress;

@Service
public class RegisterWebpublicoService {

    private static final Logger logger = LoggerFactory.getLogger(RegisterWebpublicoService.class);
    protected String urlWebpublico = "";
    @Inject
    private EmbeddedWebApplicationContext appContext;

    @PostConstruct
    public void init() {
        this.urlWebpublico = ConnectionUtil.getURLNfse();
    }

    public String getBaseUrl() {
        try {
            Connector connector = ((TomcatEmbeddedServletContainer) appContext.getEmbeddedServletContainer()).getTomcat().getConnector();
            String scheme = connector.getScheme();
            String ip = InetAddress.getLocalHost().getHostAddress();
            int port = connector.getPort();
            String contextPath = appContext.getServletContext().getContextPath();
            return scheme + "://" + ip + ":" + port + contextPath;
        } catch (Exception e) {
            return "unknown";
        }
    }

    @Async
    public void register() {
        String baseUrl = getBaseUrl();
        logger.info("REGISTRANDO A URL NO WEBPUBLICO {}", baseUrl);
        UriComponentsBuilder uri = UriComponentsBuilder.fromUriString(urlWebpublico + "/register-app")
            .queryParam("url", baseUrl);
        String url = uri.toUriString();
        new RestTemplate().exchange(url, HttpMethod.GET, null, Void.class);
    }

}
