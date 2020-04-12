package com.itmo.wst.fault.exception;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

@SoapFault(faultCode = FaultCode.CUSTOM,
        customFaultCode = "{" + IncorrectFormatException.NAMESPACE_URI + "}INVALID_FORMAT",
        faultStringOrReason = "Request format is not valid. Please, check host:port/ws/wines.wsdl")
public class IncorrectFormatException extends Exception {

    private static final long serialVersionUID = 1L;
    public static final String NAMESPACE_URI = "itmo.com/wst";

    public IncorrectFormatException() {
        super();
    }

    public IncorrectFormatException(String message) {
        super(message);
    }
}
