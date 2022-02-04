package com.green.greenchallenge.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChallengeListResponseDTO {

    private Long challengeId;
    private String challengeName;
    private Long treeId;
    private Double percent;
    private int rewordToken;
    private int numberOfChallengers;
    private boolean isComplete;
    private boolean isParticipating;

}
