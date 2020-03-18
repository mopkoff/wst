package com.itmo.wst.fault.exception;

import org.springframework.ws.soap.server.endpoint.annotation.FaultCode;
import org.springframework.ws.soap.server.endpoint.annotation.SoapFault;

@SoapFault(faultCode = FaultCode.CUSTOM,
        customFaultCode = "{" + WineNotFoundException.NAMESPACE_URI + "}NOT_FOUND",
        faultStringOrReason = "No wines were found")
public class WineNotFoundException extends Exception {

    private static final long serialVersionUID = 1L;
    public static final String NAMESPACE_URI = "itmo.com/wst";

    public WineNotFoundException() {
        super();
    }

    public WineNotFoundException(String message) {
        super(message);
    }
}
