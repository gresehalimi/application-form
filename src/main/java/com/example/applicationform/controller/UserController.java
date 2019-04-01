package com.example.applicationform.controller;

import com.example.applicationform.dto.ApplicationDto;
import com.example.applicationform.dto.PasswordUpdate;
import com.example.applicationform.dto.UserRegister;
import com.example.applicationform.dto.UserUpdate;
import com.example.applicationform.service.UserService;
import com.example.applicationform.type.BooleanResultObject;
import com.example.applicationform.type.ErrorMessageResultObject;
import com.example.applicationform.type.FieldErrorResultObject;
import com.example.applicationform.type.ResponseStatusEnum;
import com.example.applicationform.validation.UserValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("perso")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserValidation userValidation;

    @PostMapping(value = "/create", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> create(@RequestBody @Valid UserRegister userRegister, HttpServletRequest request, BindingResult bindingResult) {

        ResponseEntity responseEntity;

        FieldErrorResultObject fieldErrorResultObject = new FieldErrorResultObject();
        fieldErrorResultObject.setResponseStatus(ResponseStatusEnum.FIELD_ERROR);
        List<com.example.applicationform.type.FieldError> list = new ArrayList<>();

        userValidation.validate(userRegister, bindingResult);
        if (bindingResult.hasErrors()) {
            for (Object object : bindingResult.getAllErrors()) {
                if (object instanceof FieldError) {
                    FieldError fieldError = (FieldError) object;
                    com.example.applicationform.type.FieldError fieldError1 = new com.example.applicationform.type.FieldError(fieldError.getField(), fieldError.getDefaultMessage());
                    list.add(fieldError1);
                }
            }
            fieldErrorResultObject.setFieldsError(list);
            responseEntity = ResponseEntity.status(HttpStatus.BAD_REQUEST).body(fieldErrorResultObject);
            return responseEntity;
        }

        BooleanResultObject resultObject = userService.create(userRegister);

        if (resultObject.getResponseStatus() == ResponseStatusEnum.INTERNAL_SERVER_ERROR) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ErrorMessageResultObject(new Date(), resultObject.getStatus(), "Internal Server Error!", resultObject.getMessage(), request.getRequestURI()));
        } else if (resultObject.getResponseStatus() == ResponseStatusEnum.CONFLICT) {
            responseEntity = ResponseEntity.status(HttpStatus.CONFLICT).body(
                    new ErrorMessageResultObject(new Date(), resultObject.getStatus(), "Conflict!", resultObject.getMessage(), request.getRequestURI()));

        } else {
            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(resultObject);
        }

        return responseEntity;
    }

    @PutMapping(value = "/update", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> updateUser(@RequestBody UserUpdate userUpdate, HttpServletRequest request) {

        ResponseEntity responseEntity;

        BooleanResultObject resultObject = userService.updateUser(userUpdate);

        if (resultObject.getResponseStatus() == ResponseStatusEnum.INTERNAL_SERVER_ERROR) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ErrorMessageResultObject(new Date(), resultObject.getStatus(), "Internal Server Error!", resultObject.getMessage(), request.getRequestURI()));
        } else if (resultObject.getResponseStatus() == ResponseStatusEnum.CONFLICT) {
            responseEntity = ResponseEntity.status(HttpStatus.CONFLICT).body(
                    new ErrorMessageResultObject(new Date(), resultObject.getStatus(), "Conflict!", resultObject.getMessage(), request.getRequestURI()));

        } else {
            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(resultObject);
        }
        return responseEntity;
    }

    @PutMapping(value = "/update-email", produces = "application/json")
    public ResponseEntity<?> updateEmail(@RequestParam("email") String email, HttpServletRequest request) {

        ResponseEntity responseEntity;

        BooleanResultObject resultObject = userService.updateEmail(email);

        if (resultObject.getResponseStatus() == ResponseStatusEnum.INTERNAL_SERVER_ERROR) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ErrorMessageResultObject(new Date(), resultObject.getStatus(), "Internal Server Error!", resultObject.getMessage(), request.getRequestURI()));
        } else if (resultObject.getResponseStatus() == ResponseStatusEnum.CONFLICT) {
            responseEntity = ResponseEntity.status(HttpStatus.CONFLICT).body(
                    new ErrorMessageResultObject(new Date(), resultObject.getStatus(), "Conflict!", resultObject.getMessage(), request.getRequestURI()));

        } else {
            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(resultObject);
        }
        return responseEntity;
    }

    @PutMapping(value = "/update-password", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> updatePassword(@RequestBody PasswordUpdate passwordUpdate, HttpServletRequest request) {

        ResponseEntity responseEntity;

        BooleanResultObject resultObject = userService.updatePassword(passwordUpdate);

        if (resultObject.getResponseStatus() == ResponseStatusEnum.INTERNAL_SERVER_ERROR) {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(
                    new ErrorMessageResultObject(new Date(), resultObject.getStatus(), "Internal Server Error!", resultObject.getMessage(), request.getRequestURI()));
        } else if (resultObject.getResponseStatus() == ResponseStatusEnum.CONFLICT) {
            responseEntity = ResponseEntity.status(HttpStatus.CONFLICT).body(
                    new ErrorMessageResultObject(new Date(), resultObject.getStatus(), "Conflict !", resultObject.getMessage(), request.getRequestURI()));

        } else {
            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(resultObject);
        }
        return responseEntity;
    }

    @GetMapping("/{id}/get-applications")
    public ResponseEntity<?> getPersonApplications(@PathVariable("id") Long id) {
        List<ApplicationDto> applications = userService.getAllUsersApplications(id);
        if (applications == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseStatusEnum.NOT_FOUND);
        return ResponseEntity.ok(applications);
    }
}
