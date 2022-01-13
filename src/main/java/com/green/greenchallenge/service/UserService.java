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
    public User retrieveUser(int userId) {
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
    public List<User> deleteUser(int userId) {
        Optional<User> user = userRepository.findById(userId);

        if(user == null) {
            throw new UserNotFoundException(String.format("ID[%s] not found", userId));
        }

        userRepository.deleteById(userId);

        return userRepository.findAll();
    }

    @Transactional
    public User updateUser(User user, int userId) {
        Optional<User> optionalUser = userRepository.findById(userId);

        if (!optionalUser.isPresent()) {
            throw new UserNotFoundException(String.format("ID[%s] not found", userId));
        }

        User selectedUser = optionalUser.get();
        selectedUser.setId(user.getId());
        selectedUser.setPassword(user.getPassword());
        selectedUser.setName(user.getName());
        selectedUser.setAge(user.getAge());

        return userRepository.save(selectedUser);
    }
}
