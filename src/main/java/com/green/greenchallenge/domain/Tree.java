package com.green.greenchallenge.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Tree {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long treeId;

    @ManyToOne
    @JoinColumn(name = "challenge")
    private Challenge challengeId;

    @Column(nullable = false)
    private String treeName;

    @Column(nullable = false)
    private String treeImg;

    @Column(nullable = false)
    private int treeGrowth;
}
