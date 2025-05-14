package com.mihailov.xlsx_min_service.exception;

public class FileProcessingException extends RuntimeException {

    public FileProcessingException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileProcessingException(String message) {
        super(message);
    }
}
