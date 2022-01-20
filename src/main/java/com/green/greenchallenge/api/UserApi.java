package com.green.greenchallenge.api;

import com.green.greenchallenge.domain.User;
import com.green.greenchallenge.dto.UserResponseDTO;
import com.green.greenchallenge.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api", produces = "application/json; charset=utf8")
@RequiredArgsConstructor
public class UserApi {
    private final UserService userService;

    @PostMapping("/auth")
    public ResponseEntity createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        return new ResponseEntity(UserResponseDTO.builder()
                .userId(createdUser.getUserId())
                .name(createdUser.getName())
                .build()
                ,HttpStatus.CREATED);
    }

    @GetMapping(value = "/auth/{email}")
    public ResponseEntity getProfile(@PathVariable String email) {
        if(userService.idDuplicated(email))
            return new ResponseEntity("사용가능한 이메일입니다.",HttpStatus.OK);
        else
            return new ResponseEntity("이미 사용중인 이메일입니다.", HttpStatus.CONFLICT);
    }

    @GetMapping("/profile")
    public UserResponseDTO getProfile() {
        return userService.getProfile();
    }

    @PutMapping("/profile")
    public UserResponseDTO updateProfile(@RequestBody User user) {
        return userService.updateProfile(user);
    }
}
