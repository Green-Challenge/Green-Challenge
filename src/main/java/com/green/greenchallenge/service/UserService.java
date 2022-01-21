package com.green.greenchallenge.service;

import com.green.greenchallenge.domain.User;
import com.green.greenchallenge.exception.UserException;
import com.green.greenchallenge.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpStatusCodeException;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @SneakyThrows
    @Transactional
    public User register(User user) {

        if (userRepository.findUserByEmail(user.getEmail()) != null) {
            throw new RuntimeException("email duplicated");
        } else if (user.getName() == null) {
            throw new RuntimeException("null value");
        }

        try {
            return userRepository.save(user);
        } catch (RuntimeException e) {
            throw new UserException("register error");
        }
    }

    @Transactional
    public Boolean duplicate(String email) {
        if (userRepository.findUserByEmail(email) != null) {
            return true;
        } else {
            return false;
        }
    }

    @Transactional
    public User signIn(User user) {
        User findUser = userRepository.findUserByEmail(user.getEmail());

        if (findUser == null) {
            throw new UserException(String.format("Email[%s] not founded", user.getEmail()));
        } else if (findUser.getPassword().equals(user.getPassword())) {
            return findUser;
        } else {
            throw new UserException(String.format("not matched password"));
        }
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
