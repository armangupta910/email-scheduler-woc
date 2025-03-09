package com.woc.emailscheduler.service;

import com.woc.emailscheduler.entity.UserEntity;
import com.woc.emailscheduler.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Save a new user after checking for duplicate username or email.
     * Returns a message indicating the success or failure of registration.
     */
    public String save(UserEntity user) {
        if (existsByUsername(user.getUsername())) {
            return "Username already registered.";
        }

        if (existsByEmail(user.getEmail())) {
            return "Email already registered.";
        }

        userRepository.save(user);
        return "User registered successfully.";
    }

    /**
     * Check if a username already exists in the database.
     */
    public boolean existsByUsername(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    /**
     * Check if an email already exists in the database.
     */
    public boolean existsByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    /**
     * Load user details by username for authentication.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }
}
