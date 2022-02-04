package com.green.greenchallenge.service;

import com.green.greenchallenge.domain.Challenge;
import com.green.greenchallenge.domain.Participant;
import com.green.greenchallenge.domain.User;
import com.green.greenchallenge.dto.ChallengeJoinRequestDTO;
import com.green.greenchallenge.dto.ParticipantDTO;
import com.green.greenchallenge.exception.CustomException;
import com.green.greenchallenge.exception.ErrorCode;
import com.green.greenchallenge.repository.ChallengeRepository;
import com.green.greenchallenge.repository.ParticipantRepository;
import com.green.greenchallenge.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ParticipantService {

    private final ParticipantRepository participantRepository;
    private final UserRepository userRepository;
    private final ChallengeRepository challengeRepository;

    @Transactional
    public List<Participant> getUserParticipant(Long userId){
        return null;
    }

    @Transactional
    public void createParticipant(ChallengeJoinRequestDTO challengeJoinRequestDTO){

        System.out.println("service의 createParticipant 진입");
        Optional<User> getUser = userRepository.findById(challengeJoinRequestDTO.getUserId());
        User user = getUser.get();
        System.out.println("유저 정보 확인");

        Optional<Challenge> getChallenge = challengeRepository.findById(challengeJoinRequestDTO.getChallengeId());
        Challenge challenge = getChallenge.get();
        System.out.println("챌린지 정보 확인");

        Participant checkedParticipant = participantRepository.findByUserIdAndChallengeId(user, challenge);
        if(checkedParticipant != null) throw new CustomException(ErrorCode.PARTICIPANT_EXIT);

        ParticipantDTO newParticipantDTO = ParticipantDTO.builder()
                .userId(user)
                .challengeId(challenge)
                .participateDate(LocalDate.now())
                .leafCount(0)
                .totalDistance(0.0)
                .build();

        participantRepository.save(newParticipantDTO.toParticipantEntity());

    }

}
