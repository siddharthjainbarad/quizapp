package com.siddharth.quizapp.service;

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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.siddharth.quizapp.model.Answer;
import com.siddharth.quizapp.model.Question;
import com.siddharth.quizapp.model.Quiz;
import com.siddharth.quizapp.repository.QuestionRepository;
import com.siddharth.quizapp.repository.QuizRepository;
import com.siddharth.quizapp.repository.UserRepository;

@Service
public class QuizeService {

    @Autowired
    private QuizRepository quizRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private QuestionRepository questionRepository;


    @Transactional
    public Quiz createQuiz(Quiz quiz) {
        return quizRepository.save(quiz);
    }

    public List<Quiz> getAllQuizzes() {
        return quizRepository.findAll();
    }

    public Optional<Quiz> getQuizById(int id) {
        return quizRepository.findById(id);
    }

    public void validateQuiz(Quiz quiz) {
        String userName = quiz.getCreatedBy();
        if (userRepository.findByUsername(userName) == null) {
            throw new UsernameNotFoundException("Username does not exist");
        }
    }

    public ResponseEntity<?> addQuestionsToQuiz(Map<String, Object> payload) {
        int quizId = (int) payload.get("quizId");
        System.out.println(quizId);
        List<Map<String, Object>> questionsData = (List<Map<String, Object>>) payload.get("questions");
        Optional<Quiz> quizOptional = getQuizById(quizId);
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
        createQuiz(quiz); // Save the quiz with new questions and answers
        Map<String, String> response = new HashMap<>();
        response.put("message", "Questions Created Successfully");
        return ResponseEntity.ok(response);
    }

    @Transactional
    public boolean deleteQuestionById(int questionId) {
        Optional<Question> questionOptional = questionRepository.findById(questionId);
        if (questionOptional.isPresent()) {
            questionRepository.deleteById(questionId);
            return true;
        } else {
            return false;
        }
    }
 
    public void deleteQuizById(int id) {
        quizRepository.deleteById(id);
    }
}
