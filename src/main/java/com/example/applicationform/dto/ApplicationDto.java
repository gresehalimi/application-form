package com.example.applicationform.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ApplicationDto {

    private String fullName;

    private String fileName;

    private String essay;

    private String fileUrl;

}
