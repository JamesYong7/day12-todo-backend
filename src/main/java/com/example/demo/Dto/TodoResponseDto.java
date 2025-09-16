package com.example.demo.Dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TodoResponseDto {
    private String id;
    private String text;
    private boolean done;

    public TodoResponseDto(String id, String text, boolean done) {
        this.id = id;
        this.text = text;
        this.done = done;
    }
    public TodoResponseDto() {
    }

}
