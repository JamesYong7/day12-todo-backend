package com.example.demo.Service;

import com.example.demo.Entity.Todo;
import com.example.demo.Exception.TodoEmptyException;
import com.example.demo.Exception.TodoIdNotFoundException;
import com.example.demo.Repository.TodoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoService {
    final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public List<Todo> index() {
        return todoRepository.findAll();
    }

    public Todo create(Todo todo) {
        if(todo.getText() == null || todo.getText().isEmpty()) {
            throw new TodoEmptyException("Todo cannot be null");
        }
        return todoRepository.save(todo);
    }

    public Todo update(String id, Todo todo) {
        if(todo.getText() == null || todo.getText().isEmpty()) {
            throw new TodoEmptyException("Todo cannot be null");
        }
        Todo existingTodo = todoRepository.findById(id).orElseThrow(() -> new TodoIdNotFoundException("Todo not found"));
        existingTodo.setText(todo.getText());
        existingTodo.setDone(todo.isDone());
        return todoRepository.save(existingTodo);
    }

    public void deleteExactTodo(String id){
        Todo existingTodo = todoRepository.findById(id).orElseThrow(() -> new TodoIdNotFoundException("Todo not found"));
        todoRepository.delete(existingTodo);
    }
}
