package com.green.greenchallenge.service;

import com.green.greenchallenge.domain.User;
import com.green.greenchallenge.dto.UserResponseDTO;
import com.green.greenchallenge.exception.CustomException;
import com.green.greenchallenge.exception.ErrorCode;
import com.green.greenchallenge.exception.UserNotFoundException;
import com.green.greenchallenge.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserResponseDTO insertUser(User user) {
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
    public List<User> deleteUser(long userId) {
        Optional<User> user = userRepository.findById(userId);

        if(user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", userId));
        }

        userRepository.deleteById(userId);

        return userRepository.findAll();
    }

    @Transactional
    public User updateUser(User user, long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (!optionalUser.isPresent()) {
            throw new UserNotFoundException(String.format("ID[%s] not found", userId));
        }

        User selectedUser = optionalUser.get();
        selectedUser.setEmail(user.getEmail());
        selectedUser.setPassword(user.getPassword());
        selectedUser.setName(user.getName());
        selectedUser.setNickName(user.getNickName());
        selectedUser.setAddress(user.getAddress());

        return userRepository.save(selectedUser);
    }

    @Transactional
    public UserResponseDTO registerUser(User user){

        User getUser = userRepository.findByEmail(user.getEmail());

        if(getUser != null) {
            throw new CustomException(ErrorCode.EMAIL_EXIST);
        }
        try {
            userRepository.save(user);
        } catch (RuntimeException e){
            throw new CustomException(ErrorCode.UNKNOWN_ERROR);
        }

        User foundUser = userRepository.findById(user.getUserId()).get();

        return UserResponseDTO.builder()
                .userId(foundUser.getUserId())
                .name(foundUser.getName())
                .build();

    }

    @Transactional
    public boolean validEmail(String email){
        User getUser = userRepository.findByEmail(email);
        if(getUser != null){
            return false;
        } else {
            return true;
        }
    }


    @Transactional
    public UserResponseDTO getProfile(long userId){
        Optional<User> profile = userRepository.findById(userId);

        if(profile == null) throw new CustomException(ErrorCode.USER_NOT_FOUND);

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
        Optional<User> getUser = userRepository.findById(user.getUserId());

        if(getUser == null){
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        } else {
            User newUser = getUser.get();
            newUser.setProfileImg(user.getProfileImg());
            newUser.setNickName(user.getNickName());
            newUser.setSiNm(user.getSiNm());
            newUser.setSggNm(user.getSggNm());

            try {
                userRepository.save(newUser);
            } catch (RuntimeException ex) {
                throw new CustomException(ErrorCode.UNKNOWN_ERROR);
            }

        }

        User updatedUser = userRepository.findById(user.getUserId()).get();

        return UserResponseDTO.builder()
                .userId(updatedUser.getUserId())
                .name(updatedUser.getName())
                .build();
    }

}
