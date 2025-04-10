package com.woc.emailscheduler.repository;

import com.woc.emailscheduler.model.Scheduler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EmailRepository extends JpaRepository<Scheduler, Long> {

    @Query("SELECT e FROM Scheduler e WHERE e.status = 'PENDING' AND e.scheduledTime <= :cutoffTime ORDER BY e.scheduledTime ASC")
    List<Scheduler> findPendingEmails(@Param("cutoffTime") LocalDateTime cutoffTime);

    @Query("SELECT e FROM Scheduler e WHERE e.status = 'PENDING' AND e.scheduledTime BETWEEN :currentTime AND :cutoffTime ORDER BY e.scheduledTime ASC")
    List<Scheduler> findFutureEmails(@Param("currentTime") LocalDateTime currentTime, @Param("cutoffTime") LocalDateTime cutoffTime);

    @Query("SELECT COUNT(e) FROM Scheduler e WHERE e.status = 'sent'")
    long countSentEmails();

    @Query("SELECT COUNT(e) FROM Scheduler e WHERE e.status = 'failed'")
    long countFailedEmails();

    @Query("SELECT COUNT(e) FROM Scheduler e WHERE e.status = 'pending'")
    long countPendingEmails();

    @Query("SELECT COUNT(e) FROM Scheduler e WHERE LOWER(e.subject) LIKE 'follow-up:%' AND e.status = 'sent'")
    long countFollowupEmailsSent();

    @Query("SELECT COUNT(e) FROM Scheduler e WHERE LOWER(e.subject) LIKE 'follow-up:%' AND e.status = 'failed'")
    long countFailedFollowupEmails();

    @Query("SELECT COUNT(e) FROM Scheduler e WHERE LOWER(e.subject) LIKE 'follow-up:%' AND e.status = 'pending'")
    long countFollowupScheduled();

    @Query("SELECT e.body, e.recipientEmail, MAX(e.scheduledTime) " +
            "FROM Scheduler e " +
            "GROUP BY e.body, e.recipientEmail")
    List<Object[]> findCompanyInfo();

    @Query("SELECT e.recipientEmail, e.body, MAX(e.scheduledTime) " +
            "FROM Scheduler e " +
            "WHERE LOWER(e.subject) LIKE 'follow-up:%' " +
            "GROUP BY e.recipientEmail, e.body")
    List<Object[]> findFollowupCompanyInfo();
}
