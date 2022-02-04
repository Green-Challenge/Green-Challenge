package com.green.greenchallenge.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.green.greenchallenge.domain.MovementLog;
import com.green.greenchallenge.domain.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MovementLogDTO {
    private User userId;
    private Long movementLogId;
    private LocalDate day;
    private Double distance;
    private String transportation;

    @JsonCreator
    @Builder
    public MovementLogDTO(User userId, Long movementLogId, LocalDate day, Double distance, String transportation) {
        this.userId = userId;
        this.movementLogId = movementLogId;
        this.day = day;
        this.distance = distance;
        this.transportation = transportation;
    }

    public MovementLog toEntity() {
        return MovementLog.builder()
                .userId(userId)
                .movementLogId(movementLogId)
                .day(day)
                .distance(distance)
                .transportation(transportation)
                .build();
    }

    public MovementLogDTO toDTO() {
        return MovementLogDTO.builder()
                .day(day)
                .distance(distance)
                .transportation(transportation)
                .build();
    }

}
