package com.example.todoapp.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.todoapp.models.Users;
import com.example.todoapp.repositories.UserRepository;

@Service
public class UserDetailsImp implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsImp(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Loads a user by username.
     *
     * @param username the username of the user to load
     * @return the UserDetails object representing the user
     * @throws UsernameNotFoundException if the user is not found
    */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        return org.springframework.security.core.userdetails.User
                .withUsername(user.getUsername())
                .password(user.getPassword())
                .authorities(user.getAuthorities())
                .build();
    }

}