package com.siddharth.quizapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.siddharth.quizapp.model.Answer;

public interface AnswerRepository extends JpaRepository<Answer, Integer> {
}
