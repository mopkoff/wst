package com.itmo.wst.config;

import com.itmo.wst.fault.WineSoapFaultDefinitionExceptionResolver;
import com.itmo.wst.fault.exception.ServiceFaultException;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.ws.config.annotation.EnableWs;
import org.springframework.ws.config.annotation.WsConfigurerAdapter;
import org.springframework.ws.soap.server.endpoint.SoapFaultDefinition;
import org.springframework.ws.soap.server.endpoint.SoapFaultMappingExceptionResolver;
import org.springframework.ws.transport.http.MessageDispatcherServlet;
import org.springframework.ws.wsdl.wsdl11.DefaultWsdl11Definition;
import org.springframework.xml.xsd.SimpleXsdSchema;
import org.springframework.xml.xsd.XsdSchema;

import java.util.Properties;

@EnableWs
@Configuration
public class WebServiceConfig extends WsConfigurerAdapter {
	@Bean
	public ServletRegistrationBean messageDispatcherServlet(ApplicationContext applicationContext) {
		MessageDispatcherServlet servlet = new MessageDispatcherServlet();
		servlet.setApplicationContext(applicationContext);
		servlet.setTransformWsdlLocations(true);
		return new ServletRegistrationBean(servlet, "/ws/*");
	}

	@Bean(name = "wines")
	public DefaultWsdl11Definition defaultWsdl11Definition(XsdSchema winesSchema) {
		DefaultWsdl11Definition wsdl11Definition = new DefaultWsdl11Definition();
		wsdl11Definition.setPortTypeName("WinesPort");
		wsdl11Definition.setLocationUri("/ws");
		wsdl11Definition.setTargetNamespace("itmo.com/wst");
		wsdl11Definition.setSchema(winesSchema);
		return wsdl11Definition;
	}

	@Bean
	public XsdSchema winesSchema() {
		return new SimpleXsdSchema(new ClassPathResource("wines.xsd"));
	}

	@Bean
	public SoapFaultMappingExceptionResolver exceptionResolver() {
		SoapFaultMappingExceptionResolver exceptionResolver = new WineSoapFaultDefinitionExceptionResolver();

		SoapFaultDefinition faultDefinition = new SoapFaultDefinition();
		faultDefinition.setFaultCode(SoapFaultDefinition.SERVER);
		exceptionResolver.setDefaultFault(faultDefinition);

		Properties errorMappings = new Properties();
		errorMappings.setProperty(Exception.class.getName(), SoapFaultDefinition.SERVER.toString());
		errorMappings.setProperty(ServiceFaultException.class.getName(), SoapFaultDefinition.SERVER.toString());
		exceptionResolver.setExceptionMappings(errorMappings);
		exceptionResolver.setOrder(1);
		return exceptionResolver;
	}

}
