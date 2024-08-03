package com.siddharth.quizapp.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Quiz {

    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Setter
    @Getter
    @Column(nullable = false)
    private String title;

    @Setter
    @Getter
    @Column(nullable = false)
    private String topic;

    @Setter
    @Getter
    @Column(nullable = false)
    private String createdBy;

    @Setter
    @Getter
    @Column(nullable = false, updatable = false)
    private Timestamp createdAt = Timestamp.valueOf(LocalDateTime.now());

    @Setter
    @Getter
    @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private Set<Question> questions;
}
