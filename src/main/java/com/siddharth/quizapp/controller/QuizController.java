package com.siddharth.quizapp.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.siddharth.quizapp.exception.QuizNotFoundException;
import com.siddharth.quizapp.model.Quiz;
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
                .orElseThrow(() -> {
                    throw new QuizNotFoundException("Quiz with id " + id + " not found");
                });
    }

    @PostMapping("/createQuiz")
    public ResponseEntity<?> createQuiz(@RequestBody Quiz quiz) {
        // try {
        //     quizService.validateQuiz(quiz);
        // } catch (UsernameNotFoundException e) {
        //     return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        // }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userService.findByUsername(username);
        quiz.setCreatedBy(user.getUsername());
        quizService.createQuiz(quiz);
        return ResponseEntity.ok(quiz.getTitle() + " : Created Successfully");
    }

    @PostMapping("/submitQuiz")
    public ResponseEntity<?> addQuestion(@RequestBody Map<String, Object> payload) {
        return quizService.addQuestionsToQuiz(payload);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteQuiz(@PathVariable int id) {
        quizService.deleteQuizById(id);
        return ResponseEntity.ok("Quiz deleted successfully");
    }
}
