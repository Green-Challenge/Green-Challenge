package com.green.greenchallenge.api;

import com.green.greenchallenge.domain.User;
import com.green.greenchallenge.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class UserApi {
    private final UserService userService;

    @PostMapping("/register")
    public User register(@RequestBody User user) {
        user.setCreateDate(LocalDate.now());
        if(userService.register(user) == null) {
            user.setSuccess(false);
            user.setErrorMsg("error");
        } else {
            user.setSuccess(true);
            user.setErrorMsg(null);
        }

        return user;
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