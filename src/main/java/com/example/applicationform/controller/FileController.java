package com.example.applicationform.controller;

import com.example.applicationform.dto.FileDto;
import com.example.applicationform.service.FileService;
import com.example.applicationform.type.ResponseStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("file")
public class FileController {

    @Autowired
    FileService fileService;

    @GetMapping("/download/{id}")
    public ResponseEntity<?> downloadFile(@PathVariable Long id) {
        // Load file from database
        FileDto file = fileService.loadFile(id);

        if (file == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ResponseStatusEnum.NOT_FOUND);
        }
        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                .body(new ByteArrayResource(file.getFile()));
    }
}
