package com.example.demo.Controller;

import com.example.demo.Entity.Todo;
import com.example.demo.Repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {
    @Autowired
    TodoRepository todoRepository;

    @GetMapping
    List<Todo> index() {
        return todoRepository.findAll();
    }
}
