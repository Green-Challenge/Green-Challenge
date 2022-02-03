package com.green.greenchallenge.api;

import com.green.greenchallenge.domain.User;
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

    @GetMapping("/profile/{userId}")
    public ResponseEntity getProfile(@PathVariable long userId) {
        return new ResponseEntity(userService.getProfile(userId), HttpStatus.OK);
    }

    @PutMapping("/profile")
    public ResponseEntity updateProfile(@RequestBody UserDTO userDTO) {
        return new ResponseEntity(userService.updateProfile(userDTO), HttpStatus.OK);
    }

    @PostMapping("/profile")
    public ResponseEntity inputProfile(@RequestBody UserDTO userDTO) {
        return new ResponseEntity(userService.updateProfile(userDTO), HttpStatus.OK);
    }

    @PostMapping("/auth/signin")
    public ResponseEntity login(@RequestBody UserDTO userDTO, HttpServletResponse httpServletResponse) {
        String token = authService.login(userDTO);
        httpServletResponse.setHeader("X-AUTH-TOKEN", token);
        return new ResponseEntity("login succes", HttpStatus.OK);
    }

    @ApiImplicitParams({
            @ApiImplicitParam(
                    name = "X-AUTH-TOKEN",
                    value = "로그인 성공 후 AccessToken",
                    required = true, dataType = "String", paramType = "header"
            )
    })
    @GetMapping("/auth/me")
    public ResponseEntity checkToken() {
        return new ResponseEntity("success", HttpStatus.OK);
    }
}
