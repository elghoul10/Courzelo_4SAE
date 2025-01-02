package com.example.test2.Entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.*;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotBlank;


import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString

@Entity
public class User   {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @NotBlank
    @Size(max = 20)
    private String username;
    @NotBlank
    @Size(max = 50)
    @Email
    private String email;
    @NotBlank
    @Size(max = 120)
    private String password;
    @Column
    private String firstName;
    @Column
    private String lasteName;
    @Column
    private String cin;
    @Column String phone;

    private String imgUrl;


    @Column(name = "enabled")
    private Boolean enabled = false; // Par défaut, le compte n'est pas activé


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(  name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    public User(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    @ManyToMany
    private List<Grades> grades;
    @OneToMany(mappedBy ="user")
    @JsonManagedReference
    private Set<ReservationCourse> reservationCourses;

    @OneToMany(mappedBy ="createdBy")
    @JsonBackReference
    private List<Course> Courses;

    @JsonIgnore
    @OneToOne(mappedBy = "user")
    private Basket basket;


}

