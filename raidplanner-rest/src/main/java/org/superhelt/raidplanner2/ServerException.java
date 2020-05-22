package org.superhelt.raidplanner2;

public class ServerException extends RuntimeException {

    private final int statusCode;

    public ServerException(int statusCode, String message) {
        super(message);
        this.statusCode = statusCode;
    }

    public ServerException(String message, Object... params) {
        this(400, String.format(message, params));
    }

    public ServerException(int statusCode, String message, Object... params) {
        this(statusCode, String.format(message, params));
    }
}
