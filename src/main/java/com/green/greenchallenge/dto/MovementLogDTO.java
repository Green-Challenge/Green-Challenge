package com.green.greenchallenge.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.green.greenchallenge.domain.MovementLog;
import com.green.greenchallenge.domain.User;
import lombok.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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
    private String x;
    private Double y;

    public MovementLog toEntity() {
        return MovementLog.builder()
                .movementLogId(movementLogId)
                .day(day)
                .distance(distance)
                .transportation(transportation)
                .build();
    }

    public static MovementLogDTO toDTO(MovementLog movementLog) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM.dd");
        return MovementLogDTO.builder()
                .x(movementLog.getDay().format(formatter))
                .y(Math.round(movementLog.getDistance() * Transportation.valueOf(movementLog.getTransportation()).getCost() * 10) / 10.0)
                .build();
    }

}
