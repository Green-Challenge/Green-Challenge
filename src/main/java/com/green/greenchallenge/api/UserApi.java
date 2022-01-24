package com.green.greenchallenge.api;

import com.green.greenchallenge.domain.User;
import com.green.greenchallenge.dto.UserDTO;
import com.green.greenchallenge.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api")
public class UserApi {

    private final UserService userService;

    @PostMapping("/auth")
    public ResponseEntity<UserDTO> insertUser(@RequestBody UserDTO userDTO){
        return new ResponseEntity(userService.insertUser(userDTO), HttpStatus.OK);
    }

    @GetMapping("/auth{email}")
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
    public ResponseEntity<UserDTO> getProfile(@PathVariable Long userId){
        return new ResponseEntity(userService.getProfile(userId), HttpStatus.OK);
    }

    @PostMapping("/profile")
    public ResponseEntity<UserDTO> updateProfile(@RequestBody UserDTO userDTO){
        return new ResponseEntity(userService.updateProfile(userDTO), HttpStatus.OK);
    }

    @PutMapping("/profile")
    public ResponseEntity<UserDTO> inputProfile(@RequestBody UserDTO userDTO){
        return new ResponseEntity(userService.updateProfile(userDTO), HttpStatus.OK);
    }

}
