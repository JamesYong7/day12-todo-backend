package com.example.demo.Controller;

import com.example.demo.Dto.TodoRequestDto;
import com.example.demo.Dto.TodoResponseDto;
import com.example.demo.Entity.Todo;
import com.example.demo.Error.TodoEmptyException;
import com.example.demo.Error.TodoIdNotFoundException;
import com.example.demo.Service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.demo.Service.Mapper.TodoMapper;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {
    private final TodoService todoService;
    private final TodoMapper todoMapper;

    public TodoController(TodoService todoService, TodoMapper todoMapper) {
        this.todoService = todoService;
        this.todoMapper = todoMapper;
    }

    @GetMapping
    public List<TodoResponseDto> index(){
        List<Todo> todos = todoService.index();
        return todoMapper.toResponseDtoList(todos);
    }

    @PostMapping
    public ResponseEntity<Todo> create(@RequestBody TodoRequestDto todo) {
        try {
            Todo newTodo = todoService.create(todoMapper.toEntity(todo));
            return ResponseEntity.status(HttpStatus.CREATED).body(newTodo);
        } catch (TodoEmptyException e) {
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Todo> update(@PathVariable String id, @RequestBody TodoRequestDto todo) {
        try {
            Todo updatedTodo = todoService.update(id, todoMapper.toEntity(todo));
            return ResponseEntity.ok(updatedTodo);
        } catch (TodoEmptyException e) {
            return ResponseEntity.unprocessableEntity().build();
        }catch (TodoIdNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExactTodo(@PathVariable String id) {
        try {
            todoService.deleteExactTodo(id);
            return ResponseEntity.noContent().build();
        } catch (TodoIdNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
