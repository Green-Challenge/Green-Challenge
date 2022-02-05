package com.green.greenchallenge.service;

import com.green.greenchallenge.domain.*;
import com.green.greenchallenge.dto.AddRecordDTO;
import com.green.greenchallenge.dto.ChallengeDTO;
import com.green.greenchallenge.dto.ChallengeResponseDTO;
import com.green.greenchallenge.exception.CustomException;
import com.green.greenchallenge.exception.ErrorCode;
import com.green.greenchallenge.repository.*;
import lombok.RequiredArgsConstructor;
import org.h2.index.TreeIndex;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeRepository challengeRepository;
    private final UserRepository userRepository;
    private final ParticipantRepository participantRepository;
    private final TreeRepository treeRepository;
    private final TreeInstanceRepository treeInstanceRepository;
    private final MovementLogRepository movementLogRepository;

    @Transactional
    public ChallengeResponseDTO getChallenge(long challengeId) {

        Optional<Challenge> getChallenge = challengeRepository.findById(challengeId);
        if (getChallenge.isEmpty()) throw new CustomException(ErrorCode.CHALLENGE_NOT_FOUND);

        Challenge challenge = getChallenge.get();

        List<Tree> treeList = treeRepository.findByChallengeId(challenge);
        HashMap<Long, Integer> sameTree = new HashMap<>();
        for (Tree tree : treeList) {
            sameTree.put(tree.getTreeId(), tree.getTreeGrowth());
        }
        Long maxTreeId = Collections.max(sameTree.entrySet(), Map.Entry.comparingByValue()).getKey();
        System.out.println(maxTreeId);

        return ChallengeResponseDTO.builder()
                .challengeName(challenge.getChallengeName())
                .numberOfChallengers(participantRepository.countByChallengeId(challenge))
                .rewardToken(challenge.getRewardToken())
                .description(challenge.getDescription())
                .hashTag(challenge.getHashTag())
                .treeId(maxTreeId.toString())
                .challengeImg(challenge.getChallengeImg())
                .build();
    }

    @Transactional
    public void addRecord(AddRecordDTO addRecordDTO) {

        Participant participant = participantRepository.findByUserIdAndChallengeId(
                User.builder().userId(addRecordDTO.getUserId()).build(),
                Challenge.builder().challengeId(addRecordDTO.getChallengeId()).build());

        double challengeGoalDistance = challengeRepository.findById(addRecordDTO.getChallengeId()).get().getGoalDistance();

        participant.setLeafCount( // 목표 이동거리를 넘겼을 경우 남은 나뭇잎 수를 증가시킴
                participant.getLeafCount() +
                        (
                                ((int) ((participant.getTotalDistance() + addRecordDTO.getAchieved()) / challengeGoalDistance)) -
                                        ((int) (participant.getTotalDistance() / challengeGoalDistance))
                        )
        );

        participant.setTotalDistance(participant.getTotalDistance() + addRecordDTO.getAchieved());

        participantRepository.save(participant);

        movementLogRepository.save(
                MovementLog.builder()
                        .user(User.builder().userId(addRecordDTO.getUserId()).build())
                        .distance(addRecordDTO.getAchieved())
                        .day(LocalDate.now())
                        .transportation(challengeRepository.findById(addRecordDTO.getChallengeId()).get().getTransportation())
                        .build()
        );
    }
}