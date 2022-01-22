package com.green.greenchallenge.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
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
    private String createDate;
}
