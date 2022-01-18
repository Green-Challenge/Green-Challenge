package com.green.greenchallenge.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participant_id", nullable = false)
    private Long participantId;

    private Long userId;
    private Long challengeId;
    private LocalDate participateDate;
}
