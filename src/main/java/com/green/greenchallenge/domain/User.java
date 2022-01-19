package com.green.greenchallenge.domain;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String nickName;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String siNm; // 거주지_시

    @Column(nullable = false)
    private String sggNm; // 거주지_구

    @Column(nullable = false)
    private String profileImg; // 이미지 링크

    @Column(nullable = false)
    private LocalDate createDate;
}
