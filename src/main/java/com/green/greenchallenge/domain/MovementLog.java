package com.green.greenchallenge.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovementLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long movementLogId;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @Column(nullable = false)
    private Double distance;

    @Column(nullable = false)
    private String transportation;

    @Column(nullable = false)
    @CreatedDate
    private LocalDate day;

}
