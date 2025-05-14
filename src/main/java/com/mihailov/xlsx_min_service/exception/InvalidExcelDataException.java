package com.mihailov.xlsx_min_service.exception;

public class InvalidExcelDataException extends RuntimeException {
    public InvalidExcelDataException(String message) {
        super(message);
    }
}
