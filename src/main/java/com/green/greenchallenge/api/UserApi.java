package com.green.greenchallenge.api;

import com.green.greenchallenge.domain.User;
import com.green.greenchallenge.dto.UserResponseDto;
import com.green.greenchallenge.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserApi {
    private final UserService userService;

    @PostMapping("/auth")
    public UserResponseDto register(@RequestBody User user) {
        user.setCreateDate(LocalDate.now());
        UserResponseDto res = new UserResponseDto();
        res.setUserId(userService.register(user).getUserId());
        res.setName(user.getName());

        return res;
    }

    @GetMapping("/auth{email}")
    public Boolean duplicate(@RequestParam String email) {
        return userService.duplicate(email);
    }

    @PostMapping("/signin")
    public User signIn(@RequestBody User user) {
        return userService.signIn(user);
    }

    @GetMapping("/profile{userId}")
    public User getProfile(@RequestParam Long userId) {
        return userService.getProfile(userId);
    }

    @PostMapping("/profile")
    public User setProfile(@RequestBody User user) {
        return userService.updateProfile(user);
    }

    @PutMapping("/profile")
    public User updateProfile(@RequestBody User user) {
        return userService.updateProfile(user);
    }
}