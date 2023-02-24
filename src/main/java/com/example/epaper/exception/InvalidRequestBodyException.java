package com.example.epaper.exception;

/**
 * Exception for processing of invalid request params / bodies
 */
public class InvalidRequestBodyException extends RuntimeException {

    /**
     * {@inheritDoc}
     */
    public InvalidRequestBodyException(final String message) {
        super(message);
    }

    /**
     * {@inheritDoc}
     */
    public InvalidRequestBodyException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
