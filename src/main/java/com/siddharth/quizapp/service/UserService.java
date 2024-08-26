package com.siddharth.quizapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.siddharth.quizapp.exception.DuplicateException;
import com.siddharth.quizapp.model.User;
import com.siddharth.quizapp.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;
    
    BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public User registerUser(User user) {
        validateUser(user);
        String encodedPassword  = encoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findById(int id) {
        return userRepository.findById(id);
    }

    public List<User> getAllUsers(){
        return userRepository.findAll();
    }
    
    public void validateUser(User user) {
        String username = user.getUsername();
        String email = user.getEmail();
        if (userRepository.findByUsername(username) != null) {
            throw new DuplicateException("Username already exists");
        }
        if (userRepository.findByEmail(email) != null) {
            throw new DuplicateException("Email already exists");
        }
    }
    
    public boolean validateCredentials(String username, String password) {
        User user = userRepository.findByUsername(username);
        return user != null && user.getPassword().equals(password);
    }

    public boolean deleteUserById(int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isPresent()) {
            userRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
