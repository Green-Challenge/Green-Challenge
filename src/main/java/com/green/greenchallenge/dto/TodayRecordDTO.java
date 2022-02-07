package com.green.greenchallenge.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TodayRecordDTO {

    private Long challengeId;
    private Long userId;
    private double distance;
    private double reducedCarbon;
}
