package com.example.epaper.controller;

import com.example.epaper.exception.InvalidRequestBodyException;
import com.example.epaper.exception.NotFoundException;
import com.example.epaper.model.dto.ErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class RestApiExceptionHandler {

    @ExceptionHandler(InvalidRequestBodyException.class)
    public ResponseEntity<ErrorResponseDto> globalExceptionHandling(final InvalidRequestBodyException exception) {
        final ErrorResponseDto errorResponse = getErrorResponseDto(exception);
        log.error(exception.getMessage(), exception);
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDto> globalExceptionHandling(final Exception exception) {
        final ErrorResponseDto errorResponse = getErrorResponseDto(exception);
        log.error(exception.getMessage(), exception);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDto> globalExceptionHandling(final NotFoundException exception) {
        final ErrorResponseDto errorResponse = getErrorResponseDto(exception);
        log.error(exception.getMessage(), exception);
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    private ErrorResponseDto getErrorResponseDto(final Exception exception) {
        return ErrorResponseDto.builder()
                .message(exception.getMessage())
                .build();
    }

}
