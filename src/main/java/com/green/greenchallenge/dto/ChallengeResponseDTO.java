package com.green.greenchallenge.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ChallengeResponseDTO {

    private String challengeName;
    private int numberOfChallengers;
    private int rewardToken;
    private String description;
    private List<String> hashTag;
    private Long treeId;
    private String challengeImg;

}
