package com.example.test2.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long idEvent;
    String nomLesson;
    String descriptionLesson;
    Date dateDebLesson;
    Date dateFinLesson;
    String lieuLesson;
    String imageLesson;

    private String title;
    private String description;
    private int durationInMinutes;
    private boolean isCompleted;
    private String difficultyLevel;
    private String location;

    @ManyToOne
    @JoinColumn(name = "course")
    Course course;

}
