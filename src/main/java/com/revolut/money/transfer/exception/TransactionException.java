package com.revolut.money.transfer.exception;


import javax.ws.rs.core.Response;

public class TransactionException extends RuntimeException {
    private final Response.Status status;

    public TransactionException(Response.Status status, String errorMessage) {
        super(errorMessage);
        this.status = status;
    }

    public Response.Status getStatus() {
        return status;
    }
}
