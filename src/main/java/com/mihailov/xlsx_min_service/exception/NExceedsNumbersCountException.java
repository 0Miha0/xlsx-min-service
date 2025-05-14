package com.mihailov.xlsx_min_service.exception;

public class NExceedsNumbersCountException extends IllegalArgumentException {
    public NExceedsNumbersCountException(String message) {
        super(message);
    }
}