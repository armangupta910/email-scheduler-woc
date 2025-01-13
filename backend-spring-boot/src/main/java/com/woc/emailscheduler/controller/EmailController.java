package com.woc.emailscheduler.controller;

import com.woc.emailscheduler.model.scheduler;
import com.woc.emailscheduler.service.email_save;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/email/save")
public class EmailController {

    private final email_save email_save;

    public EmailController(email_save email_save) {
        this.email_save = email_save;
    }

    @PostMapping
    public ResponseEntity<scheduler> createEmail(@RequestBody scheduler email) {
        try {
            scheduler savedEmail = email_save.saveEmail(email);
            return new ResponseEntity<>(savedEmail, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

