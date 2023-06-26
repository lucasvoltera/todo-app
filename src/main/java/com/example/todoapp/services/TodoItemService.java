package com.example.todoapp.services;

import com.example.todoapp.models.TodoItem;
import com.example.todoapp.models.Users;
import com.example.todoapp.repositories.TodoItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Service
public class TodoItemService {

    @Autowired
    private TodoItemRepository todoItemRepository;

    /**
     * Retrieves a TodoItem by its ID.
     *
     * @param id the ID of the TodoItem to retrieve
     * @return an Optional containing the retrieved TodoItem, or an empty Optional if not found
     */
    public Optional<TodoItem> getById(Long id) {
        return todoItemRepository.findById(id);
    }

    /**
     * Retrieves all TodoItems.
     *
     * @return an Iterable containing all TodoItems
     */
    public Iterable<TodoItem> getAll() {
        return todoItemRepository.findAll();
    }

    /**
     * Saves a TodoItem.
     *
     * @param todoItem the TodoItem to be saved
     * @return the saved TodoItem
     */
    public TodoItem save(TodoItem todoItem) {
        if (todoItem.getId() == null) {
            todoItem.setCreatedAt(Instant.now());
        }
        todoItem.setUpdatedAt(Instant.now());
        return todoItemRepository.save(todoItem);
    }

    /**
     * Deletes a TodoItem.
     *
     * @param todoItem the TodoItem to be deleted
     */
    public void delete(TodoItem todoItem) {
        todoItemRepository.delete(todoItem);
    }

    /**
     * Retrieves TodoItems associated with a specific user.
     *
     * @param user the user associated with the TodoItems
     * @return a list of TodoItems associated with the user
     */
    public List<TodoItem> findByUser(Users user) {
        return todoItemRepository.findByUser(user);
    }

    /**
     * Retrieves TodoItems associated with a specific user and created within a specified time range.
     *
     * @param user       the user associated with the TodoItems
     * @param startDate  the start date of the time range
     * @param endDate    the end date of the time range
     * @return a list of TodoItems associated with the user and created within the time range
     */
    public List<TodoItem> findByUserAndCreatedAtBetween(Users user, Instant startDate, Instant endDate) {
        return todoItemRepository.findByUserAndCreatedAtBetween(user, startDate, endDate);
    }

    /**
     * Retrieves TodoItems associated with a specific user, with a specified completion status, and created within a specified time range.
     *
     * @param user           the user associated with the TodoItems
     * @param isComplete     the completion status of the TodoItems
     * @param startDateTime  the start date and time of the time range
     * @param endDateTime    the end date and time of the time range
     * @return a list of TodoItems associated with the user, with the specified completion status,
     *         and created within the time range
     */
    public List<TodoItem> findByUserAndIsCompleteAndCreatedAtBetween(Users user, boolean isComplete, Instant startDateTime, Instant endDateTime) {
        return todoItemRepository.findByUserAndIsCompleteAndCreatedAtBetween(user, isComplete, startDateTime, endDateTime);
    }

    /**
     * Retrieves TodoItems associated with a specific user, with a false completion status, and created within a specified time range.
     *
     * @param user           the user associated with the TodoItems
     * @param startDateTime  the start date and time of the time range
     * @param endDateTime    the end date and time of the time range
     * @return a list of TodoItems associated with the user, with a null completion status,
     *         and created within the time range
     */
    public List<TodoItem> findByUserAndIsCompleteFalseAndCreatedAtBetween(Users user, Instant startDateTime, Instant endDateTime) {
        return todoItemRepository.findByUserAndIsCompleteFalseAndCreatedAtBetween(user, startDateTime, endDateTime);
    }

    /**
     * Retorna uma lista de TodoItems associados a um usuário específico, com status de conclusão definido como verdadeiro,
     * e criados dentro de um intervalo de tempo especificado.
     *
     * @param user           O usuário associado aos TodoItems
     * @param startDateTime  A data e hora de início do intervalo
     * @param endDateTime    A data e hora de término do intervalo
     * @return Uma lista de TodoItems associados ao usuário, com status de conclusão verdadeiro e criados dentro do intervalo especificado.
     */
    public List<TodoItem> findByUserAndIsCompleteTrueAndCreatedAtBetween(Users user, Instant startDateTime, Instant endDateTime) {
        return todoItemRepository.findByUserAndIsCompleteTrueAndCreatedAtBetween(user, startDateTime, endDateTime);
    }


}
