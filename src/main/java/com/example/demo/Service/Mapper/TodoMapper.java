package com.example.demo.Service.Mapper;

import com.example.demo.Dto.TodoRequestDto;
import com.example.demo.Dto.TodoResponseDto;
import com.example.demo.Entity.Todo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class TodoMapper {

    public TodoResponseDto toResponseDto(Todo todo) {
        TodoResponseDto dto = new TodoResponseDto();
        dto.setId(todo.getId());
        dto.setText(todo.getText());
        dto.setDone(todo.isDone());
        return dto;
    }

    public List<TodoResponseDto> toResponseDtoList(List<Todo> todos) {
        return todos.stream().map(this::toResponseDto).toList();
    }

    public Todo toEntity(TodoRequestDto dto) {
        Todo todo = new Todo();
        todo.setId(dto.getId());
        todo.setText(dto.getText());
        todo.setDone(dto.isDone());
        return todo;
    }
}
