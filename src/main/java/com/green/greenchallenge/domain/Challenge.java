package com.green.greenchallenge.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
public class Challenge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "challenge_id", nullable = false)
    private Long challengeId;

    private String challengeName;
    private String description;
    private String challengeImg;
    private String hashTag;
    private String transportation;
    private int goal;
    private LocalDate startDate;
    private LocalDate finishDate;
}
