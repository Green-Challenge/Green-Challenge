package com.green.greenchallenge.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetChartResponseDTO {
    private List<MovementLogDTO> lastMonth;
    private List<MovementLogDTO> currentMonth;
}
