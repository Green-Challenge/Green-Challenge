package com.green.greenchallenge.api;

import com.green.greenchallenge.domain.User;
import com.green.greenchallenge.dto.UserResponseDTO;
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

    @PostMapping("/api/auth")
    public ResponseEntity registerUser(@RequestBody User user){
        return new ResponseEntity(userService.registerUser(user), HttpStatus.OK);
    }

    @GetMapping("/api/auth{email}")
    public ResponseEntity<Object> emailAuth(@RequestParam String email){

        Map<String, Object> result = new HashMap<>();
        boolean isValidEmail = userService.validEmail(email);
        result.put("validation", isValidEmail);

        if(isValidEmail){
            return new ResponseEntity<>(result, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(result, HttpStatus.CONFLICT);
        }

    }

    @GetMapping("/profile/{userId}")
    public ResponseEntity getProfile(@PathVariable Long userId){
        return new ResponseEntity(userService.getProfile(userId), HttpStatus.OK);
    }

    @PostMapping("/profile")
    public ResponseEntity updateProfile(@RequestBody User user){
        return new ResponseEntity(userService.updateProfile(user), HttpStatus.OK);
    }

    @PutMapping("/profile")
    public ResponseEntity inputProfile(@RequestBody User user){
        return new ResponseEntity(userService.updateProfile(user), HttpStatus.OK);
    }

}
