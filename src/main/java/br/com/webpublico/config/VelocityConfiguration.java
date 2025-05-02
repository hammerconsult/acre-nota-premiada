package br.com.webpublico.config;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.VelocityException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.ui.velocity.VelocityEngineFactory;

import java.io.IOException;
import java.util.Properties;

@Configuration
public class VelocityConfiguration {

    private final Logger log = LoggerFactory.getLogger(VelocityConfiguration.class);

    @Bean
    public VelocityEngine velocityEngine() throws VelocityException, IOException {
        log.debug("Configurando velocity ....");
        VelocityEngineFactory factory = new VelocityEngineFactory();
        Properties props = new Properties();
        props.put("resource.loader", "class");
        props.put("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
        factory.setVelocityProperties(props);
        return factory.createVelocityEngine();
    }
}
