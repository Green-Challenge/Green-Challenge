package com.green.greenchallenge.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponseDTO {
    private Long userId;
    private String email;
    private String password;
    private String name;
    private String nickName;
    private String siNm; // 거주지_시
    private String sggNm; // 거주지_구
    private String profileImg; // 이미지 링크
    private LocalDate createDate;
}
