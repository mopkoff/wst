package com.itmo.wst;

import java.math.BigInteger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.itmo.wst.wsdl.*;

@SpringBootApplication
public class ConsumingWebServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsumingWebServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner lookup(WineClient quoteClient) {
		return args -> {
			BigInteger id = BigInteger.valueOf(362);
			DeleteWineResponse response = quoteClient.deleteWine(id);
			System.err.println(response.getStatus());
		};
	}

}
