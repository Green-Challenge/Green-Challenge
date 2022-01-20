package com.green.greenchallenge.api;

import com.green.greenchallenge.domain.User;
import com.green.greenchallenge.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
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

    // HashMap으로 json응답
    @PostMapping("/api/auth")
    public ResponseEntity<Object> registerUser(@RequestBody User user, Map<String, Object> map){
        ResponseEntity<Object> responseEntity = null;
        Map<String, Object> result = new HashMap<>();

        userService.registerUser(user);
        result.put("userId",user.getUserId());
        result.put("name", user.getName());
        responseEntity = new ResponseEntity<Object>(result, HttpStatus.OK);

        return responseEntity;
    }

    @GetMapping("/api/auth{email}")
    public ResponseEntity<Object> emailAuth(@RequestParam String email){
        ResponseEntity<Object> responseEntity = null;
        Map<String, Object> result = new HashMap<>();

        boolean isValidEmail = userService.validEmail(email);
        result.put("validation", isValidEmail);
        responseEntity = new ResponseEntity<>(result, HttpStatus.OK);

        return responseEntity;
    }

    // responseDto를 이용한 응답
//    @PostMapping("/api/auth/register")
//    public ResponseDto registerUser(@RequestBody User user){
//
//        ResponseDto responseDto = new ResponseDto(true, null);
//        userService.registerUser(user);
//        return responseDto;
//
//    }

}
