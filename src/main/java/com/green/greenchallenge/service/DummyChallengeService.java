package com.green.greenchallenge.service;

import com.green.greenchallenge.domain.*;
import com.green.greenchallenge.dto.AddRecordDTO;
import com.green.greenchallenge.exception.CustomException;
import com.green.greenchallenge.exception.ErrorCode;
import com.green.greenchallenge.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DummyChallengeService {

    private final ChallengeRepository challengeRepository;
    private final UserRepository userRepository;
    private final ParticipantRepository participantRepository;
    private final TreeInstanceRepository treeInstanceRepository;
    private final MovementLogRepository movementLogRepository;
    private final DonationLogRepository donationLogRepository;

    @Transactional
    public void addRecord(AddRecordDTO addRecordDTO, LocalDate dummyDate) {
        Optional<Challenge> challenge = challengeRepository.findById(addRecordDTO.getChallengeId());
        if (challenge.isEmpty()) {
            throw new CustomException(ErrorCode.CHALLENGE_NOT_FOUND);
        }

        Optional<User> user = userRepository.findById(addRecordDTO.getUserId());
        if (user.isEmpty()) {
            throw new CustomException(ErrorCode.USER_NOT_FOUND);
        }

        Participant participant = participantRepository.findByUserIdAndChallengeId(user.get(), challenge.get());
        if (participant == null) {
            throw new CustomException(ErrorCode.PARTICIPANT_EMPTY);
        }

        double challengeGoalDistance = challenge.get().getGoalDistance();

        if (addRecordDTO.getAchieved() <= 0) {
            throw new CustomException(ErrorCode.WRONG_VALUE);
        }
        int addLeaves = ((int) ((participant.getTotalDistance() + addRecordDTO.getAchieved()) / challengeGoalDistance)) - ((int) (participant.getTotalDistance() / challengeGoalDistance));

        participant.setLeafCount( // 목표 이동거리를 넘겼을 경우 나뭇잎 수를 증가시킴
                participant.getLeafCount() + addLeaves
        );

        for (; addLeaves > 0; addLeaves--) { // 만약 생성된 나뭇잎수가 1개 이상일 경우에 자동적으로 기부가 되도록 함
            Optional<TreeInstance> treeInstance = treeInstanceRepository.findByChallengeIdAndFinishedDateIsNull(challenge.get());
            if (treeInstance.isEmpty()) { // 만약 새로 시작할 인스턴스가 없을경우, 새로운 인스턴스를 생성한다.
                treeInstanceRepository.save(
                        TreeInstance.builder()
                                .challengeId(challenge.get())
                                .build()
                );

                Optional<TreeInstance> newTreeInstance = treeInstanceRepository.findByChallengeIdAndFinishedDateIsNull(challenge.get());
                // 위에서 새로 생성시켰으므로, finished date가 null인 값이 존재한다. 해당 나무인스턴스의 기부로그를 추가한다.
                newTreeInstance.get().setNumberOfLeaf(newTreeInstance.get().getNumberOfLeaf() + 1);
                donationLogRepository.save(
                        DonationLog.builder()
                                .treeInstanceId(newTreeInstance.get())
                                .participantId(participant)
                                .donationDate(dummyDate)
                                .build()
                );

            } else { // 시작할 인스턴스가 있는 경우 해당 인스턴스에 값을 저장한다.
                treeInstance.get().setNumberOfLeaf(treeInstance.get().getNumberOfLeaf() + 1); // 나무인스턴스의 모인나뭇잎 수를 1 증가시킨다.
                donationLogRepository.save( // 해당 나무인스턴스의 기부로그를 추가한다.
                        DonationLog.builder()
                                .treeInstanceId(treeInstance.get())
                                .participantId(participant)
                                .donationDate(dummyDate)
                                .build()
                );
                if (treeInstance.get().getNumberOfLeaf() == challenge.get().getGoalLeaves()) {
                    // 만약 모인 나뭇잎수가 챌린지의 목표 나뭇잎수에 도달할 경우 해당 인스턴스를 종료하고, 새로운 인스턴스를 생성한다.
                    // 나무인스턴스가 완료되고 새로운 나무인스턴스가 생성된다.
                    treeInstance.get().setFinishedDate(dummyDate);
                    treeInstanceRepository.save(
                            TreeInstance.builder()
                                    .challengeId(challenge.get())
                                    .build()
                    );

                    // 1. 나무인스턴스가 완료됐으므로, 해당 나무 인스턴스에 참여한 기부로그를 조회한다.
                    List<DonationLog> donationLogList = donationLogRepository.findByTreeInstanceId(treeInstance.get());

                    // 2. 해당 기부로그에서 참여번호로 해당유저를 찾은다음 리워드토큰을 지급한다.
                    for (DonationLog donationLog : donationLogList) {
                        Optional<User> donatedUser = userRepository.findById(
                                participantRepository.findById(donationLog.getParticipantId().getParticipantId()).get().getUserId().getUserId()
                        );
                        donatedUser.get().setToken(
                                donatedUser.get().getToken() + challenge.get().getRewardToken() / 100
                        );
                    }
                }
            }
        }

        participant.setTotalDistance(participant.getTotalDistance() + addRecordDTO.getAchieved());

        participantRepository.save(participant);

        movementLogRepository.save(
                MovementLog.builder()
                        .user(user.get())
                        .distance(addRecordDTO.getAchieved())
                        .day(dummyDate)
                        .transportation(challenge.get().getTransportation())
                        .build()
        );
    }
}