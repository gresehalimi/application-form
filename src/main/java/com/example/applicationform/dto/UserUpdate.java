package com.example.applicationform.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdate {

    private String id;

    private String name;

    private String surname;

    private String username;

    private String email;

    private String password;

    private String confirmPassword;

    private String phone;
}
