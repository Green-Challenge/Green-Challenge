package com.green.greenchallenge.repository;

import com.green.greenchallenge.domain.MovementLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovementLogRepository extends JpaRepository<MovementLog, Long> {

}
