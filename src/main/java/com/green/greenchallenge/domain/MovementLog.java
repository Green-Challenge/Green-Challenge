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
    @Column(nullable = false)
    private int distance;
    @Column(nullable = false)
    private String transportation;
    @Column(nullable = false)
    private LocalDate day;

}
