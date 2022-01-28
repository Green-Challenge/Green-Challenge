package com.green.greenchallenge.domain;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@DynamicInsert
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "participant_id", nullable = false)
    private Long participantId;

    @ManyToOne
    @JoinColumn(name= "User")
    private User userId;

    @ManyToOne
    @JoinColumn(name = "Challenge")
    private Challenge challengeId;

    @Column(nullable = false)
    private LocalDate participateDate;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int leafCount;

    @Column(nullable = false)
    @ColumnDefault("0")
    private double totalDistance;
}
