package com.example.demo.Advice;

import com.example.demo.Exception.TodoEmptyException;
import com.example.demo.Exception.TodoIdNotFoundException;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TodoEmptyException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public HttpResponseException todoEmptyException(Exception e){
        return new HttpResponseException(e.getMessage());
    }

    @ExceptionHandler(TodoIdNotFoundException.class)
    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
    public HttpResponseException todoIdNotFoundException(Exception e){
            return new HttpResponseException(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public HttpResponseException handlerArgumentNotValid(MethodArgumentNotValidException exception){
        String errorMessage = exception.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(Collectors.joining(" | "));
        return new HttpResponseException(errorMessage);
    }
}
