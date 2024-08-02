package com.siddharth.quizapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.siddharth.quizapp.exception.QuizNotFoundException;
import com.siddharth.quizapp.model.Quiz; // Add this import statement
import com.siddharth.quizapp.model.User;
import com.siddharth.quizapp.service.QuizeService;
import com.siddharth.quizapp.service.UserService;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {

    @Autowired
    private QuizeService quizService;

    @Autowired
    private UserService userService;


    @GetMapping
    public List<Quiz> getAllQuizzes() {
        return quizService.getAllQuizzes();
    }

    @GetMapping("/{id}")
    public Quiz getQuizByIdQuiz(@PathVariable int id) {
        return quizService.getQuizById(id)
            .orElseThrow(() -> { throw new QuizNotFoundException("Quiz with id " + id + " not found"); });
        }

    @PostMapping("createQuiz")
    public Quiz createQuiz(@RequestBody Quiz quiz) {
        User user = userService.findByUsername(quiz.getCreatedBy());
        //TODO handle null user check;
        quiz.setCreatedBy(user.getUsername());
        return quizService.createQuiz(quiz);
    }

}
