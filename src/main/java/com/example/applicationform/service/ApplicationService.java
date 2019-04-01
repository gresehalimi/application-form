package com.example.applicationform.service;

import com.example.applicationform.dto.ApplicationDto;
import com.example.applicationform.entity.Application;
import com.example.applicationform.entity.File;
import com.example.applicationform.entity.User;
import com.example.applicationform.mail.MailService;
import com.example.applicationform.mail.MailTemplate;
import com.example.applicationform.repository.ApplicationRepository;
import com.example.applicationform.repository.UserRepository;
import com.example.applicationform.type.ResponseStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class ApplicationService {

    @Autowired
    ApplicationRepository applicationRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    MailService mailService;

    public ResponseStatusEnum create(String essay, MultipartFile multipartFile, Long userId) {

        ResponseStatusEnum result = ResponseStatusEnum.ERROR;

        Optional<User> optionalUser = userRepository.findById(userId);
        if (!optionalUser.isPresent()) {
            return result;
        }
        try {
            Application application = new Application();
            application.setEssay(essay);
            application.setUser(optionalUser.get());

            File file = new File(multipartFile.getOriginalFilename(), getFileExtension(multipartFile.getOriginalFilename()), multipartFile.getBytes());
            file.setApplication(application);

            application.setFile(file);

            applicationRepository.save(application);

            mailService.sendEmail(new MailTemplate("EmailNo1@gmail.com", "EmailNo2@abidat.de", "Confirmation", " Your application is submitted"));

            result = ResponseStatusEnum.SUCCESS;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }


    public ResponseStatusEnum update(String essay, MultipartFile multipartFile, Long id) {
        ResponseStatusEnum result;
        Optional<Application> optionalApplication = applicationRepository.findById(id);
        if (!optionalApplication.isPresent()) {
            return ResponseStatusEnum.ERROR;
        }

        try {
            Application application = optionalApplication.get();

            application.setEssay(essay);

            File file = application.getFile();

            file.setFile(multipartFile.getBytes());
            file.setFileName(multipartFile.getOriginalFilename());
            file.setExtension(getFileExtension(multipartFile.getOriginalFilename()));

            applicationRepository.save(application);
            result = ResponseStatusEnum.SUCCESS;
        } catch (IOException e) {
            System.err.println(e.getMessage());
            result = ResponseStatusEnum.ERROR;
        }
        return result;
    }


    public ResponseStatusEnum delete(Long id) {

        ResponseStatusEnum result = ResponseStatusEnum.ERROR;

        Optional<Application> optionalApplication = applicationRepository.findById(id);
        if (!optionalApplication.isPresent()) {
            return result;
        }
        try {
            Application application = optionalApplication.get();
            applicationRepository.delete(application);
            result = ResponseStatusEnum.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    private String getFileExtension(String fileName) {
        return fileName.substring(fileName.indexOf('.'));
    }


    public ApplicationDto getApplication(Long id) {
        Optional<Application> optionalApplication = applicationRepository.findById(id);

        if (!optionalApplication.isPresent()) {
            return null;
        }

        Application application = optionalApplication.get();

        User user = application.getUser();

        return new ApplicationDto(
                user.getName() + " " + user.getSurname(),
                application.getFile().getFileName(),
                application.getEssay(),
                "file/download/" + application.getFile().getId()
        );
    }

}
