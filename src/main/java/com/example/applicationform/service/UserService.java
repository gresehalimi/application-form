package com.example.applicationform.service;

import com.example.applicationform.dto.ApplicationDto;
import com.example.applicationform.dto.PasswordUpdate;
import com.example.applicationform.dto.UserRegister;
import com.example.applicationform.dto.UserUpdate;
import com.example.applicationform.entity.Application;
import com.example.applicationform.entity.User;
import com.example.applicationform.mail.MailService;
import com.example.applicationform.mail.MailTemplate;
import com.example.applicationform.repository.UserRepository;
import com.example.applicationform.type.BooleanResultObject;
import com.example.applicationform.type.ResponseStatusEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    MailService mailService;

    public BooleanResultObject create(UserRegister userRegister) {
        BooleanResultObject resultObject = new BooleanResultObject();
        resultObject.setResponseStatus(ResponseStatusEnum.CONFLICT);

        Optional<User> optionalUser = userRepository.findByEmail(userRegister.getEmail());
        if (optionalUser.isPresent()) {
            resultObject.setStatus(409);
            resultObject.setMessage("Email exists!");
            return resultObject;
        }

        Optional<User> optionalUserByPhone = userRepository.findByPhone(userRegister.getPhone());
        if (optionalUserByPhone.isPresent()) {
            resultObject.setStatus(409);
            resultObject.setMessage("Username exists!");
            return resultObject;
        }
        try {
            User user = new User();
            user.setName(userRegister.getName());
            user.setSurname(userRegister.getSurname());
            user.setUsername(userRegister.getUsername());
            user.setEmail(userRegister.getUsername());
            user.setPhone(userRegister.getPhone());
            user.setPassword(userRegister.getPassword());
            user.setConfirmPassword(userRegister.getConfirmPassword());

            userRepository.save(user);
            mailService.sendEmail(new MailTemplate("EmailNo1@gmail.com", "EmailNo2@abidat.de", "Confirmation", "Your Registration is Done"));

            resultObject.setStatus(201);
            resultObject.setResponseStatus(ResponseStatusEnum.CREATED);
            resultObject.setMessage("Success!");
        } catch (Exception e) {
            e.printStackTrace();
            resultObject.setStatus(500);
            resultObject.setResponseStatus(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
            resultObject.setMessage("Can not be created!");
        }

        return resultObject;
    }

    public BooleanResultObject updateUser(UserUpdate userUpdate) {
        BooleanResultObject resultObject = new BooleanResultObject();
        resultObject.setResponseStatus(ResponseStatusEnum.CONFLICT);
        try {
            Optional<User> optionalUser = userRepository.findById(1L);
            if (!optionalUser.isPresent()) {
                resultObject.setStatus(409);
                resultObject.setMessage("User does not exist!");
                return resultObject;
            }
            User user = optionalUser.get();

            user.setName(userUpdate.getName());
            user.setSurname(userUpdate.getSurname());
            user.setPhone(userUpdate.getPhone());
            user.setUsername(userUpdate.getUsername());

            userRepository.save(user);

            resultObject.setStatus(201);
            resultObject.setResponseStatus(ResponseStatusEnum.SUCCESS);
            resultObject.setMessage("Username Successfully Updated");

        } catch (Exception e) {
            e.printStackTrace();
            resultObject.setStatus(500);
            resultObject.setResponseStatus(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
            resultObject.setMessage("Username could not be Updated!");
        }
        return resultObject;
    }

    public BooleanResultObject updatePassword(PasswordUpdate passwordUpdate) {
        BooleanResultObject resultObject = new BooleanResultObject();
        resultObject.setResponseStatus(ResponseStatusEnum.INTERNAL_SERVER_ERROR);

        Optional<User> optionalUser = userRepository.findById(1L);
        if (!optionalUser.isPresent()) {
            return resultObject;
        }

        try {

            User user = optionalUser.get();

            if (user.getPassword().equals(passwordUpdate.getOldPassword())) {
                if (passwordUpdate.getNewPassword().equals(passwordUpdate.getConfirmNewPassword())) {
                    user.setPassword(passwordUpdate.getConfirmNewPassword());
                } else {
                    return resultObject;
                }

                userRepository.save(user);
                resultObject.setStatus(201);
                resultObject.setResponseStatus(ResponseStatusEnum.SUCCESS);
                resultObject.setMessage("Password Successfully Updated");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resultObject.setStatus(500);
            resultObject.setResponseStatus(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
            resultObject.setMessage("Password could not be Updated!");
        }
        return resultObject;
    }

    public BooleanResultObject updateEmail(String email) {
        BooleanResultObject resultObject = new BooleanResultObject();
        resultObject.setResponseStatus(ResponseStatusEnum.CONFLICT);
        try {


            Optional<User> optionalUser = userRepository.findById(6L);
            if (!optionalUser.isPresent()) {
                throw new Exception();
            }

            User user = optionalUser.get();

            user.setEmail(email);

            userRepository.save(user);
            resultObject.setStatus(201);
            resultObject.setResponseStatus(ResponseStatusEnum.SUCCESS);
            resultObject.setMessage("Email Successfully Updated");

        } catch (Exception e) {
            e.printStackTrace();
            resultObject.setStatus(500);
            resultObject.setResponseStatus(ResponseStatusEnum.INTERNAL_SERVER_ERROR);
            resultObject.setMessage("Email could not be Updated!");
        }
        return resultObject;
    }


    public List<ApplicationDto> getAllUsersApplications(Long userId) {
        Optional<User> optionalPerson = userRepository.findById(userId);
        if (!optionalPerson.isPresent()) {
            return null;
        }
        List<ApplicationDto> result = null;
        try {
            User user = optionalPerson.get();
            List<Application> applications = user.getApplications();

            result = new ArrayList<>();
            for (Application application : applications) {
                ApplicationDto a = new ApplicationDto();
                a.setEssay(application.getEssay());
                a.setFileName(application.getFile().getFileName());
                a.setFileUrl("file/download/" + application.getFile().getId());
                result.add(a);
            }
        } catch (Exception e) {

            e.printStackTrace();
        }
        return result;
    }
}

