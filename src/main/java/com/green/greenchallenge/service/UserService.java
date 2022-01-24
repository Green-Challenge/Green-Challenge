package com.green.greenchallenge.service;

import com.green.greenchallenge.domain.User;
import com.green.greenchallenge.dto.UserDTO;
import com.green.greenchallenge.exception.CustomException;
import com.green.greenchallenge.exception.ErrorCode;
import com.green.greenchallenge.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UserDTO insertUser(UserDTO userDTO) {

        User checkUser = userRepository.findByEmail(userDTO.getEmail());
        if(checkUser != null) throw new CustomException(ErrorCode.EMAIL_EXIST);
        checkUser = userDTO.toEntity();

//        userDTO.setCreateDate(LocalDate.now()); // 생성시간

            System.out.println("서비스 접근중");
            userRepository.save(checkUser);
            System.out.println("서비스 완료");

        User savedUser = userRepository.findById(checkUser.getUserId()).get();

        return UserDTO.builder()
                .userId(savedUser.getUserId())
                .name(savedUser.getName())
                .email(savedUser.getEmail())
                .build();

    }

    @Transactional
    public List<User> deleteUser(long userId) {
        Optional<User> user = userRepository.findById(userId);

        if(user == null) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        userRepository.deleteById(userId);

        return userRepository.findAll();
    }

    @Transactional
    public User updateUser(User user, long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (!optionalUser.isPresent()) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        User selectedUser = optionalUser.get();
        selectedUser.setEmail(user.getEmail());
        selectedUser.setPassword(user.getPassword());
        selectedUser.setName(user.getName());
        selectedUser.setNickName(user.getNickName());


        return userRepository.save(selectedUser);
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
    public UserDTO getProfile(long userId){
        Optional<User> profile = userRepository.findById(userId);

        if(profile == null) throw new CustomException(ErrorCode.USER_NOT_FOUND);

        User user = profile.get();

        return UserDTO.builder()
                .profileImg(user.getProfileImg())
                .nickName(user.getNickName())
                .siNm(user.getSiNm())
                .sggNm(user.getSggNm())
                .build();
    }

    @Transactional
    public UserDTO updateProfile(UserDTO userDTO) {
        User getUser = userRepository.findById(userDTO.getUserId()).get();

        if(getUser == null){
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        } else {
            System.out.println("UserId: " + getUser.getUserId());

            userDTO.setUserId(getUser.getUserId());
            userDTO.setEmail(getUser.getEmail());
            userDTO.setPassword(getUser.getPassword());
            userDTO.setName(getUser.getName());
            userDTO.setCreateDate(getUser.getCreateDate());

            User updatingUser = userDTO.toEntity();

            userRepository.save(updatingUser);

        }

        User updatedUser = userRepository.findById(userDTO.getUserId()).get();

        return UserDTO.builder()
                .nickName(updatedUser.getNickName())
                .siNm(updatedUser.getSiNm())
                .sggNm(updatedUser.getSggNm())
                .profileImg(updatedUser.getProfileImg())
                .build();
    }

}
