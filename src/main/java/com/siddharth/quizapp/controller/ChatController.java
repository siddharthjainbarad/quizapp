package com.siddharth.quizapp.controller;

import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.siddharth.quizapp.exception.QuizNotFoundException;
import com.siddharth.quizapp.model.Quiz;
import com.siddharth.quizapp.service.ChatService;
import com.siddharth.quizapp.service.QuizeService;

@RestController
@RequestMapping("/api/chat")
public class ChatController {

    private final ChatService chatService;

    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }

    @Autowired
    QuizeService quizeService;

    @Autowired
    QuizController quizController;

// ChatController.java
    @PostMapping("/addQuestions/{quizId}")
    public ResponseEntity<?> ask(@PathVariable int quizId) {
        Quiz quiz = quizeService.getQuizById(quizId)
                .orElseThrow(() -> {
                    throw new QuizNotFoundException("Quiz with id " + quizId + " not found");
                });
        String userPrompt = (quiz.getTitle() + "(" + quiz.getTopic() + ")," + quiz.getId());
        CompletableFuture<String> futureResponse = chatService.ask(userPrompt);
        try {
            String response = futureResponse.get();
            System.out.println("\n\n\n\n" + response + "\n\n\n\n\n");
            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> payload = mapper.readValue(response, Map.class);
            return quizeService.addQuestionsToQuiz(payload);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing request");
        }
    }
}
