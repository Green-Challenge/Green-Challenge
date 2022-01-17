package com.green.greenchallenge.service;

import com.green.greenchallenge.domain.User;
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
        selectedUser.setNickname(user.getNickname());
        selectedUser.setAddress(user.getAddress());

        return userRepository.save(selectedUser);
    }
}
