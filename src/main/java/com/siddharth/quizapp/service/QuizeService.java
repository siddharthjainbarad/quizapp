package com.siddharth.quizapp.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.siddharth.quizapp.model.Quiz;
import com.siddharth.quizapp.repository.QuizRepository;

@Service
public class QuizeService {
    @Autowired
    private QuizRepository quizRepository;

    public Quiz createQuiz(Quiz quiz){
        return quizRepository.save(quiz); 
    }

    public List<Quiz> getAllQuizzes(){
        return quizRepository.findAll();
    }

    public Quiz getQuizById(int id) {
        return quizRepository.findById(id).orElse(null);
    }
}
