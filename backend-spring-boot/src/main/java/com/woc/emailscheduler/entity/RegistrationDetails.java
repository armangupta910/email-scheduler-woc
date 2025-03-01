package com.woc.emailscheduler.service;
//reg
import com.woc.emailscheduler.entity.RegistrationDetails;
import com.woc.emailscheduler.repository.AppUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserDetailsService {
    @Autowired
    private AppUserRepository repo;

    @Override
    public UserDetails loadUserByUsername(String EmailId) throws UsernameNotFoundException{
        RegistrationDetails rd =repo.findByEmail(EmailId);
        if(rd!=null){
            return User.withUsername(rd.getEmailId())
                    .password(rd.getPassword())
                    .build();
        }

        return null;
    }

}
