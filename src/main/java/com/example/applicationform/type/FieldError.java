package com.example.applicationform.type;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@EqualsAndHashCode(callSuper = false)
public class FieldError {
    private String field;
    private String message;
}

