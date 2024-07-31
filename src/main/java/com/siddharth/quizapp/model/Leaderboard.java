// package com.siddharth.quizapp.model;

// import jakarta.persistence.Column;
// import jakarta.persistence.Entity;
// import jakarta.persistence.GeneratedValue;
// import jakarta.persistence.GenerationType;
// import jakarta.persistence.Id;
// import jakarta.persistence.JoinColumn;
// import jakarta.persistence.ManyToOne;
// import lombok.Getter;
// import lombok.Setter;

// @Entity
// public class Leaderboard {
//     @Id
//     @GeneratedValue(strategy=GenerationType.IDENTITY)
//     private Long id;

//     @Getter
//     @Setter
//     @ManyToOne
//     @JoinColumn(name = "user_id")
//     private User user;

//     @Getter
//     @Setter
//     @ManyToOne
//     @JoinColumn(name = "quiz_id")
//     private Quiz quiz;

//     @Getter
//     @Setter
//     @ManyToOne
//     @JoinColumn(name = "question_id")
//     private Question question;

//     @Getter
//     @Setter
//     @Column(nullable = false)
//     private String selectedAnswer;

// }
