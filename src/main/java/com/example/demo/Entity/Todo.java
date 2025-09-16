package com.example.demo.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
//@Table(name = "todos")
public class Todo {
    public Todo(String id, String buyMilk, boolean b) {
        this.id = id;
        this.text = buyMilk;
        this.done = b;
    }
    @Id
    private String id;
    @Column(nullable = false)
    private String text;
    @Column(nullable = false)
    private boolean done;

    public Todo() {

    }

    @PrePersist
    public void ensureId(){
        if(this.id == null){
            this.id = UUID.randomUUID().toString();
        }
    }
}
