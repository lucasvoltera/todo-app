package com.example.todoapp.controllers;

import org.springframework.stereotype.Controller;

import com.example.todoapp.models.TodoItem;
import com.example.todoapp.models.Users;
import com.example.todoapp.repositories.UserRepository;
import com.example.todoapp.services.TodoItemService;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

    @Autowired
    private TodoItemService todoItemService;
    @Autowired
    private UserRepository userRepository;

    /**
     * Displays the index page with the list of todo items for the authenticated user.
     *
     * @param authentication the authentication object containing information about the authenticated user
     * @return a ModelAndView object with the "index" view and the list of todo items for the authenticated user
     */
    @GetMapping("/")
    public ModelAndView index(Authentication authentication) {
        ModelAndView modelAndView = new ModelAndView("index");

        // Assuming you have the authenticated user available in the "authentication" object
        Users authenticatedUser = userRepository.findByUsername(authentication.getName());
        String authenticatedUserName = authentication.getName();

        // Retrieve todo items added by the authenticated user
        List<TodoItem> todoItems = todoItemService.findByUser(authenticatedUser);

        modelAndView.addObject("name", authenticatedUserName);
        modelAndView.addObject("todoItems", todoItems);
        return modelAndView;
    }

    /**
     * Filters the todo items based on the specified criteria and retrieves the filtered items for the authenticated user.
     *
     * @param authentication     the authentication object containing information about the authenticated user
     * @param startDate          the start date for filtering the todo items (inclusive)
     * @param endDate            the end date for filtering the todo items (inclusive)
     * @param completedCheckbox  the checkbox indicating whether to include completed items (true) or not (false or null)
     * @param notCompletedCheckbox  the checkbox indicating whether to include not completed items (true) or not (false or null)
     * @return a ModelAndView object with the "index" view containing the filtered todo items
    */
    @GetMapping("/filter")
    public ModelAndView filterTodoItems(
            Authentication authentication,
            @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(value = "completedCheckbox", required = false) Boolean completedCheckbox,
            @RequestParam(value = "notCompletedCheckbox", required = false) Boolean notCompletedCheckbox
    ) {
        ModelAndView modelAndView = new ModelAndView("index");

        Users authenticatedUser = userRepository.findByUsername(authentication.getName());

        // Convert LocalDate to Instant for comparison
        Instant startDateTime = startDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();
        Instant endDateTime = endDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant();

        List<TodoItem> todoItems;

        if (completedCheckbox != null && notCompletedCheckbox != null) {
            // Both checkboxes are checked, ignore the checkbox values and retrieve all todoItems
            todoItems = todoItemService.findByUserAndCreatedAtBetween(authenticatedUser, startDateTime, endDateTime);
        } else if (completedCheckbox != null && completedCheckbox) {
            todoItems = todoItemService.findByUserAndIsCompleteTrueAndCreatedAtBetween(authenticatedUser, startDateTime, endDateTime);
        } else if (notCompletedCheckbox != null && notCompletedCheckbox) {
            todoItems = todoItemService.findByUserAndIsCompleteFalseAndCreatedAtBetween(authenticatedUser, startDateTime, endDateTime);
        } else {
            todoItems = todoItemService.findByUserAndCreatedAtBetween(authenticatedUser, startDateTime, endDateTime);
        }

        modelAndView.addObject("name", authentication.getName());
        modelAndView.addObject("todoItems", todoItems);
        return modelAndView;
    }

    /**
     * Clears the filter and redirects the user to the homepage.
     *
     * @return a string representing the redirect URL to the homepage
     */
    @GetMapping("/clear-filter")
    public String clearFilter() {
        return "redirect:/";
    }
}
