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
@DynamicInsert
@NoArgsConstructor
@AllArgsConstructor
public class TreeInstance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long treeInstanceId;

    @ManyToOne
    @JoinColumn(name = "challengeId")
    private Challenge challengeId;

    @Column
    @ColumnDefault("0")
    private int numberOfLeaf;

    private LocalDate finishedDate;
}
