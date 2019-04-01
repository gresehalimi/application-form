package com.example.applicationform.controller;

import com.example.applicationform.dto.ApplicationDto;
import com.example.applicationform.service.ApplicationService;
import com.example.applicationform.type.ResponseStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("application")
public class ApplicationController {

    @Autowired
    ApplicationService applicationService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestParam("essay") String essay, @RequestParam("file") MultipartFile file, @RequestParam("personId") Long personId) {

        ResponseEntity<?> responseEntity;

        ResponseStatusEnum result = applicationService.create(essay, file, personId);

        if (result.equals(ResponseStatusEnum.SUCCESS)) {
            responseEntity = ResponseEntity.status(HttpStatus.CREATED).body(result);
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
        }

        return responseEntity;
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestParam("essay") String essay, @RequestParam("file") MultipartFile file, @PathVariable("id") Long id) {

        ResponseStatusEnum result = applicationService.update(essay, file, id);

        if (result.equals(ResponseStatusEnum.SUCCESS))
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(result);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {

        ResponseStatusEnum result = applicationService.delete(id);

        if (result.equals(ResponseStatusEnum.SUCCESS))
            return ResponseEntity.status(HttpStatus.GONE).body(result);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(result);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> get(@PathVariable("id") Long id) {
        ApplicationDto application = applicationService.getApplication(id);

        if (application == null)
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseStatusEnum.NOT_FOUND);

        return ResponseEntity.ok(application);
    }


}
