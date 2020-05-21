package org.superhelt.raidplanner2.service;

public class ServiceException extends RuntimeException {

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Object... params) {
        super(String.format(message, params));
    }
}
