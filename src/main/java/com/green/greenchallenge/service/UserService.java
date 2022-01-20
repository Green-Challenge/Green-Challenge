package com.green.greenchallenge.service;

import com.green.greenchallenge.domain.User;
import com.green.greenchallenge.exception.CustomException;
import com.green.greenchallenge.exception.ErrorCode;
import com.green.greenchallenge.exception.UserNotFoundException;
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
    public List<User> retrieveUsers() {
        return userRepository.findAll();
    }

    @Transactional
    public User retrieveUser(long userId) {
        Optional<User> user = userRepository.findById(userId);

        if(user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", userId));
        }

        return user.get();
    }

    @Transactional
    public User insertUser(User user) {
        return userRepository.save(user);
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
    public void registerUser(User user){

        User getUser = userRepository.findByEmail(user.getEmail());

        if(getUser != null){
            throw new CustomException(ErrorCode.EMAIL_EXIST);
        } else {
            userRepository.save(user);
        }

    }

    @Transactional
    public boolean validEmail(String email){
        User getUser = userRepository.findByEmail(email);
        if(getUser != null){
            throw new CustomException(ErrorCode.EMAIL_EXIST);
        } else {
            return true;
        }
    }



}
