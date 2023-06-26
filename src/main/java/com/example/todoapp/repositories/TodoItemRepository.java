package com.example.todoapp.repositories;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.todoapp.models.TodoItem;
import com.example.todoapp.models.Users;

@Repository
public interface TodoItemRepository extends JpaRepository<TodoItem, Long> {

    /**
     * Retrieves a TodoItem by its ID.
     *
     * @param id the ID of the TodoItem
     * @return an Optional containing the TodoItem, or empty if not found
     */
    Optional<TodoItem> findById(Long id);
    /**
     * Retrieves a list of TodoItems associated with the specified user.
     *
     * @param user the user associated with the TodoItems
     * @return a list of TodoItems belonging to the user
     */
    List<TodoItem> findByUser(Users user);
    /**
     * Retrieves a list of TodoItems associated with the specified user and created within the given time range.
     *
     * @param user       the user associated with the TodoItems
     * @param startDate  the start date of the time range (inclusive)
     * @param endDate    the end date of the time range (inclusive)
     * @return a list of TodoItems belonging to the user and created within the time range
     */
    List<TodoItem> findByUserAndCreatedAtBetween(Users user, Instant startDate, Instant endDate);
    /**
     * Retrieves a list of TodoItems associated with the specified user, with the given completion status,
     * and created within the given time range.
     *
     * @param user            the user associated with the TodoItems
     * @param isComplete      the completion status of the TodoItems
     * @param startDateTime   the start date and time of the time range (inclusive)
     * @param endDateTime     the end date and time of the time range (inclusive)
     * @return a list of TodoItems belonging to the user, matching the completion status, and created within the time range
     */
    List<TodoItem> findByUserAndIsCompleteAndCreatedAtBetween(Users user, boolean isComplete, Instant startDateTime, Instant endDateTime);
    /**
     * Retrieves a list of TodoItems associated with the specified user, with a false completion status,
     * and created within the given time range.
     *
     * @param user            the user associated with the TodoItems
     * @param startDateTime   the start date and time of the time range (inclusive)
     * @param endDateTime     the end date and time of the time range (inclusive)
     * @return a list of TodoItems belonging to the user, with a null completion status, and created within the time range
     */
    List<TodoItem> findByUserAndIsCompleteFalseAndCreatedAtBetween(Users user, Instant startDateTime, Instant endDateTime);

    /**
     * Retorna uma lista de TodoItems associados a um usuário específico, com status de conclusão definido como verdadeiro,
     * e criados dentro de um intervalo de tempo especificado.
     *
     * @param user           O usuário associado aos TodoItems
     * @param startDateTime  A data e hora de início do intervalo
     * @param endDateTime    A data e hora de término do intervalo
     * @return Uma lista de TodoItems associados ao usuário, com status de conclusão verdadeiro e criados dentro do intervalo especificado.
     */
    public List<TodoItem> findByUserAndIsCompleteTrueAndCreatedAtBetween(Users user, Instant startDateTime, Instant endDateTime);
}
