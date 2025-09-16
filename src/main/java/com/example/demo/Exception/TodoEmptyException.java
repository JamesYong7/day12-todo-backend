package com.example.demo.Exception;

public class TodoEmptyException extends IllegalArgumentException {
    public TodoEmptyException(String message) {
        super(message);
    }
}
