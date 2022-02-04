package com.green.greenchallenge.repository;

import com.green.greenchallenge.domain.DonationLog;
import com.green.greenchallenge.domain.Participant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.ArrayList;

public interface DonationLogRepository extends JpaRepository<DonationLog, Long> {
    ArrayList<DonationLog> findByParticipantId(Participant participant);
}
