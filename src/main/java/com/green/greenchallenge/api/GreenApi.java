package com.green.greenchallenge.api;

import com.green.greenchallenge.domain.User;
import com.green.greenchallenge.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class GreenApi {
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
}
