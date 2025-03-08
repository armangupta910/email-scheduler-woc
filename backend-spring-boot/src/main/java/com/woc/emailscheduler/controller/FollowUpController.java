package com.woc.emailscheduler.controller;

import com.woc.emailscheduler.dto.FollowUpRequest;
import com.woc.emailscheduler.model.Scheduler;
import com.woc.emailscheduler.repository.EmailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/email")
public class FollowUpController {

    @Autowired
    private EmailRepository emailRepository;
    public int counter=0;

    @PostMapping("/followup")
    public String scheduleFollowUpEmail(@RequestBody FollowUpRequest followUpRequest) {
        Scheduler followUpEmail = new Scheduler();
        followUpEmail.setRecipientEmail(followUpRequest.getEmail());
        followUpEmail.setSubject("Follow-up: " + followUpRequest.getCompany());

        String followUpBody = String.format(
                "Dear %s,\n\n" +
                        "This is a follow-up email regarding the invitation extended to %s " +
                        "for \"IIT Jodhpur's placement and internship season 2025-26\".\n" +
                        "Do let me know in case of any developments.\n" +
                        "We would like to empanel %s and establish a long-term association with you.\n" +
                        "In case of any query, do feel free to reach out to us.\n\n" +
                        "Looking forward to hearing from you.\n\n" +
                        "--\n" +
                        "Warm Regards,\n" +
                        "%s\n" +
                        "%s\n" +
                        "Career Development Cell | IIT Jodhpur\n" +
                        "Contact: %s",
                followUpRequest.getSalutation(),
                followUpRequest.getCompany(),
                followUpRequest.getCompany(),
                followUpRequest.getName(),
                followUpRequest.getDesignation(),
                followUpRequest.getPhone()
        );

        followUpEmail.setBody(followUpBody);
        followUpEmail.setScheduledTime(followUpRequest.getScheduledTime()); // Use the provided dateTime
        followUpEmail.setStatus("PENDING");

        emailRepository.save(followUpEmail);
        counter++;
        return "Follow-up email scheduled successfully.";
    }
}
