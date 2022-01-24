package com.green.greenchallenge.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.green.greenchallenge.domain.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponseDto {

    private Long userId;
    private String name;
    private String email;
    private String location;
    private String password;
    private String nickName;
    private String siNm;
    private String sggNm;
    private String profileImg;
    private LocalDate createDate;

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
                .build();
    }
}
