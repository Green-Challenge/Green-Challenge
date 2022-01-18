package com.green.greenchallenge.repository;

import com.green.greenchallenge.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantRepository extends JpaRepository<User, Long> {
}
