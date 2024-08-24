package com.siddharth.quizapp.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
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
            userService.registerUser(user);
            return ResponseEntity.ok(user.getUsername() + " : Created Successfully");
        } catch (DuplicateException e) {
            return ResponseEntity.status(HttpStatus.FOUND)
                                 //.header(HttpHeaders.LOCATION, "/login")
                                 .body(e.getMessage() + user.getUsername());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody User user) {
        boolean isValid = userService.validateCredentials(user.getUsername(), user.getPassword());
        if (isValid) {
            System.out.println("\n\nLogin Success");
            String token = "JWT token here";
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials");
        }
    }

    @GetMapping("/users/{id}")
    public Optional<User> getUserById(@PathVariable int id) {
        return userService.findById(id);
    }

    @GetMapping("/users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUserById(@PathVariable int id) {
        boolean isDeleted = userService.deleteUserById(id);
        if (isDeleted) {
            return ResponseEntity.ok("User deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
    }
}
