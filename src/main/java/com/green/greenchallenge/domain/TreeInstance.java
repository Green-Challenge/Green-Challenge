package com.green.greenchallenge.domain;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@DynamicInsert
public class TreeInstance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long treeInstanceId;

    @ManyToOne
    @JoinColumn(name = "Challenge")
    private Challenge challengeId;

    @Column
    @ColumnDefault("0")
    private int numberOfLeaf;

    private LocalDate finishedDate;
}
