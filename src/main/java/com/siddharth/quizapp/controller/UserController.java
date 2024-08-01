package com.siddharth.quizapp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.siddharth.quizapp.exception.DuplicateException;
import com.siddharth.quizapp.model.User;
import com.siddharth.quizapp.service.UserService;


@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping(value = "/register", consumes = "application/json")
    public ResponseEntity<?> registerUser(@RequestBody User user) {
        try{
            userService.validateUser(user);
            User registerUser = userService.registerUser(user);
            return ResponseEntity.ok(user.getUsername() + " : Created Successfully");
        } catch (DuplicateException e) {
            return ResponseEntity.status(HttpStatus.FOUND)
                                 //.header(HttpHeaders.LOCATION, "/login")
                                 .body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public String  loginUser(@RequestBody User user) {
        User existingUser = userService.findByUsername(user.getUsername());
        return "JWT token here";
    }

    @GetMapping("/users/{id}")
    public Optional<User> getUserById(@PathVariable int id) {
        return userService.findById(id);
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

}
