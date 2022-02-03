package com.green.greenchallenge.repository;

import com.green.greenchallenge.domain.Challenge;
import com.green.greenchallenge.domain.Participant;
import com.green.greenchallenge.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ParticipantRepository extends JpaRepository<Participant, Long> {

    List<Participant> findByUserId(Long userId);

    int countByChallengeId(Challenge challenge);
}
