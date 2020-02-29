package com.itmo.wst.fault;

import javax.xml.namespace.QName;

import com.itmo.wst.fault.exception.ServiceFaultException;
import com.itmo.wst.fault.exception.WineNotFoundException;
import com.itmo.wst.model.Status;
import org.springframework.ws.soap.SoapFault;
import org.springframework.ws.soap.SoapFaultDetail;
import org.springframework.ws.soap.server.endpoint.SoapFaultMappingExceptionResolver;


public class WineSoapFaultDefinitionExceptionResolver extends SoapFaultMappingExceptionResolver {

    private static final QName CODE = new QName("statusCode");
    private static final QName MESSAGE = new QName("message");

    @Override
    protected void customizeFault(Object endpoint, Exception ex, SoapFault fault) {
        logger.warn("Exception processed ", ex);
        if (ex instanceof ServiceFaultException) {
            Status status = ((ServiceFaultException) ex).getStatus();
            SoapFaultDetail detail = fault.addFaultDetail();
            detail.addFaultDetailElement(CODE).addText(status.value());
        }
    }
}
