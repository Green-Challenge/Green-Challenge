package com.green.greenchallenge.api;

import com.green.greenchallenge.domain.User;
import com.green.greenchallenge.dto.ResponseDto;
import com.green.greenchallenge.exception.AlreadyExistEmailException;
import com.green.greenchallenge.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
//    @PostMapping("/api/auth/register")
//    public ResponseEntity<Object> registerUser(@RequestBody User user, Map<String, Object> map){
//        ResponseEntity<Object> responseEntity = null;
//        Map<String, Object> result = new HashMap<>();
//
//        try {
//            userService.registerUser(user);
//            result.put("success",true);
//            result.put("errorMsg?", null);
//            responseEntity = new ResponseEntity<Object>(result, HttpStatus.OK);
//        } catch (AlreadyExistEmailException e){
//            e.printStackTrace();
//            result.put("success",false);
//            result.put("errorMsg?",e.getMessage());
//            responseEntity = new ResponseEntity<Object>(map, HttpStatus.BAD_REQUEST);
//        }
//
//        return responseEntity;
//    }

    @PostMapping("/api/auth/register")
    public ResponseDto registerUser(@RequestBody User user){

        ResponseDto responseDto = new ResponseDto(true, null);
        userService.registerUser(user);
        return responseDto;

    }
}
