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
    public Boolean duplicate(@PathVariable String email) {
        return userService.duplicate(email);
    }

    @PostMapping("/signin")
    public User signIn(@RequestBody User user) {
        return userService.signIn(user);
    }

    @PostMapping("/profile")
    public User getProfile(@RequestBody User user) {
        return userService.getProfile(user);
    }

    @PutMapping("/profile")
    public User setProfile(@RequestBody User user) {
        return userService.editProfile(user);
    }
}