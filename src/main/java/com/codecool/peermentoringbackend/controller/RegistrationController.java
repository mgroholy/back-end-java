package com.codecool.peermentoringbackend.controller;

import com.codecool.peermentoringbackend.model.RegResponse;
import com.codecool.peermentoringbackend.model.UserModel;
import com.codecool.peermentoringbackend.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@CrossOrigin(origins = "https://keen-murdock-7f431a.netlify.app", allowCredentials = "true")
@RequestMapping("/reg")
public class RegistrationController {

    @Autowired
    protected RegistrationService registrationService;

    @PostMapping(value = "/registration")
    public void doRegistration(HttpServletResponse response, @RequestBody UserModel userModel) throws IOException {

        RegResponse regResponse = registrationService.handleRegistration(userModel);
        if (regResponse.isSuccess()) {
            response.setStatus(200);
        } else {
            response.setStatus(400);
        }
        response.getWriter().println(regResponse.getMessage());
    }

}
