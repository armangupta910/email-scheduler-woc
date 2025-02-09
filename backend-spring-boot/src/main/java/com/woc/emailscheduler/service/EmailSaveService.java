package com.woc.emailscheduler.service;

import com.woc.emailscheduler.model.Scheduler;
import com.woc.emailscheduler.EmailSender;
import com.woc.emailscheduler.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class EmailSaveService {

    private final EmailRepository emailRepository;
    private final EmailSender emailSender; // Inject EmailSender service

    @Autowired
    public EmailSaveService(EmailRepository emailRepository, EmailSender emailSender) {
        this.emailRepository = emailRepository;
        this.emailSender = emailSender;
    }

    // Save email and schedule/send it
    public Scheduler saveEmail(Scheduler email) {
        email.setStatus("PENDING");
        Scheduler savedEmail = emailRepository.save(email);

        // If the scheduled time is in the past, send the email immediately
        if (email.getScheduledTime().isBefore(LocalDateTime.now())) {
            sendEmailNow(email);
        }

        return savedEmail;
    }

    // Use EmailSender to send email
    private void sendEmailNow(Scheduler email) {
        emailSender.sendSimpleEmail(email.getRecipientEmail(), email.getSubject(), email.getBody());
        email.setStatus("SENT");
        emailRepository.save(email);
    }

    // Scheduler logic to send pending emails
    @Scheduled(fixedRate = 5000) // Runs every 5 seconds
    public void checkPendingEmails() {
        LocalDateTime cutoffTime = LocalDateTime.now();
        List<Scheduler> pendingEmails = emailRepository.findPendingEmails(cutoffTime);

        for (Scheduler email : pendingEmails) {
            sendEmailNow(email);
        }
    }

    // Scheduled task to send future emails that are near their scheduled time
    @Scheduled(fixedRate = 5000) // Runs every 5 seconds
    public void checkFutureEmails() {
        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime cutoffTime = currentTime.plusSeconds(5);

        List<Scheduler> futureEmails = emailRepository.findFutureEmails(currentTime, cutoffTime);

        for (Scheduler email : futureEmails) {
            sendEmailNow(email);
        }
    }

}
