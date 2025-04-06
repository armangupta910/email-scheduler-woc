package com.woc.emailscheduler.controller;

import com.woc.emailscheduler.dto.CompanyInfoDTO;
import com.woc.emailscheduler.repository.EmailRepository;
import com.woc.emailscheduler.service.EmailSaveService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/stats")
public class EmailStatsController {

    private final EmailRepository emailRepository;
    private final EmailSaveService emailService;
    private final FollowUpController followUpController;


    public EmailStatsController(EmailRepository emailRepository, EmailSaveService emailService) {
        this.emailRepository = emailRepository;
        this.emailService = emailService;
        this.followUpController = new FollowUpController();
    }

    @GetMapping("/counts")
    public Map<String, Long> getEmailCounts() {
        Map<String, Long> counts = new HashMap<>();
        counts.put("totalEmailsSent", emailRepository.countSentEmails());
        counts.put("totalEmailsScheduled", emailRepository.countPendingEmails());
        counts.put("failedEmails", emailRepository.countFailedEmails());
        counts.put("totalFollowupEmailsSent", emailRepository.countFollowupEmailsSent());
        counts.put("totalFollowupScheduled", emailRepository.countFollowupScheduled());
        counts.put("failedFollowupEmails", emailRepository.countFailedFollowupEmails());

        return counts;
    }
    @GetMapping("/companies")
    public List<CompanyInfoDTO> getCompanyInfo() {
        List<CompanyInfoDTO> companyInfoList = emailService.getCompanyInfo();
        return companyInfoList;
    }
}
