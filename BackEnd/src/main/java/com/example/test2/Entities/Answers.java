package com.example.test2.Entities;


import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

@Entity
public class Answers {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long answerId;

    private String announcement;


    private Boolean isCorrect;

    private String explication;
    @ManyToOne
    private Question question;

    // Constructors, getters, and setters
}
