package com.green.greenchallenge.api;

import com.green.greenchallenge.domain.User;
import com.green.greenchallenge.dto.UserResponseDto;
import com.green.greenchallenge.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserApi {
    private final UserService userService;

    @PostMapping("/auth")
    public ResponseEntity<UserResponseDto> register(@RequestBody UserResponseDto userResponseDto) {
        return new ResponseEntity(userService.register(userResponseDto), HttpStatus.CREATED);
    }

    @GetMapping("/auth{email}")
    public Boolean duplicate(@RequestParam String email) {
        return userService.duplicate(email);
    }

    @PostMapping("/signin")
    public UserResponseDto signIn(@RequestBody UserResponseDto userResponseDto) {
        return userService.signIn(userResponseDto);
    }

    @GetMapping("/profile{userId}")
    public UserResponseDto getProfile(@RequestParam Long userId) {
        return userService.getProfile(userId);
    }

    @PostMapping("/profile")
    public UserResponseDto setProfile(@RequestBody UserResponseDto userResponseDto) {
        return userService.updateProfile(userResponseDto);
    }

    @PutMapping("/profile")
    public UserResponseDto updateProfile(@RequestBody UserResponseDto userResponseDto) {
        return userService.updateProfile(userResponseDto);
    }
}