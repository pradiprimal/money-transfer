package com.revolut.money.transfer.exception;

import com.revolut.money.transfer.dto.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class TransactionExceptionMapper implements ExceptionMapper<TransactionException> {
    private static final Logger LOGGER = LoggerFactory.getLogger(TransactionExceptionMapper.class);

    @Override
    public Response toResponse(TransactionException exception) {
        LOGGER.error("Transaction exception occurred", exception);
        return Response.status(exception.getStatus())
                .entity(new ErrorResponse(exception.getStatus().getStatusCode(), exception.getMessage()))
                .build();
    }

}
