package com.example.demo.Controller;

import com.example.demo.Entity.Todo;
import com.example.demo.Service.TodoService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public Todo create(Todo todo){
        return todoService.create(todo);
    }
}
