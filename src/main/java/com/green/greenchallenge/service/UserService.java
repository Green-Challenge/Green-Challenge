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
    public void insertUser(User user) {
        userRepository.save(user);
    }

    @Transactional
    public void deleteUser(int userId) {
        userRepository.deleteById(userId);
    }

//    @Transactional
//    public User updateUser(User user) {
//        Optional<User> user = userRepository.findById(user.getId());
//    }
}
