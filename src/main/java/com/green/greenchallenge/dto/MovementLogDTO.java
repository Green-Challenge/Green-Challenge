package com.green.greenchallenge.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.green.greenchallenge.domain.MovementLog;
import com.green.greenchallenge.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MovementLogDTO {
    private User userId;
    private Long movementLogId;
    private LocalDate day = LocalDate.now();
    private String logdate;
    private Double distance;
    private String transportation;

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
                .logdate(day.toString())
                .distance(distance)
                .transportation(transportation)
                .build();
    }

}
