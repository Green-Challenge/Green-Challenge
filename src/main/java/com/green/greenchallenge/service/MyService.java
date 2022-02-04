package com.green.greenchallenge.service;

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
    public List<MovementLogDTO> getChart(UserDTO userDTO) {
        Optional<MovementLogDTO> logList = movementLogRepository.findByUserId(userDTO.getUserId());

        if(logList.isEmpty()) throw new CustomException(ErrorCode.USER_NOT_FOUND);

        return logList.stream().map(MovementLogDTO::toDTO).collect(Collectors.toList());
    }

    @Transactional
    public MovementLogDTO insertLog(MovementLogDTO movementLogDTO) {
        movementLogRepository.save(movementLogDTO.toEntity());

        return movementLogDTO.toDTO();
    }
}
