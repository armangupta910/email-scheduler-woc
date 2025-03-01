package com.woc.emailscheduler.repository;
//reg
import com.woc.emailscheduler.entity.RegistrationDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<RegistrationDetails, Integer> {
    //new
    public RegistrationDetails findByEmail(String EmailId);
}
