package com.example.applicationform.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FieldErrorResultObject {

    private ResponseStatusEnum responseStatus;

    private List<FieldError> fieldsError;
}
