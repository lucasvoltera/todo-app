package com.example.todoapp.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@Getter
@Setter
@Entity
@Table(name = "todo_items")
public class TodoItem implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Description is required")
    private String description;

    private Boolean isComplete = false;

    private Instant createdAt;

    private Instant updatedAt;

    private String itemCategory;

    private Integer quantity;

    private String storeName;


    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "userId", columnDefinition = "bigint", referencedColumnName = "id")
    @JsonIgnoreProperties("todoitems")
    private Users user;

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return String.format("TodoItem{id=%d, description='%s', isComplete='%s', createdAt='%s', updatedAt='%s'}",
                id, description, isComplete, createdAt, updatedAt);
    }

}