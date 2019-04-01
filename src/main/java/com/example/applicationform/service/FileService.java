package com.example.applicationform.service;

import com.example.applicationform.dto.FileDto;
import com.example.applicationform.entity.File;
import com.example.applicationform.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FileService {

    @Autowired
    FileRepository fileRepository;

    @Value("${download.uri}")
    private String downloadUri;

    public FileDto loadFile(Long id) {

        Optional<File> optionalFile = fileRepository.findById(id);
        if (!optionalFile.isPresent()) {
            return null;
        }
        File file = optionalFile.get();

        return new FileDto(file.getFileName(), file.getFile());
    }

}
