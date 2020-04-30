package com.itmo.wst;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
public class WineConfiguration {

	@Bean
	public Jaxb2Marshaller marshaller() {
		Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
		// this package must match the package in the <generatePackage> specified in
		// pom.xml
		marshaller.setContextPath("com.itmo.wst.wsdl");
		return marshaller;
	}

	@Bean
	public WineClient wineClient(Jaxb2Marshaller marshaller) {
		WineClient client = new WineClient();
		client.setDefaultUri("http://localhost:8090/ws");
		client.setMarshaller(marshaller);
		client.setUnmarshaller(marshaller);
		return client;
	}

}
