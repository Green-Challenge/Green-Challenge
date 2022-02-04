package com.green.greenchallenge.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDate;

@Builder
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long participantId;

    @ManyToOne
    @JoinColumn(name= "UserId")
    private User userId;

    @ManyToOne
    @JoinColumn(name = "challengeId")
    private Challenge challengeId;

    @Column(nullable = false)
    private LocalDate participateDate;

    @Column(nullable = false)
    @ColumnDefault("0")
    private int leafCount;

    @Column(nullable = false)
    @ColumnDefault("0.0")
    private double totalDistance;
}
