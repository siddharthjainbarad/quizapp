package com.siddharth.quizapp.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.siddharth.quizapp.model.Quiz;
import com.siddharth.quizapp.model.User;
import com.siddharth.quizapp.model.UserQuizAttempt;
import com.siddharth.quizapp.service.QuizeService;
import com.siddharth.quizapp.service.UserQuizAttemptService;
import com.siddharth.quizapp.service.UserService;

@RestController
@RequestMapping("/api/userQuizAttempts")
public class UserQuizAttemptController {

    @Autowired
    private UserQuizAttemptService userQuizAttemptService;

    @Autowired
    private UserService userService;

    @Autowired
    private QuizeService quizeService;

    // Get all UserQuizAttempts
    @GetMapping
    public ResponseEntity<List<UserQuizAttempt>> getAllUserQuizAttempts() {
        List<UserQuizAttempt> attempts = userQuizAttemptService.getAllUserQuizAttempts();
        return ResponseEntity.ok(attempts);
    }

    // Get a specific UserQuizAttempt by ID
    @GetMapping("/{quizId}")
    public ResponseEntity<List<UserQuizAttempt>> getTop3UserQuizAttemptsByQuizId(@PathVariable Long quizId) {
        List<UserQuizAttempt> topAttempts = userQuizAttemptService.getTop3UserQuizAttemptsByQuizId(quizId);
        return ResponseEntity.ok(topAttempts);
    }

    @PostMapping
    public ResponseEntity<?> createUserQuizAttempt(@RequestBody Map<String, Object> payload) {
        int quizId = (int) payload.get("quizId");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        Optional<Quiz> quizOptional = quizeService.getQuizById(quizId);
        if (!quizOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Quiz not found");
        }
        Quiz quiz = quizOptional.get();
        UserQuizAttempt userQuizAttempt = new UserQuizAttempt();
        userQuizAttempt.setQuiz(quiz);
        userQuizAttempt.setUser(user);
        userQuizAttempt.setScore((int) payload.get("score"));
        userQuizAttemptService.createUserQuizAttempt(userQuizAttempt);
        return ResponseEntity.ok(userQuizAttempt);
    }

    // Update an existing UserQuizAttempt
    @PutMapping("/{id}")
    public ResponseEntity<UserQuizAttempt> updateUserQuizAttempt(@PathVariable Long id, @RequestBody UserQuizAttempt userQuizAttempt) {
        UserQuizAttempt updatedAttempt = userQuizAttemptService.updateUserQuizAttempt(id, userQuizAttempt);
        return ResponseEntity.ok(updatedAttempt);
    }

    // Delete a UserQuizAttempt
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUserQuizAttempt(@PathVariable Long id) {
        userQuizAttemptService.deleteUserQuizAttempt(id);
        return ResponseEntity.noContent().build();
    }
}