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

    @GetMapping("/users")
    public List<User> retrieveUsers() {
        return userService.retrieveUsers();
    }

    @GetMapping("/users/{uid}")
    public User retrieveUser(@PathVariable long uid) {
        return userService.retrieveUser(uid);
    }

    @PostMapping("/users")
    public User insertUser(@RequestBody User user) {
        return userService.insertUser(user);
    }

    @DeleteMapping("/users/{uid}")
    public List<User> deleteUser(@PathVariable long uid) {
        return userService.deleteUser(uid);
    }

    @PutMapping("/users/{uid}")
    public User updateUser(@RequestBody User user, @PathVariable long uid) {
        return userService.updateUser(user, uid);
    }



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

    /*
    @PostMapping("/profile")
    public User getProfile(@RequestBody User user) {

    }

    @PutMapping("/profile")
    public User setProfile(@RequestBody User user) {

    }

     */
}