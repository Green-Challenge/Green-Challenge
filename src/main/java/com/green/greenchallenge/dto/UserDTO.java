package com.green.greenchallenge.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.green.greenchallenge.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
    private Long userId;
    private String email;
    private String password;
    private String name;
    private String nickName;
    private String siNm; // 거주지_시
    private String sggNm; // 거주지_구
    private String profileImg; // 이미지 링크
    private List<String> roles = Collections.singletonList("ROLE_USER");
    private LocalDate createDate = LocalDate.now();

    public void encodePassword(PasswordEncoder passwordEncoder) {
        this.password = passwordEncoder.encode(password);
    }

    public User toEntity() {
        return User.builder()
                .userId(this.userId)
                .email(this.email)
                .password(this.password)
                .name(this.name)
                .nickName(this.nickName)
                .siNm(this.siNm)
                .sggNm(this.sggNm)
                .createDate(this.createDate)
                .roles(this.roles)
                .build();
    }
}
