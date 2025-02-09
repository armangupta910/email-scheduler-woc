package com.woc.emailscheduler.dto;

import java.time.LocalDateTime;

public class FollowUpRequest {
    private String company;
    private LocalDateTime dateTime;
    private String email;
    private String salutation;
    private String name;
    private String designation;
    private String phone;

    // Default constructor
    public FollowUpRequest() {
    }

    // Parameterized constructor
    public FollowUpRequest(String company, LocalDateTime dateTime, String email, String salutation, String name, String designation, String phone) {
        this.company = company;
        this.dateTime = dateTime;
        this.email = email;
        this.salutation = salutation;
        this.name = name;
        this.designation = designation;
        this.phone = phone;
    }

    // Getters and Setters
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSalutation() {
        return salutation;
    }

    public void setSalutation(String salutation) {
        this.salutation = salutation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    // toString() method for debugging/logging purposes
    @Override
    public String toString() {
        return "FollowUpRequest{" +
                "company='" + company + '\'' +
                ", dateTime=" + dateTime +
                ", email='" + email + '\'' +
                ", salutation='" + salutation + '\'' +
                ", name='" + name + '\'' +
                ", designation='" + designation + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
