package com.example.todoapp.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.todoapp.models.Users;
import com.example.todoapp.repositories.UserRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;


@Controller
public class AuthController {

    @Autowired
    UserRepository userRepository;

    /**
     * Handles the request for the home page based on the user's authentication status.
     * If the user is authenticated, it returns the "index" view.
     * If the user is not authenticated, it redirects to the "signin" view.
     *
     * @param authentication the authentication object representing the current user's authentication status
     * @return a string representing the view name to be rendered
     */
    @GetMapping("/index")
    public String home(Authentication authentication) {
        if (authentication.getPrincipal() instanceof UserDetails) {
            return "index";
        } else {
            return "signin";
        }
    }

    /**
     * Handles the request for the signin page.
     *
     * @return a string representing the view name to be rendered
     */
    @GetMapping("/signin")
    public String signin() {
        return "signin";
    }

    /**
     * Handles the request for the signup page.
     *
     * @param model the model object used to add attributes for the view
     * @return a string representing the view name to be rendered
     */
    @GetMapping("/signup")
    public String signup(Model model) {
        model.addAttribute("user", new Users());
        return "signup";
    }

    /**
     * Handles the request to signup a new user.
     * Encrypts the user's password, saves the user to the database, and redirects to the signin page.
     *
     * @param user the user object containing the signup form data
     * @return a string representing the redirect URL to the signin page
     */
    @PostMapping("/signup")
    public String signupUser(@ModelAttribute Users user) {
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userRepository.save(user);
        return "signin";
    }

    /**
     * Handles the request to sign out the current user.
     * Clears the security context and performs a logout operation.
     *
     * @param request  the HTTP servlet request
     * @param response the HTTP servlet response
     * @return a string representing the redirect URL to the signin page
     */
    @GetMapping("/signout")
    public String signout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContextHolder.clearContext();
        new SecurityContextLogoutHandler().logout(request, response, null);
        return "redirect:/signin";

    }
}
