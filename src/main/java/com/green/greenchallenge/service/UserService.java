package com.green.greenchallenge.service;

import com.green.greenchallenge.domain.User;
import com.green.greenchallenge.dto.UserDTO;
import com.green.greenchallenge.exception.CustomException;
import com.green.greenchallenge.exception.ErrorCode;
import com.green.greenchallenge.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserDTO createUser(UserDTO userDTO) {
        User checkedUser = userRepository.findByEmail(userDTO.getEmail());

        if(checkedUser != null) throw new CustomException(ErrorCode.EMAIL_EXIST);

        userDTO.encodePassword(passwordEncoder);

        userRepository.save(userDTO.toEntity());

        User user = userRepository.findByEmail(userDTO.getEmail());
        return UserDTO.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .build();
    }

    @Transactional
    public Boolean idDuplicated(String email) {
        User user = userRepository.findByEmail(email);

        if(user == null) return true;

        return false;
    }

    @Transactional
    public UserDTO getProfile(long userId) {
        Optional<User> profile = userRepository.findById(userId);

        if(profile.isEmpty()) throw new CustomException(ErrorCode.USER_NOT_FOUND);

        User user = profile.get();

        return UserDTO.builder()
                .profileImg(user.getProfileImg())
                .nickName(user.getNickName())
                .siNm(user.getSiNm())
                .sggNm(user.getSggNm())
                .build();
    }

    @Transactional
    public UserDTO updateProfile(User user) {
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
        return UserDTO.builder()
                .userId(savedUser.getUserId())
                .name(savedUser.getName())
                .build();
    }
}
