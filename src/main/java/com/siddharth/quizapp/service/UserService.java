package com.siddharth.quizapp.service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.siddharth.quizapp.exception.DuplicateException;
import com.siddharth.quizapp.model.User;
import com.siddharth.quizapp.model.UserProfile;
import com.siddharth.quizapp.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional
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

    public String updateProfile(String username, String email, String firstName, String lastName, String dob, String password) {
        User user = findByUsername(username);
        if (user == null) {
            return "User not found";
        }
    
        boolean updated = false;
    
        if (email != null && !email.trim().isEmpty() && (user.getEmail() == null || user.getEmail().trim().isEmpty())) {
            user.setEmail(email.trim());
            updated = true;
        }
    
        UserProfile profile = user.getUserProfile();
        if (profile == null) {
            profile = new UserProfile();
            profile.setUser(user);
        }
    
        if (firstName != null && !firstName.trim().isEmpty() && (profile.getFirstName() == null || profile.getFirstName().trim().isEmpty())) {
            profile.setFirstName(firstName.trim());
            updated = true;
        }
    
        if (lastName != null && !lastName.trim().isEmpty() && (profile.getLastName() == null || profile.getLastName().trim().isEmpty())) {
            profile.setLastName(lastName.trim());
            updated = true;
        }
    
        if (dob != null && !dob.trim().isEmpty() && profile.getDateOfBirth() == null) {
            try {
                LocalDate dateOfBirth = LocalDate.parse(dob.trim());
                profile.setDateOfBirth(dateOfBirth);
                updated = true;
            } catch (DateTimeParseException e) {
                return "Invalid date format for date of birth";
            }
        }
    
        if (password != null && !password.trim().isEmpty()) {
            user.setPassword(encoder.encode(password.trim()));
            updated = true;
        }
    
        if (updated) {
            user.setUserProfile(profile);
            userRepository.save(user);
            // We don't need to explicitly save the profile, as it will be saved due to the cascade relationship
            return "Profile updated successfully";
        } else {
            return "No changes were made to the profile";
        }
    }
}
