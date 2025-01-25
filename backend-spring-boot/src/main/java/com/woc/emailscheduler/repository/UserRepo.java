package com.woc.emailscheduler.repository;

import com.woc.emailscheduler.entity.RegistrationDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<RegistrationDetails, Integer> {
}
