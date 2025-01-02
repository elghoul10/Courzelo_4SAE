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
public class Grades {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "grade_id")
    private Long gradeId;

    private Float grade;

    @Temporal(TemporalType.DATE)
    private Date date;

    @ManyToMany(mappedBy = "grades")
    private List<User> users;
    @ManyToOne
    private Quiz quiz;
}