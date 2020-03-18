package com.itmo.wst.fault.exception;

import com.itmo.wst.model.Status;

public class ServiceFaultException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private Status status;

    public ServiceFaultException(String message, Status status) {
        super(message);
        this.status = status;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

}
