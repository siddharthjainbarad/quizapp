package com.siddharth.quizapp.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.siddharth.quizapp.exception.QuizNotFoundException;
import com.siddharth.quizapp.model.Answer;
import com.siddharth.quizapp.model.Question;
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
                .orElseThrow(() -> {
                    throw new QuizNotFoundException("Quiz with id " + id + " not found");
                });
    }

    @PostMapping("/createQuiz")
    public ResponseEntity<?> createQuiz(@RequestBody Quiz quiz) {
        try {
            quizService.validateQuiz(quiz);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        User user = userService.findByUsername(quiz.getCreatedBy());
        quiz.setCreatedBy(user.getUsername());
        quizService.createQuiz(quiz);
        return ResponseEntity.ok(quiz.getTitle() + " : Created Successfully");
    }

    @PostMapping("/submitQuiz")
    public ResponseEntity<?> addQuestion(@RequestBody Map<String, Object> payload) {
        int quizId = Integer.parseInt((String) payload.get("quizId"));
        System.out.println(quizId);
        List<Map<String, Object>> questionsData = (List<Map<String, Object>>) payload.get("questions");
        Optional<Quiz> quizOptional = quizService.getQuizById(quizId);
        if (!quizOptional.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Quiz not found");
        }
        Quiz quiz = quizOptional.get();
        for (Map<String, Object> questionData : questionsData) {
            Question question = new Question();
            question.setText((String) questionData.get("text"));
            question.setQuiz(quiz);

            List<Map<String, Object>> answersData = (List<Map<String, Object>>) questionData.get("answers");
            Set<Answer> answers = new HashSet<>();
            for (Map<String, Object> answerData : answersData) {
                Answer answer = new Answer();
                answer.setText((String) answerData.get("text"));
                answer.setCorrect((boolean) answerData.get("isCorrect"));
                answer.setQuestion(question);
                answers.add(answer);
            }
            question.setAnswers(answers);
            quiz.getQuestions().add(question);
        }
        quizService.createQuiz(quiz); // Save the quiz with new questions and answers
        Map<String, String> response = new HashMap<>();
        response.put("message", "Questions Created Successfully");
        return ResponseEntity.ok(response);
    }

}
