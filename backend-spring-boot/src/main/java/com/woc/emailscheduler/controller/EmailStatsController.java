package com.woc.emailscheduler.controller;

import com.woc.emailscheduler.repository.EmailRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/stats")
public class EmailStatsController {

    private final EmailRepository emailRepository;

    public EmailStatsController(EmailRepository emailRepository) {
        this.emailRepository = emailRepository;
    }

    @GetMapping("/counts")
    public Map<String, Long> getEmailCounts() {
        Map<String, Long> counts = new HashMap<>();
        counts.put("totalEmailsSent", emailRepository.countSentEmails());
        counts.put("totalEmailsScheduled", emailRepository.countPendingEmails());
        counts.put("failedEmails", emailRepository.countFailedEmails());
       // counts.put("totalFollowupEmailsSent", emailRepository.countFollowupEmailsSent());
       // counts.put("totalFollowupScheduled", emailRepository.countFollowupEmailsScheduled());
       // counts.put("failedFollowupEmails", emailRepository.countFailedFollowupEmails());

        return counts;
    }
}
