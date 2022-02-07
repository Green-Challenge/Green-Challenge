package com.green.greenchallenge.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChallengeListResponseDTO {

    private int challengeId;
    private String challengeName;
    private int treeId;
    private Double percent;
    private int rewordToken;
    private int numberOfChallengers;
    private boolean isComplete;
    private boolean isParticipating;

}
