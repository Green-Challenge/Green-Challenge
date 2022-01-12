package com.green.greenchallenge.api;

import com.green.greenchallenge.domain.User;
import com.green.greenchallenge.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class Api {
    private final UserService userService;

    @GetMapping("/users")
    public List<User> retrieveUsers() {
        return userService.retrieveUsers();
    }

    @GetMapping("/users/{userId}")
    public User retrieveUser(@PathVariable int userId) {
        return userService.retrieveUser(userId);
    }

    @PostMapping("/users")
    public List<User> insertUser(User user) {
        System.out.println(user);
        userService.insertUser(user);

        return userService.retrieveUsers();
    }

    @DeleteMapping("/users/{id}")
    public List<User> deleteUser(@PathVariable int userId) {
        userService.deleteUser(userId);

        return userService.retrieveUsers();
    }

//    @PutMapping("/users")
//    public User updateUser(User user) {
//        return userService.updateUser(user);
//    }
}
