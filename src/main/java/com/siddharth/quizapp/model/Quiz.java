package com.siddharth.quizapp.model;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Quiz {
    @Id
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
    @ManyToOne
    @JoinColumn(name = "created_by")
    private String createdBy;

    @Setter
    @Getter
    @Column(nullable = false, updatable=false)
    private Timestamp createdAt = Timestamp.valueOf(LocalDateTime.now());;
}
