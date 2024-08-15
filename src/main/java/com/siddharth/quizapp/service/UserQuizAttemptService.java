package com.siddharth.quizapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.siddharth.quizapp.model.UserQuizAttempt;
import com.siddharth.quizapp.repository.UserQuizAttemptRepository;

@Service
public class UserQuizAttemptService {

    @Autowired
    private UserQuizAttemptRepository userQuizAttemptRepository;

    // Get all UserQuizAttempts
    public List<UserQuizAttempt> getAllUserQuizAttempts() {
        return userQuizAttemptRepository.findAll();
    }

    // Get a specific UserQuizAttempt by ID
    public UserQuizAttempt getUserQuizAttemptById(Long id) {
        Optional<UserQuizAttempt> attempt = userQuizAttemptRepository.findById(id);
        return attempt.orElse(null);
    }

    // Create a new UserQuizAttempt
    public UserQuizAttempt createUserQuizAttempt(UserQuizAttempt userQuizAttempt) {
        return userQuizAttemptRepository.save(userQuizAttempt);
    }

    // Update an existing UserQuizAttempt
    public UserQuizAttempt updateUserQuizAttempt(Long id, UserQuizAttempt userQuizAttempt) {
        if (userQuizAttemptRepository.existsById(id)) {
            userQuizAttempt.setId(id);
            return userQuizAttemptRepository.save(userQuizAttempt);
        }
        return null;
    }

    // Delete a UserQuizAttempt
    public void deleteUserQuizAttempt(Long id) {
        userQuizAttemptRepository.deleteById(id);
    }
}