package com.example.applicationform.validation;

import com.example.applicationform.dto.UserRegister;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;


@Component
public class UserValidation implements Validator {

    @Override
    public boolean supports(Class<?> aClass) {
        return UserRegister.class.isAssignableFrom(aClass);
    }

    @Override
    public void validate(Object object, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "name", "name.required", "Name is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "surname", "surname.required", "Surname is required.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "username.required", "Username is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "email.required", "Email is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "password.required", "Password is required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "confirmPassword", "confirmPassword.required", "Confirm Password must be the same as Password");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "phone", "phone.required", "Role Name is required");

        if (errors.hasErrors()) {
            return;
        }
        UserRegister userRegister = (UserRegister) object;

        //Name
        if (userRegister.getName().length() > 45) {
            errors.rejectValue("name", "name.length", "name must be less than 45 characters");
        } else if (!userRegister.getName().matches("^[a-zA-Z]+$")) {
            errors.rejectValue("name", "name.invalid", "name must contain only letters");
        }

        //Surname
        if (userRegister.getSurname().length() > 45) {
            errors.rejectValue("surname", "surname.length", "surname must be less than 45 characters");
        } else if (!userRegister.getSurname().matches("^[a-zA-Z]+$")) {
            errors.rejectValue("surname", "surname.invalid", "surname must contain only letters");
        }

        //Username
        if (userRegister.getUsername().length() > 9) {
            errors.rejectValue("username", "username.length", "username must be less than 9 characters");
        } else if (!userRegister.getSurname().matches("^[A-Za-z0-9_.]+$")) {
            errors.rejectValue("username", "username.invalid", "username must contain letters, numbers and from the symbols only _ and . ");
        }

        //Email
        if (userRegister.getEmail().length() > 45) {
            errors.rejectValue("email", "email.length", "email must be less than 45 characters");
        } else if (!userRegister.getEmail().matches("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")) {
            errors.rejectValue("email", "email.invalid", "email is not in the right format");
        }
        //Password
        if (userRegister.getPassword().length() < 6) {
            errors.rejectValue("password", "password.length", "Password must contain more than 6 characters");
        } else if (!userRegister.getPassword().matches("^[a-zA-Z0-9_!#$%&’*+/=?`{|}~^.-]+$")) {
            errors.rejectValue("password", "password.invalid", "Password is Malformed, try another password");
        }

        //Password Matching
        if (!userRegister.getPassword().equals(userRegister.getConfirmPassword())) {
            errors.rejectValue("confirmPassword", "confirmPassword.notequal", "Confirm Password must be the same as Password");
        }


        //Phone
        if (userRegister.getPhone().length() > 20) {
            errors.rejectValue("phone", "phone.length", "Phone must have less then 20 numbers");
        } else if (!userRegister.getPhone().matches("^[+0-9]+$")) {
            errors.rejectValue("phone", "phone.invalid", "Phone can't contain other characters than + and numbers");
        }

    }
}