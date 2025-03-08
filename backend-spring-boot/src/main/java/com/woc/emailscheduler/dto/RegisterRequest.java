package com.woc.emailscheduler.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    private String username;
    private String email;
    private String phone;
    private String designation;
    private String password;
}
