package com.woc.emailscheduler.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class CompanyController {

    @GetMapping("/company-info")
    @PreAuthorize("hasRole('ADMIN')")  // Only Admins can access this
    public String getCompanyInfo() {
        return "Company Name, Email ID, Last Scheduled Time";
    }
}
