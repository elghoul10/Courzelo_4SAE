package com.example.test2.Entities;

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
public class Quiz {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_id")
    private Long quizId;

    @Column(name = "quiz_name")
    private String quizName;

    @Temporal(TemporalType.TIME)
    private Date quizDuration;

    @Temporal(TemporalType.TIMESTAMP)
    private Date quizStartDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date quizEndDate;
    @OneToMany(mappedBy = "quiz")
    private List<Grades> grades;
    @OneToMany(mappedBy = "quiz")
    private List<Question> questions;
}
