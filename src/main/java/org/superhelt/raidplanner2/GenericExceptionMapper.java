package org.superhelt.raidplanner2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.superhelt.raidplanner2.service.ServiceException;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class GenericExceptionMapper implements ExceptionMapper<Exception> {

    private static final Logger log = LoggerFactory.getLogger(GenericExceptionMapper.class);

    @Override
    public Response toResponse(Exception exception) {
        ServiceException ex;
        if(exception instanceof ServiceException) {
            ex = (ServiceException) exception;
        } else {
            log.error("Unexpected exception encountered in exception mapper.", exception);
            ex = new ServiceException("Unknown exception happened");
        }

        return Response.status(400).entity(ex.getMessage()).build();
    }

}