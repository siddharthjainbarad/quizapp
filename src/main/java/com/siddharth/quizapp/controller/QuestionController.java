package com.siddharth.quizapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.siddharth.quizapp.service.QuizeService;

@RestController
@RequestMapping("/api/question")
public class QuestionController {

    @Autowired
    private QuizeService quizService;

    @DeleteMapping("/{questionId}")
    public ResponseEntity<?> deleteQuestion(@PathVariable int questionId) {
        boolean isDeleted = quizService.deleteQuestionById(questionId);
        if (isDeleted) {
            return ResponseEntity.ok("Question deleted successfully");
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Question not found");
        }
    }
}