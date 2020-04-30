package com.itmo.wst;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
@PropertySource("classpath:custom.properties")
public class WineConfiguration {

    @Value("${wine.server.address}")
    public String serverAddress;

    @Value("${wine.client.address}")
    public String clientAddress;

    @Value("${wine.context.path}")
    public String contextPath;

    @Bean
    public Jaxb2Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        // this package must match the package in the <generatePackage> specified in
        // pom.xml
        marshaller.setContextPath(contextPath);
        return marshaller;
    }

}
