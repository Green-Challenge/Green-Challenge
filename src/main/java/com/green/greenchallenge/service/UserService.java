package com.green.greenchallenge.service;

import com.green.greenchallenge.domain.User;
import com.green.greenchallenge.exception.CustomException;
import com.green.greenchallenge.exception.ErrorCode;
import com.green.greenchallenge.exception.GlobalExceptinHandler;
import com.green.greenchallenge.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

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
            throw new CustomException(ErrorCode.DUPLICATE_RESOURCE);
        } else if (user.getName() == null) {
            throw new CustomException(ErrorCode.NULL_RESOURCE);
        }

        try {
            return userRepository.save(user);
        } catch (RuntimeException e) {
            throw new RuntimeException(e);
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
            throw new CustomException(ErrorCode.NOT_FOUNDED);
        } else if (findUser.getPassword().equals(user.getPassword())) {
            return findUser;
        } else {
            throw new CustomException(ErrorCode.NOT_MATCHED);
        }
    }

    @Transactional
    public User getProfile(Long userId) {
        Optional<User> findUser = userRepository.findById(userId);
        if(findUser.get().getEmail() == null) {
            throw new CustomException(ErrorCode.NOT_FOUNDED);
        }
        return findUser.get();
    }

    @Transactional
    public User updateProfile(User user) {
        Optional<User> findUser = userRepository.findById(user.getUserId());

        findUser.get().setProfileImg(user.getProfileImg());
        findUser.get().setNickName(user.getNickName());
        findUser.get().setSiNm(user.getSiNm());
        findUser.get().setSggNm(user.getSggNm());

        userRepository.save(findUser.get());

        return findUser.get();
    }
}
