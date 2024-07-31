package com.siddharth.quizapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.siddharth.quizapp.model.Quiz;
import com.siddharth.quizapp.service.QuizeService;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {

    @Autowired
    private QuizeService quizService;

    @GetMapping
    public List<Quiz> getAllQuizzes() {
        return quizService.getAllQuizzes();
    }

    @GetMapping("/{id}")
    public Quiz getQuizByIdQuiz(@RequestParam int id) {
        return quizService.getQuizById(id);
    }
    

    @PostMapping("createQuiz")
    public Quiz createQuiz(@RequestBody Quiz quiz) {
        return quizService.createQuiz(quiz);
    }
    
    // @GetMapping("/{id}")
    // public Optional<Quiz> getQuizById(@PathVariable Long id) {
    //     return quizService.getQuizById(id);
    // }

}
