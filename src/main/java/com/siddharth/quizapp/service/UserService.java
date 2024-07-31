package com.siddharth.quizapp.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.siddharth.quizapp.model.User;
import com.siddharth.quizapp.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    
    public User registerUser(User user) {
        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findById(int id) {
        return userRepository.findById(id);
    }
}
