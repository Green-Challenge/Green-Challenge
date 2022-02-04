package com.green.greenchallenge.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.green.greenchallenge.domain.Challenge;
import com.green.greenchallenge.domain.Participant;
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
public class ParticipantDTO {

    private Long participantId;
    private User userId;
    private Challenge challengeId;
    private LocalDate participateDate;
    private int leafCount;
    private Double totalDistance;

    public Participant toParticipantEntity(){
        return Participant.builder()
                .participantId(this.participantId)
                .userId(this.userId)
                .challengeId(this.challengeId)
                .participateDate(this.participateDate)
                .leafCount(this.leafCount)
                .totalDistance(this.totalDistance)
                .build();
    }

}
