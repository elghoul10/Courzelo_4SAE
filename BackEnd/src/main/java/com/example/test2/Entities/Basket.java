package com.example.test2.Entities;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
public class Basket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;


    @Column
    private String promoCode;
    @Column
    private Double Total ;

    @Column
    private Date date_add;

    @OneToMany(mappedBy = "basket")
    private Set<Purshase> purchases;

    @OneToOne()
    private User user;
}
