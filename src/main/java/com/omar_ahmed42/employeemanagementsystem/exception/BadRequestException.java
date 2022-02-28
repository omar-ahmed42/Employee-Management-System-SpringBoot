package com.omar_ahmed42.employeemanagementsystem.exception;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String message) {
        super(message);
    }
}
