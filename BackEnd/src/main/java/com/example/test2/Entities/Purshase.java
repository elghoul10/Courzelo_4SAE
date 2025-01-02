package com.example.test2.Entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity

public class Purshase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private  Long id;
    private double prix;
    @Column
    private double remise;
    @Column
    private Date date_purchase;

    @Column
    private String Status;

    @JsonIgnore
    @ManyToOne()
    private Basket basket;

    @ManyToOne()
    private Course course;


}