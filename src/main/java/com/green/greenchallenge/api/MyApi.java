package com.green.greenchallenge.api;

import com.green.greenchallenge.domain.User;
import com.green.greenchallenge.dto.GetTreeTogetherDTO;
import com.green.greenchallenge.dto.MovementLogDTO;
import com.green.greenchallenge.dto.UserDTO;
import com.green.greenchallenge.service.MyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/api/my", produces = "application/json; charset=utf8")
@RequiredArgsConstructor
public class MyApi {
    private final MyService myService;

    @PostMapping("/profile")
    public ResponseEntity<UserDTO> createProfile(@RequestBody UserDTO userDTO) {
        return new ResponseEntity(myService.createProfile(userDTO), HttpStatus.OK);
    }

    @PutMapping("/profile")
    public ResponseEntity<UserDTO> updateProfile(@RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(myService.createProfile(userDTO), HttpStatus.OK);
    }

    @GetMapping("/chart/{userId}")
    public ResponseEntity<List<MovementLogDTO>> getChart(@PathVariable Long userId) {
        return new ResponseEntity<>(myService.getChart(userId), HttpStatus.OK);
    }

    @PostMapping("/chart")
    public ResponseEntity insertLog(@RequestBody MovementLogDTO movementLogDTO) {
        return new ResponseEntity(myService.insertLog(movementLogDTO), HttpStatus.OK);
    }

    @GetMapping("/profile/{userId}")
    public ResponseEntity<UserDTO> getProfile(@PathVariable Long userId) {
        return new ResponseEntity<>(myService.getProfile(userId), HttpStatus.OK);
    }

    @GetMapping("/trees/{userId}")
    public ResponseEntity<ArrayList<GetTreeTogetherDTO>> getTreeTogether(@PathVariable Long userId) {
        User user = new User();
        user.setUserId(userId);
        return new ResponseEntity<>(myService.getTreeTogether(user), HttpStatus.OK);
    }
}
