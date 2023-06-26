package com.example.todoapp.controllers;

import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.todoapp.services.TodoItemService;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;
import com.example.todoapp.models.TodoItem;
import com.example.todoapp.models.Users;
import com.example.todoapp.repositories.UserRepository;

import jakarta.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;


@Controller
public class TodoFormController {

    @Autowired
    private TodoItemService todoItemService;
    @Autowired
    private UserRepository userRepository;

    /**
     * Displays the form for creating a new TodoItem.
     *
     * @param todoItem the TodoItem object used to bind form data
     * @return the name of the view template for creating a new TodoItem
     */
    @GetMapping("/create-todo")
    public String showCreateForm(TodoItem todoItem) {
        return "new-todo-item";
    }
    
    /**
     * Handles the submission of the create todo item form.
     * Validates the todo item, associates it with the authenticated user, and saves it to the database.
     *
     * @param todoItem the todo item object containing the form data
     * @param result   the binding result object for validation errors
     * @param model    the model object used to add attributes for the view
     * @return a string representing the redirect URL to the home page
     */
    @PostMapping("/todo")
    public String createTodoItem(@Valid TodoItem todoItem, BindingResult result, Model model) {

        // Assuming you have the authenticated user available in the "authentication" object
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Users authenticatedUser = userRepository.findByUsername(authentication.getName());

        todoItem.setUser(authenticatedUser);

        todoItemService.save(todoItem);
        return "redirect:/";
    }

    /**
     * Deletes a specific todo item by its ID.
     * Verifies if the authenticated user is the owner of the todo item before deleting it.
     *
     * @param id    the ID of the todo item to be deleted
     * @param model the model object used to add attributes for the view
     * @return a string representing the redirect URL to the home page
     * @throws IllegalArgumentException if the todo item with the given ID is not found or the authenticated user is not the owner
     */
    @GetMapping("/delete/{id}")
    public String deleteTodoItem(@PathVariable("id") Long id, Model model) {
        TodoItem todoItem = todoItemService
                .getById(id)
                .orElseThrow(() -> new IllegalArgumentException("TodoItem id: " + id + " not found"));

        // Check if the authenticated user is the owner of the todoItem
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Users authenticatedUser = userRepository.findByUsername(authentication.getName());
        if (!Objects.equals(todoItem.getUser(), authenticatedUser)) {
            throw new IllegalArgumentException("Unauthorized access");
        }

        todoItemService.delete(todoItem);
        return "redirect:/";
    }

    /**
     * Displays the form for updating an existing todo item.
     *
     * @param id    the ID of the todo item to be updated
     * @param model the model object used to add attributes for the view
     * @return a string representing the view name for the edit-todo-item form
     * @throws IllegalArgumentException if the todo item with the given ID is not found
     */
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        TodoItem todoItem = todoItemService
                .getById(id)
                .orElseThrow(() -> new IllegalArgumentException("TodoItem id: " + id + " not found"));

        model.addAttribute("todo", todoItem);
        return "edit-todo-item";
    }

    /**
     * Handles the submission of the update todo item form.
     * Updates the existing todo item with the new data provided in the form.
     *
     * @param id        the ID of the todo item to be updated
     * @param todoItem  the todo item object containing the updated data
     * @param result    the binding result object for validation errors
     * @param model     the model object used to add attributes for the view
     * @return a string representing the redirect URL to the home page
     * @throws IllegalArgumentException if the todo item with the given ID is not found
     */
    @PostMapping("/todo/{id}")
    public String updateTodoItem(@PathVariable("id") Long id, @Valid TodoItem todoItem, BindingResult result, Model model) {

        TodoItem item = todoItemService
                .getById(id)
                .orElseThrow(() -> new IllegalArgumentException("TodoItem id: " + id + " not found"));

        item.setIsComplete(todoItem.getIsComplete());
        item.setDescription(todoItem.getDescription());
        item.setItemCategory(todoItem.getItemCategory());
        item.setQuantity(todoItem.getQuantity());
        item.setStoreName(todoItem.getStoreName());

        todoItemService.save(item);

        return "redirect:/";
    }
}
