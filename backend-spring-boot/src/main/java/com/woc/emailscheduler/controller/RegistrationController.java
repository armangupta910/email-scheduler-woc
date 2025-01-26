package com.woc.emailscheduler.controller;

import com.woc.emailscheduler.entity.RegistrationDetails;
import com.woc.emailscheduler.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/") 
public class RegistrationController {

    @Autowired
    private UserServiceImpl registrationService;

    @PostMapping("/login")
    public ResponseEntity<String> register(@RequestBody RegistrationDetails rd) {
        // Validate the registration details
        if (rd.getName() == null || rd.getEmailId() == null ||
                rd.getPassword() == null || rd.getPhoneNumber() == null ||
                rd.getDesignation() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("All fields are required.");
        }

        // Call the service to save the registration details
        boolean isRegistered = registrationService.registerUser (rd);

        if (isRegistered) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Registration successful for " + rd.getName());
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Registration failed. Please try again.");
        }
    }
}
