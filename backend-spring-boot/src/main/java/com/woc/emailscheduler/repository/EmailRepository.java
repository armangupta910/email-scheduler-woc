package com.woc.emailscheduler.repository;

import com.woc.emailscheduler.model.scheduler;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<scheduler, Long> {
}