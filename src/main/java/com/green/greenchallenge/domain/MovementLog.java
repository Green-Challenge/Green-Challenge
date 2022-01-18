package com.green.greenchallenge.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
public class MovementLog {
    @Id
    private Long movementId;

    @ManyToOne
    @JoinColumn(name = "User")
    private User userId;

    private int distance;

    private String transportation;

    private LocalDate date;
}
