package com.green.greenchallenge.service;

import com.green.greenchallenge.domain.User;
import com.green.greenchallenge.dto.UserResponseDTO;
import com.green.greenchallenge.exception.UserConflictException;
import com.green.greenchallenge.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User createUser(User user) {
        try {
            userRepository.save(user);
        } catch (RuntimeException ex) {
            throw new UserConflictException("중복된 사용자 입니다");
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
    public UserResponseDTO getProfile() {
        return null;
    }

    @Transactional
    public UserResponseDTO updateProfile(User user) {
        return null;
    }
}
