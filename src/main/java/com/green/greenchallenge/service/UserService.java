package com.green.greenchallenge.service;

import com.green.greenchallenge.domain.User;
import com.green.greenchallenge.dto.UserResponseDto;
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

    public UserResponseDto register(UserResponseDto userResponseDto) {
        if (userRepository.findUserByEmail(userResponseDto.getEmail()) != null) {
            throw new CustomException(ErrorCode.DUPLICATE_RESOURCE);
        } else if (userResponseDto.getName() == null) {
            throw new CustomException(ErrorCode.NULL_RESOURCE);
        }

        User user = new User();
        user.setName(userResponseDto.getName());
        user.setEmail(userResponseDto.getEmail());
        user.setCreateDate(LocalDate.now());
        user.setPassword(userResponseDto.getPassword());

        try {
            userRepository.save(user);
            return userResponseDto;
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
    public UserResponseDto signIn(UserResponseDto userResponseDto) {
        User findUser = userRepository.findUserByEmail(userResponseDto.getEmail());

        if (findUser == null) {
            throw new CustomException(ErrorCode.NOT_FOUNDED);
        } else if (findUser.getPassword().equals(userResponseDto.getPassword())) {
            userResponseDto.setName(findUser.getName());
            userResponseDto.setLocation(findUser.getSiNm() + " " + findUser.getSggNm());
            userResponseDto.setProfileImg(findUser.getProfileImg());
            userResponseDto.setUserId(findUser.getUserId());

            return userResponseDto;
        } else {
            throw new CustomException(ErrorCode.NOT_MATCHED);
        }
    }

    @Transactional
    public UserResponseDto getProfile(Long userId) {
        Optional<User> findUser = userRepository.findById(userId);
        if(findUser.isEmpty()) {
            throw new CustomException(ErrorCode.NOT_FOUNDED);
        }

        UserResponseDto findUserResponseDto = new UserResponseDto();

        findUserResponseDto.setProfileImg(findUser.get().getProfileImg());
        findUserResponseDto.setNickName(findUser.get().getNickName());
        findUserResponseDto.setSiNm(findUser.get().getSiNm());
        findUserResponseDto.setSggNm(findUser.get().getSggNm());

        return findUserResponseDto;
    }

    @Transactional
    public UserResponseDto updateProfile(UserResponseDto userResponseDto) {
        Optional<User> findUser = userRepository.findById(userResponseDto.getUserId());

        findUser.get().setProfileImg(userResponseDto.getProfileImg());
        findUser.get().setNickName(userResponseDto.getNickName());
        findUser.get().setSiNm(userResponseDto.getSiNm());
        findUser.get().setSggNm(userResponseDto.getSggNm());

        userRepository.save(findUser.get());

        return userResponseDto;
    }
}
