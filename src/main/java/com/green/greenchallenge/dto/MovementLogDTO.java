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
    private Long userId;
    private Long movementLogId;
    private LocalDate day = LocalDate.now();
    private String logdate;
    private Double distance;
    private String transportation;

    public MovementLog toEntity() {
        return MovementLog.builder()
                .movementLogId(movementLogId)
                .day(day)
                .distance(distance)
                .transportation(transportation)
                .build();
    }

    public static MovementLogDTO toDTO(MovementLog movementLog) {
        return MovementLogDTO.builder()
                .logdate(movementLog.getDay().toString())
                .distance(movementLog.getDistance())
                .transportation(movementLog.getTransportation())
                .build();
    }

}
