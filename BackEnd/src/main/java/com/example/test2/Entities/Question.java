package com.example.test2.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

@Entity
public class Question {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long questionId;

    private String question;

    private String questionImage;

    @ManyToOne
    private Quiz quiz;
    @OneToMany(mappedBy = "question")
    private List<Answers> answers;

    // Constructors, getters, and setters
}
