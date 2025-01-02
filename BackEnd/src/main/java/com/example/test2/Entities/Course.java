package com.example.test2.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Course implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCourse;
    private String courseTitle;
    private String courseDescription;
    private String imgUrl;
    private Long availablePlaces;
    private Date groupTime;

    private Date meetTime;

    private String meetUrl;
    private Float prix;

    @ManyToOne
    @JoinColumn(name = "specialite")
    Specialite specialite;

    @ManyToOne
    @JsonBackReference
    private User createdBy;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL)
    @JsonIgnoreProperties("course")
    @JsonIgnore
    private Set<Lesson> lessons;
    @OneToMany(mappedBy = "courses")
    @JsonIgnore
    private Set<ReservationCourse> reservationCourses;
    @OneToMany(mappedBy = "course")
    @JsonIgnore
    private Set<Rating> ratings;

    @ToString.Exclude
    @JsonIgnore
    @OneToMany(mappedBy = "course",cascade = CascadeType.ALL)
    private Set<Purshase> purshases;
}
