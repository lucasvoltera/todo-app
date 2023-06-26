package com.example.todoapp.services;

import com.example.todoapp.models.Users;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface UserServices {

    /**
     * Retrieves a list of all Users.
     *
     * @return a list of all Users
     */
    public List<Users> findAll();
    /**
     * Creates a new User.
     *
     * @param user the User object to be created
     * @return a ResponseEntity containing the created User and an appropriate HTTP status code
     */
    public ResponseEntity<Users> createUser(@RequestBody Users user);
    /**
     * Updates an existing User with the specified ID.
     *
     * @param id       the ID of the User to be updated
     * @param bodyUser the updated User object
     * @return a ResponseEntity containing the updated User and an appropriate HTTP status code,
     *         or an appropriate error response if the User was not found
     */
    public ResponseEntity<Users> updateUser(@PathVariable Long id, @RequestBody Users bodyUser);
    /**
     * Deletes the User with the specified ID.
     *
     * @param id the ID of the User to be deleted
     * @return a ResponseEntity with an appropriate HTTP status code,
     *         or an appropriate error response if the User was not found
     */
    public ResponseEntity<Void> deleteUser(@PathVariable Long id);
    /**
     * Retrieves the User with the specified ID.
     *
     * @param id the ID of the User to be retrieved
     * @return a ResponseEntity containing the retrieved User and an appropriate HTTP status code,
     *         or an appropriate error response if the User was not found
     */
    public ResponseEntity<Users> findById(@PathVariable Long id);

}
