package com.revolut.money.transfer.exception;

import com.revolut.money.transfer.dto.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class GlobalExceptionMapper implements ExceptionMapper<Exception> {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionMapper.class);

    @Override
    public Response toResponse(Exception exception) {
        LOGGER.error("Specific exception handler not found", exception);
        return Response.status(Response.Status.INTERNAL_SERVER_ERROR)
                .entity(new ErrorResponse(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), "Internal server error"))
                .type(MediaType.APPLICATION_JSON)
                .build();
    }

}
