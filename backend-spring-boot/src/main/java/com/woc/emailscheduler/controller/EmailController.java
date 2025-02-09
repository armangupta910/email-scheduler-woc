package com.woc.emailscheduler.controller;

import com.woc.emailscheduler.model.Scheduler;
import com.woc.emailscheduler.service.EmailSaveService;
import com.woc.emailscheduler.EmailRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/email")
public class EmailController {

    @Autowired
    private EmailSaveService emailSaveService;

    // Endpoint to create and schedule an email
    @PostMapping("/schedule")
    public ResponseEntity<String> scheduleEmail(@RequestBody EmailRequest emailRequest) {
        try {
            // Creating the email body dynamically (similar to single email sending)
            String emailBody = composeEmailBody(
                    emailRequest.getCompany(),
                    emailRequest.getSalutation(),
                    emailRequest.getName(),
                    emailRequest.getDesignation(),
                    emailRequest.getPhone()
            );

            // Create and set up the email to be saved and scheduled
            Scheduler email = new Scheduler();
            email.setRecipientEmail(emailRequest.getEmail());
            email.setSubject("Invitation to Participate in Campus Placement");
            email.setBody(emailBody);
            email.setScheduledTime(emailRequest.getScheduledTime());  // The user will send the scheduled time
            email.setStatus("pending");
            email.setCreatedAt(LocalDateTime.now());

            // Save and schedule the email
            emailSaveService.saveEmail(email);

            return ResponseEntity.ok("Email scheduled successfully!");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error scheduling email: " + e.getMessage());
        }
    }

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
