package com.woc.emailscheduler.controller;

import com.woc.emailscheduler.EmailSender;
import com.woc.emailscheduler.EmailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@RestController
@RequestMapping("/email")
public class MailMessage {

    @Autowired
    private EmailSender emailSender;

    // Counters for email statistics
    private final AtomicInteger countScheduledEmails = new AtomicInteger(0);
    private final AtomicInteger countSentEmails = new AtomicInteger(0);
    private final AtomicInteger countFailedEmails= new AtomicInteger(0);

    // Endpoint for sending a single email
    @PostMapping("/send")
    public ResponseEntity<String> sendEmail(@RequestBody EmailRequest emailRequest) {

        try {
            String emailBody = composeEmailBody(
                    emailRequest.getCompany(),
                    emailRequest.getSalutation(),
                    emailRequest.getName(),
                    emailRequest.getDesignation(),
                    emailRequest.getPhone()
            );
            emailSender.sendSimpleEmail(emailRequest.getEmail(), "Invitation to Participate in Campus Placement", emailBody);
            countSentEmails.incrementAndGet(); // Increment the sent emails counter
            return ResponseEntity.ok("Email sent successfully!");
        } catch (Exception e) {
            countFailedEmails.incrementAndGet(); // Increment the failed emails counter
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error sending email: " + e.getMessage());
        }
    }

    /*@GetMapping("/stats")
    public ResponseEntity<Map<String, Integer>> getEmailStats() {
        Map<String, Integer> stats = new HashMap<>();
        stats.put("totalEmailsScheduled", totalEmailsScheduled.get());
        stats.put("totalEmailsSent", totalEmailsSent.get());
        stats.put("totalFailedEmails", totalFailedEmails.get());
        return ResponseEntity.ok(stats);
    }*/

    // Method to dynamically compose the email body
    private String composeEmailBody(String company, String salutation, String name, String designation, String phone) {
        return String.format(
                "Dear %s,\n\n" +
                        "Greetings from IIT Jodhpur!\n\n" +
                        "On behalf of the Placement Cell at IIT Jodhpur, I, %s, %s, take this opportunity to invite %s to participate in our campus placement and internship season for the 2025 and 2026 batches, respectively.\n\n" +
                        "Since its inception in 2008, IIT Jodhpur has achieved several milestones and has always strived to push its limits in all spheres. The institute has a large pool of talented students pursuing their interests through a wide range of academic programs. Notably, IIT Jodhpur secured the 29th rank in NIRF 2024.\n\n" +
                        "IIT Jodhpur stands out with its Industry 4.0 curriculum, interdisciplinary projects, and mandatory courses in Machine Learning and Data Structures for all branches. Our students are actively engaged in various tech and non-tech clubs ensuring they are well-rounded and industry-ready.\n\n" +
                        "For more details, please feel free to reach out to me directly:\n" +
                        "Phone: %s\n\n" +
                        "Looking forward to your response.\n\n" +
                        "Warm Regards,\n" +
                        "%s\n" +
                        "Career Development Cell, IIT Jodhpur\n",
                salutation, name, designation, company, phone, name
        );
    }
}
