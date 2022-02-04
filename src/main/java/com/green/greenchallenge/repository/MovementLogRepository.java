package com.green.greenchallenge.repository;

import com.green.greenchallenge.domain.MovementLog;
import com.green.greenchallenge.domain.User;
import com.green.greenchallenge.dto.MovementLogDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovementLogRepository extends JpaRepository<MovementLog, Long> {

    Optional<MovementLogDTO> findByUserId(Long userId);
}
