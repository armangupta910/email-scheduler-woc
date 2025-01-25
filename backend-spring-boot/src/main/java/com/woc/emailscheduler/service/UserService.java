package com.woc.emailscheduler.service;

import com.woc.emailscheduler.entity.RegistrationDetails;
import org.springframework.stereotype.Repository;

@Repository
public interface UserService {
    public boolean registerUser(RegistrationDetails rd);
}
