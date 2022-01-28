package com.green.greenchallenge.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
public class Challenge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
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
