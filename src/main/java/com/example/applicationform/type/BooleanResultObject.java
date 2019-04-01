package com.example.applicationform.type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BooleanResultObject {

    private Integer status;

    private ResponseStatusEnum responseStatus;

    private String message;
}
