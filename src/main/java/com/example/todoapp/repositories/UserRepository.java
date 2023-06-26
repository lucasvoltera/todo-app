package com.example.todoapp.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.todoapp.models.Users;

public interface UserRepository extends JpaRepository<Users, Long> {
    /**
     * Retrieves a User by their username.
     *
     * @param username the username of the User
     * @return the User with the specified username, or null if not found
     */
    Users findByUsername(String username);
}
