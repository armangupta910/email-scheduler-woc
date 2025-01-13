package com.woc.emailscheduler.service;

import com.woc.emailscheduler.model.scheduler;
import com.woc.emailscheduler.repository.EmailRepository;
import org.springframework.stereotype.Service;

@Service
public class email_save {

    private final EmailRepository emailRepository;

    public email_save(EmailRepository emailRepository) {
        this.emailRepository = emailRepository;
    }

    public scheduler saveEmail(scheduler email) {
        return emailRepository.save(email);
    }
}
