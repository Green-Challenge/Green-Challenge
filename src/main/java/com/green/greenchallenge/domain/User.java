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

    @Column(name = "email", unique = true)
    private String email;
    private String password;
    private String name;
    private String nickName;
    private String siNm; // 거주지_시
    private String sggNm; // 거주지_구
    private String profileImg; // 이미지 링크
    private LocalDate createDate;
}
