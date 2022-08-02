package com.topcoder.onlinereview.component.jwt;

public class InvalidTokenException extends JWTException {
    public InvalidTokenException(String token, String message, Throwable cause) {
        super(token, message, cause);
    }

    public InvalidTokenException(String token, String message) {
        super(token, message);
    }

    public InvalidTokenException(String token, Throwable cause) {
        super(token, cause);
    }

    public InvalidTokenException(String token) {
        super(token);
    }
}