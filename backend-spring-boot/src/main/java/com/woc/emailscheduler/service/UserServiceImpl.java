package com.woc.emailscheduler.service;

import com.woc.emailscheduler.entity.RegistrationDetails;
import com.woc.emailscheduler.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepo repo;
    @Override
    public boolean registerUser(RegistrationDetails rd){
        repo.save(rd);

        return true;
    }

