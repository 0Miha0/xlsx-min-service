package com.mihailov.xlsx_min_service.exception;

public class DataValidationException extends RuntimeException{

    public DataValidationException(String message, Throwable cause){
        super(message, cause);
    }

    public DataValidationException(String message){
        super(message);
    }
}
