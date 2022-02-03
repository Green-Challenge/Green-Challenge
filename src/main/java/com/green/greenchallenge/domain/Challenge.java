package com.green.greenchallenge.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Challenge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long challengeId;

    @Column(nullable = false)
    private String challengeName;
    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private String challengeImg;
    @Column(nullable = false)
    private String hashTag;
    @Column(nullable = false)
    private String transportation;
    @Column(nullable = false)
    private int goalLeaves;
    @Column(nullable = false)
    private LocalDate startDate;
    @Column(nullable = false)
    private LocalDate finishDate;
    @Column(nullable = false)
    private int rewardToken;
    @Column(nullable = false)
    private double goalDistance;
}
