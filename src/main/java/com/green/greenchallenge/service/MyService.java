package com.green.greenchallenge.service;

import com.green.greenchallenge.domain.*;
import com.green.greenchallenge.dto.GetChartResponseDTO;
import com.green.greenchallenge.dto.GetTreeTogetherDTO;
import com.green.greenchallenge.domain.MovementLog;
import com.green.greenchallenge.domain.User;
import com.green.greenchallenge.dto.MovementLogDTO;
import com.green.greenchallenge.dto.UserDTO;
import com.green.greenchallenge.exception.CustomException;
import com.green.greenchallenge.exception.ErrorCode;
import com.green.greenchallenge.repository.*;
import com.green.greenchallenge.repository.MovementLogRepository;
import com.green.greenchallenge.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MyService {

    private final UserRepository userRepository;
    private final ParticipantRepository participantRepository;
    private final TreeInstanceRepository treeInstanceRepository;
    private final TreeRepository treeRepository;
    private final MovementLogRepository movementLogRepository;
    private final DonationLogRepository donationLogRepository;
    private final ChallengeRepository challengeRepository;

    @Transactional
    public UserDTO createProfile(UserDTO userDTO) {
        Optional<User> findUser = userRepository.findById(userDTO.getUserId());

        if (findUser.isEmpty()) throw new CustomException(ErrorCode.USER_NOT_FOUND);

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
    public GetChartResponseDTO getChart(Long userId) {
        Optional<User> findUser = userRepository.findById(userId);
        if (findUser.isEmpty()) throw new CustomException(ErrorCode.USER_NOT_FOUND);

        LocalDate start = LocalDate.now().withDayOfMonth(1);
        LocalDate end = LocalDate.now();

        List<MovementLog> nowMonth = movementLogRepository.findByUserAndDayGreaterThanEqualAndDayLessThanEqual(findUser.get(), start, end).stream()
                .map(Optional::orElseThrow)
                .collect(Collectors.toList());
        List<MovementLog> lastMonth = movementLogRepository.findByUserAndDayGreaterThanEqualAndDayLessThanEqual(findUser.get(), start.minusMonths(1), start.minusDays(1)).stream()
                .map(Optional::orElseThrow)
                .collect(Collectors.toList());

        List<MovementLogDTO> nowMonthDTO = nowMonth.stream()
                .map(MovementLogDTO::toDTO)
                .collect(Collectors.toList());
        List<MovementLogDTO> lastMonthDTO = lastMonth.stream()
                .map(MovementLogDTO::toDTO)
                .collect(Collectors.toList());

        return GetChartResponseDTO.builder()
                .currentMonth(nowMonthDTO)
                .lastMonth(lastMonthDTO)
                .build();
    }

    @Transactional
    public UserDTO getProfile(Long userId) {
        Optional<User> findUser = userRepository.findById(userId);

        if (findUser.isEmpty()) throw new CustomException(ErrorCode.USER_NOT_FOUND);

        return UserDTO.builder()
                .sggNm(findUser.get().getProfileImg())
                .nickName(findUser.get().getNickName())
                .siNm(findUser.get().getSiNm())
                .sggNm(findUser.get().getSggNm())
                .token(findUser.get().getToken())
                .build();
    }

    @Transactional
    public ArrayList<GetTreeTogetherDTO> getTreeTogether(Long userId) {
        List<Participant> participantList = participantRepository.findByUserId(User.builder().userId(userId).build());
        ArrayList<GetTreeTogetherDTO> getTreeTogethers = new ArrayList<>();

        if (participantList.isEmpty()) {
            throw new CustomException(ErrorCode.PARTICIPANT_EMPTY);
        }

        for (Participant participant : participantList) {
            int numberOfCompletions = 0;
            int instanceNumberOfLeaf = 0;
            boolean isFinished = false;
            List<TreeInstance> treeInstanceList = treeInstanceRepository.findByChallengeId(participant.getChallengeId());

            for (TreeInstance treeInstance : treeInstanceList) {
                if (treeInstance.getFinishedDate() != null) { // 완료가 된 나무인스턴스중에서
                    List<DonationLog> donationLogList = donationLogRepository.findByTreeInstanceIdAndParticipantId(treeInstance, participant); // 사용자가 기부한 인스턴스를 찾아서
                    if (!donationLogList.isEmpty()) {
                        numberOfCompletions++; // 증가시킨다.
                    }
                }
                instanceNumberOfLeaf = treeInstance.getNumberOfLeaf();
            }

            if (!treeRepository.findByChallengeId(participant.getChallengeId()).isEmpty()) {
                int treeGrowth = 1;
                int challengeGoalLeaves = challengeRepository.findById(participant.getChallengeId().getChallengeId()).get().getGoalLeaves();

                if (instanceNumberOfLeaf < challengeGoalLeaves / 3) {
                    treeGrowth = 1;
                } else if (instanceNumberOfLeaf < challengeGoalLeaves * 2 / 3) {
                    treeGrowth = 2;
                } else {
                    treeGrowth = 3;
                }

                Long treeId = treeRepository.findByChallengeIdAndTreeGrowth(participant.getChallengeId(), treeGrowth).getTreeId();

                GetTreeTogetherDTO togetherDTO = new GetTreeTogetherDTO(
                        participant.getChallengeId().getChallengeId(),
                        participant.getChallengeId().getChallengeName(),
                        numberOfCompletions,
                        treeId,
                        (int) (participant.getTotalDistance() / participant.getChallengeId().getGoalDistance())
                );

                getTreeTogethers.add(togetherDTO);
            }
        }

        return getTreeTogethers;
    }
}
