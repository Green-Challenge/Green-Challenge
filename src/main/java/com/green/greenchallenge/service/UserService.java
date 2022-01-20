package com.green.greenchallenge.service;

import com.green.greenchallenge.domain.User;
import com.green.greenchallenge.exception.UserNotFoundException;
import com.green.greenchallenge.repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User register(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public User signIn(User user) {
        User findUser = userRepository.findUserByEmail(user.getEmail());
        if(findUser.getPassword().equals(user.getPassword())) {
            findUser.setSuccess(true);
            findUser.setErrorMsg(null);
        } else {
            findUser.setSuccess(false);
            findUser.setErrorMsg("error");
        }

        return findUser;
    }

    @Transactional
    public User getProfile(User user) {
        Optional<User> findUser = userRepository.findById(user.getUserId());

        return findUser.get();
    }

    @Transactional
    public User editProfile(User user) {
        Optional<User> findUser = userRepository.findById(user.getUserId());

        findUser.get().setProfileImg(user.getProfileImg());
        findUser.get().setNickName(user.getNickName());
        findUser.get().setSiNm(user.getSiNm());
        findUser.get().setSggNm(user.getSggNm());

        userRepository.save(findUser.get());

        return findUser.get();
    }
}
