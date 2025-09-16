package com.example.demo.Error;

public class TodoIdNotFoundException extends IllegalArgumentException {
    public TodoIdNotFoundException(String message) {
        super(message);
    }
}
