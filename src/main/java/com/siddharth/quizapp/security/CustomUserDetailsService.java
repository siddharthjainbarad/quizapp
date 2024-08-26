package com.siddharth.quizapp.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.siddharth.quizapp.model.User;
import com.siddharth.quizapp.repository.UserRepository;

public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repo;

    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Attempting to load user by username: {}", username);
        User user = repo.findByUsername(username);
        if (user == null) {
            logger.error("User not found: {}", username);
            throw new UsernameNotFoundException(username + " not found");
        }
        logger.info("User found: {}", username);
        return new CustomUserDetails(user);
    }
}