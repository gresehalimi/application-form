package com.example.applicationform.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordUpdate {
    private String oldPassword;
    private String newPassword;
    private String confirmNewPassword;
}
