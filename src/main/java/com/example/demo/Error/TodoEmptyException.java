package com.example.demo.Error;

public class TodoEmptyException extends IllegalArgumentException {
    public TodoEmptyException(String message) {
        super(message);
    }
}
