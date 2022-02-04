package com.green.greenchallenge.service;

import com.green.greenchallenge.domain.MovementLog;
import com.green.greenchallenge.domain.User;
import com.green.greenchallenge.dto.MovementLogDTO;
import com.green.greenchallenge.dto.UserDTO;
import com.green.greenchallenge.exception.CustomException;
import com.green.greenchallenge.exception.ErrorCode;
import com.green.greenchallenge.repository.MovementLogRepository;
import com.green.greenchallenge.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyService {

    private final UserRepository userRepository;
    private final MovementLogRepository movementLogRepository;

    @Transactional
    public UserDTO createProfile(UserDTO userDTO) {
        Optional<User> findUser = userRepository.findById(userDTO.getUserId());

        if(findUser.isEmpty()) throw new CustomException(ErrorCode.USER_NOT_FOUND);

        findUser.get().setProfileImg(userDTO.getProfileImg());
        findUser.get().setNickName(userDTO.getNickName());
        findUser.get().setSiNm(userDTO.getSiNm());
        findUser.get().setSggNm(userDTO.getSggNm());

        userRepository.save(findUser.get());

        return UserDTO.builder()
                .profileImg(findUser.get().getProfileImg())
                .nickName(findUser.get().getNickName())
                .siNm(findUser.get().getSiNm())
                .sggNm(findUser.get().getSggNm())
                .build();
    }

    @Transactional
    public List<MovementLogDTO> getChart(Long userId) {
        List<MovementLog> logList = movementLogRepository.findByUserId(userId).stream()
                .map(Optional::orElseThrow).collect(Collectors.toList());

        if(logList.isEmpty()) throw new CustomException(ErrorCode.USER_NOT_FOUND);

        return logList.stream().map(MovementLogDTO::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public MovementLogDTO insertLog(MovementLogDTO movementLogDTO) {
        User findUser = userRepository.findById(movementLogDTO.getUserId()).orElseThrow();

        MovementLog move = movementLogDTO.toEntity();
        move.setUser(findUser);

        movementLogRepository.save(move);

        return MovementLogDTO.toDTO(move);
    }

    @Transactional
    public UserDTO getProfile(Long userId) {
        Optional<User> findUser = userRepository.findById(userId);

        if(findUser.isEmpty()) throw new CustomException(ErrorCode.USER_NOT_FOUND);

        return UserDTO.builder()
                .sggNm(findUser.get().getProfileImg())
                .nickName(findUser.get().getNickName())
                .siNm(findUser.get().getSiNm())
                .sggNm(findUser.get().getSggNm())
                .token(findUser.get().getToken())
                .build();
    }
}
