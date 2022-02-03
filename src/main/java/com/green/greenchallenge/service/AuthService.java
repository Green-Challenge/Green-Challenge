package com.green.greenchallenge.service;

import com.green.greenchallenge.config.JwtProvider;
import com.green.greenchallenge.domain.User;
import com.green.greenchallenge.dto.UserDTO;
import com.green.greenchallenge.exception.CustomException;
import com.green.greenchallenge.exception.ErrorCode;
import com.green.greenchallenge.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtProvider jwtProvider;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public String login(UserDTO userDTO) {
        User loginUser = userRepository.findByEmail(userDTO.getEmail());

        if(loginUser == null) throw new CustomException(ErrorCode.USER_NOT_FOUND);

        if(!passwordEncoder.matches(userDTO.getPassword(), loginUser.getPassword()))
            throw new CustomException(ErrorCode.UNKNOWN_ERROR);
        return jwtProvider.createToken(String.valueOf(loginUser.getUserId()), userDTO.getRoles());
    }
}
