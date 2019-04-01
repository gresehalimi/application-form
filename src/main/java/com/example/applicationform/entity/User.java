package com.example.applicationform.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class User {

    @Column(name = "id")
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "name", length = 50)
    private String name;

    @Column(name = "surname", length = 50)
    private String surname;

    @Column(name = "username", length = 50, unique = true)
    private String username;

    @Column(name = "password", length = 50)
    private String password;

    @Column(name = "confirmPassword", length = 50)
    private String confirmPassword;

    @Column(name = "email", length = 30, unique = true)
    private String email;

    @Column(name = "phone", length = 30, unique = true)
    private String phone;

    @OneToMany(targetEntity = Application.class, mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Application> applications;

}
