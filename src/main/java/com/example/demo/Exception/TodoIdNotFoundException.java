package com.example.demo.Exception;

public class TodoIdNotFoundException extends IllegalArgumentException {
    public TodoIdNotFoundException(String message) {
        super(message);
    }
}
