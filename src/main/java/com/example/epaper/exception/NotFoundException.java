package com.example.epaper.exception;

/**
 * Exception for processing of non-existing entities
 */
public class NotFoundException extends RuntimeException {

    /**
     * {@inheritDoc}
     */
    public NotFoundException(final String message) {
        super(message);
    }

    /**
     * {@inheritDoc}
     */
    public NotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

}
