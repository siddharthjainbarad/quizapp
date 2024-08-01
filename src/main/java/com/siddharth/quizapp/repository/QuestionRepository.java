package com.siddharth.quizapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.siddharth.quizapp.model.Question;

public interface QuestionRepository extends JpaRepository<Question, Integer> {
}
