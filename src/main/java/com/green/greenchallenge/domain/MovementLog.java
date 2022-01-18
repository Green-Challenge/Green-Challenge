package com.green.greenchallenge.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.time.LocalDate;

@Data
@Entity
public class MovementLog {
    @Id
    @OneToOne
    private User userId;

    private String transportation;

    private LocalDate date;
}
