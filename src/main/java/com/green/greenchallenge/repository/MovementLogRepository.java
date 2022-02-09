package com.green.greenchallenge.repository;

import com.green.greenchallenge.domain.MovementLog;
import com.green.greenchallenge.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MovementLogRepository extends JpaRepository<MovementLog, Long> {

    @Query(value = "select * from movement_log where user_id = :userId", nativeQuery = true)
    List<Optional<MovementLog>> findByUserId(Long userId);

    List<Optional<MovementLog>> findByUserAndDayGreaterThanEqualAndDayLessThanEqual(User userId, LocalDate start, LocalDate end);

    List<Optional<MovementLog>> findByDayGreaterThanEqualAndDayLessThanEqualAndTransportationAndUser(LocalDate start, LocalDate end, String transportation, User user);
}
