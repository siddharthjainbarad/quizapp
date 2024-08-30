package com.siddharth.quizapp.controller;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
        try {
            userService.validateUser(user);
            userService.registerUser(user);
            return ResponseEntity.ok(user.getUsername() + " : Created Successfully");
        } catch (DuplicateException e) {
            return ResponseEntity.status(HttpStatus.FOUND)
                    //.header(HttpHeaders.LOCATION, "/login")
                    .body(e.getMessage() + user.getUsername());
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

    @GetMapping("/current-user")
    public ResponseEntity<?> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            return ResponseEntity.ok(userDetails.getUsername());
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("No user is currently logged in");
        }
    }

    @PostMapping("/update-profile")
    public ResponseEntity<?> updateProfile(@RequestParam String email,
                                           @RequestParam(required = false) String firstName,
                                           @RequestParam(required = false) String lastName,
                                           @RequestParam(required = false) String dob,
                                           @RequestParam String password) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            String result = userService.updateProfile(userDetails.getUsername(), email, firstName, lastName, dob, password);
            if (result.equals("Profile updated successfully")) {
                return ResponseEntity.ok(result);
            } else if (result.equals("No changes were made to the profile")) {
                return ResponseEntity.ok(result);
            } else {
                return ResponseEntity.badRequest().body(result);
            }
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("User not authenticated");
    }
}
