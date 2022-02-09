package com.green.greenchallenge.api;

import com.green.greenchallenge.config.JwtProvider;
import com.green.greenchallenge.dto.AuthResponseDTO;
import com.green.greenchallenge.dto.UserDTO;
import com.green.greenchallenge.service.AuthService;
import com.green.greenchallenge.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/api", produces = "application/json; charset=utf8")
@RequiredArgsConstructor
public class UserApi {
    private final UserService userService;
    private final AuthService authService;
    private final JwtProvider jwtProvider;

    @PostMapping("/auth")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        return new ResponseEntity(userService.createUser(userDTO), HttpStatus.CREATED);
    }

    @GetMapping(value = "/auth/{email}")
    public ResponseEntity getProfile(@PathVariable String email) {
        if(userService.idDuplicated(email))
            return new ResponseEntity("사용가능한 이메일입니다.",HttpStatus.OK);
        else
            return new ResponseEntity("이미 사용중인 이메일입니다.", HttpStatus.CONFLICT);
    }

    @PostMapping("/auth/signin")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody UserDTO userDTO, HttpServletResponse httpServletResponse) {
        String token = authService.login(userDTO);
        httpServletResponse.setHeader("X-AUTH-TOKEN", token);
        return new ResponseEntity(AuthResponseDTO.builder().userId(Long.parseLong(jwtProvider.getUserPk(token))).build(), HttpStatus.OK);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataType = "String", paramType = "header"
            )
    })
    @GetMapping("/auth/me")
    public ResponseEntity checkToken(@RequestHeader(value = "X-AUTH-TOKEN") String token) {
        return new ResponseEntity(AuthResponseDTO.builder().userId(Long.parseLong(jwtProvider.getUserPk(token))).build(), HttpStatus.OK);
    }
}
