package com.green.greenchallenge.service;

import com.green.greenchallenge.domain.User;
import com.green.greenchallenge.dto.UserResponseDTO;
import com.green.greenchallenge.exception.CustomException;
import com.green.greenchallenge.exception.ErrorCode;
import com.green.greenchallenge.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User createUser(User user) {
        User checkUser = userRepository.findByEmail(user.getEmail());
        if(checkUser != null) throw new CustomException(ErrorCode.EMAIL_EXIST);
        try {
            userRepository.save(user);
        } catch (RuntimeException ex) {
            throw new CustomException(ErrorCode.EMAIL_EXIST);
        }

        return userRepository.findById(user.getUserId()).get();
    }

    @Transactional
    public Boolean idDuplicated(String email) {
        User user = userRepository.findByEmail(email);

        if(user == null) return true;

        return false;
    }

    @Transactional
    public User getProfile(long userId) {
        Optional<User> profile = userRepository.findById(userId);

        if(profile == null) {
            throw new CustomException(ErrorCode.UNKNOWN_ERROR);
        }

        return profile.get();
    }

    @Transactional
    public UserResponseDTO updateProfile(User user) {
        return null;
    }
}
