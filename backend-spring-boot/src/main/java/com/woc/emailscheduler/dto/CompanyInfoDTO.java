package com.woc.emailscheduler.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CompanyInfoDTO {
    private final String name;
    private final String emailId;
    private final LocalDateTime lastScheduledTime;

    public CompanyInfoDTO(String name, String emailId, LocalDateTime lastScheduledTime) {
        this.name = name;
        this.emailId = emailId;
        this.lastScheduledTime = lastScheduledTime;
    }

}
