package com.example.todoapp.controllers;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.todoapp.models.Users;
import com.example.todoapp.repositories.UserRepository;
import com.example.todoapp.services.UserServices;

@RestController
@RequestMapping("/user")
public class UserController implements UserServices {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Retrieves all users.
     *
     * @return a list of all users
     */
    @GetMapping
    @Override
    public List<Users> findAll() {
        return userRepository.findAll();
    }


    /**
     * Creates a new user.
     *
     * @param user the user object to be created
     * @return a ResponseEntity containing the created user object in the response body,
     *         or a bad request response if the user's name is null or empty
     */
    @PostMapping("/signup")
    @Override
    public ResponseEntity<Users> createUser(@RequestBody Users user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Users newUser = userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(newUser);
    }

    /**
     * Retrieves a user by its ID.
     *
     * @param id the ID of the user to retrieve
     * @return a ResponseEntity containing the retrieved user object in the response body,
     *         or a not found response if the user with the given ID is not found
     */
    @GetMapping("/{id}")
    @Override
    public ResponseEntity<Users> findById(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(user -> ResponseEntity.ok().body(user))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Updates a user with new data.
     *
     * @param id       the ID of the user to update
     * @param bodyUser the user object containing the updated data
     * @return a ResponseEntity containing the updated user object in the response body,
     *         or a not found response if the user with the given ID is not found
     */
    @PutMapping("/{id}")
    @Override
    public ResponseEntity<Users> updateUser(@PathVariable Long id, @RequestBody Users bodyUser) {
        return userRepository.findById(id)
                .map(user -> {
                    user.setName(bodyUser.getName());
                    user.setPassword(bodyUser.getPassword());
                    Users updatedUser = userRepository.save(user);
                    return ResponseEntity.ok().body(updatedUser);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Deletes a user by its ID.
     *
     * @param id the ID of the user to delete
     * @return a ResponseEntity representing the success or failure of the deletion,
     *         or a not found response if the user with the given ID is not found
     */
    @DeleteMapping("/{id}")
    @Override
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        return userRepository.findById(id)
                .map(user -> {
                    userRepository.deleteById(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().<Void>build());
    }

}
