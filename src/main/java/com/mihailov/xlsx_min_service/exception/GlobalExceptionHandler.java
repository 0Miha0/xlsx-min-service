package com.mihailov.xlsx_min_service.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIllegalArgumentException(IllegalArgumentException ex) {
        return buildResponse(ex);
    }

    @ExceptionHandler(InvalidExcelDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInvalidExcelDataException(InvalidExcelDataException ex) {
        return buildResponse(ex);
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleDataValidationException(Exception ex) {
        return buildResponse(ex);
    }

    @ExceptionHandler(FileProcessingException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleFileProcessingException(FileProcessingException ex) {
        return buildResponse(ex);
    }

    @ExceptionHandler(NExceedsNumbersCountException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleNExceedsNumbersCountException(NExceedsNumbersCountException ex) {
        return buildResponse(ex);
    }

    private ErrorResponse buildResponse(Exception ex) {
        log.error(ex.getClass().getName(), ex);
        return ErrorResponse.builder()
                .timestamp(LocalDateTime.now())
                .error(ex.getClass().getName())
                .message(Objects.requireNonNullElse(ex.getMessage(), "No message available"))
                .build();
    }
}
