package com.green.greenchallenge.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    private String nickName;

    private String siNm; // 거주지_시

    private String sggNm; // 거주지_구

    private String profileImg; // 이미지 링크

    @Column(nullable = false)
    private LocalDate createDate;

    @Transient
    private String errorMsg;

    @Transient
    private String location;
}
