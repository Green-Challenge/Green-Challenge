package com.green.greenchallenge.service;

import com.green.greenchallenge.domain.User;
import com.green.greenchallenge.dto.UserResponseDTO;
import com.green.greenchallenge.exception.CustomException;
import com.green.greenchallenge.exception.ErrorCode;
import com.green.greenchallenge.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserResponseDTO createUser(User user) {
        User checkUser = userRepository.findByEmail(user.getEmail());

        if(checkUser != null) throw new CustomException(ErrorCode.EMAIL_EXIST);

        user.setCreateDate(LocalDate.now());

        try {
            userRepository.save(user);
        } catch (RuntimeException ex) {
            throw new CustomException(ErrorCode.UNKNOWN_ERROR);
        }

        User savedUser = userRepository.findById(user.getUserId()).get();

        return UserResponseDTO.builder()
                .userId(savedUser.getUserId())
                .name(savedUser.getName())
                .build();
    }

    @Transactional
    public Boolean idDuplicated(String email) {
        User user = userRepository.findByEmail(email);

        if(user == null) return true;

        return false;
    }

    @Transactional
    public UserResponseDTO getProfile(long userId) {
        Optional<User> profile = userRepository.findById(userId);

        if(profile.isEmpty()) throw new CustomException(ErrorCode.USER_NOT_FOUND);

        User user = profile.get();

        return UserResponseDTO.builder()
                .profileImg(user.getProfileImg())
                .nickName(user.getNickName())
                .siNm(user.getSiNm())
                .sggNm(user.getSggNm())
                .build();
    }

    @Transactional
    public UserResponseDTO updateProfile(User user) {
        Optional<User> selectedUser = userRepository.findById(user.getUserId());

        if(selectedUser.isEmpty()) throw new CustomException(ErrorCode.USER_NOT_FOUND);

        User updatedUser = selectedUser.get();

        updatedUser.setProfileImg(user.getProfileImg());
        updatedUser.setNickName(user.getNickName());
        updatedUser.setSiNm(user.getSiNm());
        updatedUser.setSggNm(user.getSggNm());

        try {
            userRepository.save(updatedUser);
        } catch (RuntimeException ex) {
            throw new CustomException(ErrorCode.UNKNOWN_ERROR);
        }

        User savedUser = userRepository.findById(user.getUserId()).get();

        return UserResponseDTO.builder()
                .userId(savedUser.getUserId())
                .name(savedUser.getName())
                .build();
    }
}
