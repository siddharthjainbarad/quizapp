package com.siddharth.quizapp.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.siddharth.quizapp.model.Quiz;
import com.siddharth.quizapp.repository.QuizRepository;
import com.siddharth.quizapp.repository.UserRepository;

@Service
public class QuizeService {
    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private UserRepository userRepository;

    public Quiz createQuiz(Quiz quiz){
        return quizRepository.save(quiz); 
    }

    public List<Quiz> getAllQuizzes(){
        return quizRepository.findAll();
    }

    public Optional<Quiz> getQuizById(int id) {
        return quizRepository.findById(id);
    }

    public void validateQuiz(Quiz quiz){
        String userName = quiz.getCreatedBy();
        if(userRepository.findByUsername(userName) == null){
            throw new UsernameNotFoundException("Username does not exist");
        }
    }
}
