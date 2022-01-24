//package com.green.greenchallenge.service;
//
//import com.green.greenchallenge.domain.User;
//import com.green.greenchallenge.exception.CustomException;
//import com.green.greenchallenge.repository.UserRepository;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//
//import java.util.Optional;
//
//@SpringBootTest
//public class UserServiceTest {
//    @Autowired
//    UserService userService;
//    @Autowired
//    UserRepository userRepository;
//
//    @BeforeAll
//    void initDB() {
//        User user;
//    }
//
//    @Test
//    void createUserTest() {
//        long countUser = userRepository.count();
//        User user = new User();
//        user.setName("test");
//        user.setEmail("test@naver.com");
//        user.setPassword("test123");
//        userService.createUser(user);
//        Assertions.assertEquals(countUser+1L,userRepository.count());
//    }
//
//    @Test
//    void idDuplicatedTest() {
//        String email = "test333@naver.com";
//        Assertions.assertTrue(userService.idDuplicated(email));
//        User user = new User();
//        user.setUserId(1L);
//        user.setName("test");
//        user.setEmail("test333@naver.com");
//        user.setPassword("test123");
//        userService.createUser(user);
//        Assertions.assertTrue(!userService.idDuplicated(email));
//    }
//
//    @Test
//    void updateProfileTest() {
//        User user = new User();
//        user.setName("updater");
//        user.setEmail("update@naver.com");
//        user.setPassword("update123");
//        userService.createUser(user);
//
//        User updateData = userRepository.findByEmail("update@naver.com");
//
//        updateData.setProfileImg("http://test.com/123");
//        updateData.setNickName("테스터");
//        updateData.setSiNm("서울시");
//        updateData.setSggNm("강남구");
//
//        User updatedUser = userRepository.findById(userService.updateProfile(updateData).getUserId()).get();
//
//        Assertions.assertEquals("http://test.com/123", updatedUser.getProfileImg());
//        Assertions.assertEquals("테스터", updatedUser.getNickName());
//        Assertions.assertEquals("서울시", updatedUser.getSiNm());
//        Assertions.assertEquals("강남구", updatedUser.getSggNm());
//    }
//
////    @Test
////    void getProfileTest() {
////        User getProfile = userService.getProfile(1L);
////        Assertions.assertEquals("");
////    }
//}
