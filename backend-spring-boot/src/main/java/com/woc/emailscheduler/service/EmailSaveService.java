package com.woc.emailscheduler.service;

import com.woc.emailscheduler.dto.CompanyInfoDTO;
import com.woc.emailscheduler.model.Scheduler;
import com.woc.emailscheduler.EmailSender;
import com.woc.emailscheduler.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class EmailSaveService {

    private final EmailRepository emailRepository;
    private final EmailSender emailSender;// Inject EmailSender service

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

        long totalPendingEmails = emailRepository.countPendingEmails();
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

    public List<CompanyInfoDTO> getCompanyInfo() {
        return emailRepository.findCompanyInfo().stream()
                .map(record -> {
                    String body = (String) record[0];
                    String emailId = (String) record[1];
                    String companyName = extractCompanyName(body);
                    return new CompanyInfoDTO(companyName, emailId, (record[2] != null) ? LocalDateTime.parse(record[2].toString()) : null);
                })
                .collect(Collectors.toList());
    }

    public String extractCompanyName(String emailBody) {
        Pattern pattern = Pattern.compile("invite\\s+(\\S+)");
        Matcher matcher = pattern.matcher(emailBody);

        if (matcher.find()) {
            return matcher.group(1).trim();  // Extracted single word after "invite"
        }
        return "Unknown Company";  // Default if no match found
    }
}
