package com.example.demo.Dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TodoRequestDto {
    private String id;
    private String text;
    private boolean done;

    public TodoRequestDto(String id, String text, boolean done) {
        this.id = id;
        this.text = text;
        this.done = done;
    }
    public TodoRequestDto() {
    }

}