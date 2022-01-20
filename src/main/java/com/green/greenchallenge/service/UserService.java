package com.green.greenchallenge.service;

import com.green.greenchallenge.domain.User;
import com.green.greenchallenge.exception.UserNotFoundException;
import com.green.greenchallenge.repository.UserRepository;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.SQLException;
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
    public User retrieveUser(long uid) {
        Optional<User> user = userRepository.findById(uid);

        if(user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", uid));
        }

        return user.get();
    }

    @Transactional
    public User insertUser(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public List<User> deleteUser(long uid) {
        Optional<User> user = userRepository.findById(uid);

        if(user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", uid));
        }

        userRepository.deleteById(uid);

        return userRepository.findAll();
    }

    @Transactional
    public User updateUser(User user, long uid) {
        Optional<User> optionalUser = userRepository.findById(uid);

        if (!optionalUser.isPresent()) {
            throw new UserNotFoundException(String.format("ID[%s] not found", uid));
        }

        User selectedUser = optionalUser.get();
        selectedUser.setEmail(user.getEmail());
        selectedUser.setPassword(user.getPassword());
        selectedUser.setName(user.getName());
        selectedUser.setNickName(user.getNickName());

        return userRepository.save(selectedUser);
    }

    @Transactional
    public User register(User user) {
        return userRepository.save(user);
    }

    @Transactional
    public User signIn(User user) {
        User findUser = userRepository.findUserByEmail(user.getEmail());
        if(findUser.getPassword().equals(user.getPassword())) {
            findUser.setSuccess(true);
            findUser.setErrorMsg(null);
        } else {
            findUser.setSuccess(false);
            findUser.setErrorMsg("error");
        }

        return findUser;
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
