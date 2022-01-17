package com.green.greenchallenge.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "uid", nullable = false)
    private Long uid;

    private String email;
    private String password;
    private String name;
    private String nickname;
    private String address;
}
