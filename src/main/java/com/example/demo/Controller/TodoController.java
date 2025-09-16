package com.example.demo.Controller;

import com.example.demo.Entity.Todo;
import com.example.demo.Error.TodoEmptyException;
import com.example.demo.Error.TodoIdNotFoundException;
import com.example.demo.Service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {
    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public List<Todo> index(){
        return todoService.index();
    }

    @PostMapping
    public ResponseEntity<Todo> create(@RequestBody Todo todo) {
        try {
            Todo newTodo = todoService.create(todo);
            return ResponseEntity.status(HttpStatus.CREATED).body(newTodo);
        } catch (TodoEmptyException e) {
            return ResponseEntity.unprocessableEntity().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Todo> update(@PathVariable String id, @RequestBody Todo todo) {
        try {
            Todo updatedTodo = todoService.update(id, todo);
            return ResponseEntity.ok(updatedTodo);
        } catch (TodoEmptyException e) {
            return ResponseEntity.unprocessableEntity().build();
        }catch (TodoIdNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
