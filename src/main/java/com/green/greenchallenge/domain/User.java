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

    @Column(name = "email", nullable = false)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;

    @Transient
    private String passwordConfirm;
    @Transient
    private String name;
    @Transient
    private String nickName;
    @Transient
    private String address;
    @Transient
    private String siNm; // 거주지_시
    @Transient
    private String sggNm; // 거주지_구
    @Transient
    private String profileImg; // 이미지 링크
    @Transient
    private LocalDate createDate;
}
