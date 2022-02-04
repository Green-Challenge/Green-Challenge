package com.green.greenchallenge.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.green.greenchallenge.domain.Challenge;
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
public class ChallengeDTO {
    private Long challengeId;
    private String challengeName;
    private String description;
    private String challengeImg;
    private String hashTag;
    private String transportation;
    private int goalLeaves;
    private LocalDate startDate;
    private LocalDate finishDate;
    private int rewardToken;
    private Double goalDistance;

    public Challenge toEntity(){
        return Challenge.builder()
                .challengeId(this.challengeId)
                .challengeName(this.challengeName)
                .description(this.description)
                .challengeImg(this.challengeImg)
                .hashTag(this.hashTag)
                .transportation(this.transportation)
                .goalLeaves(this.goalLeaves)
                .startDate(this.startDate)
                .finishDate(this.finishDate)
                .rewardToken(this.rewardToken)
                .goalDistance(this.goalDistance)
                .build();
    }
}