package com.green.greenchallenge.service;

import com.green.greenchallenge.domain.*;
import com.green.greenchallenge.dto.*;
import com.green.greenchallenge.exception.CustomException;
import com.green.greenchallenge.exception.ErrorCode;
import com.green.greenchallenge.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChallengeService {

    private final ChallengeRepository challengeRepository;
    private final UserRepository userRepository;
    private final ParticipantRepository participantRepository;
    private final TreeRepository treeRepository;
    private final TreeInstanceRepository treeInstanceRepository;
    private final MovementLogRepository movementLogRepository;
    private final DonationLogRepository donationLogRepository;

    @Transactional
    public ChallengeResponseDTO getChallenge(long challengeId) {

        Optional<Challenge> getChallenge = challengeRepository.findById(challengeId);
        if (getChallenge.isEmpty()) throw new CustomException(ErrorCode.CHALLENGE_NOT_FOUND);

        Challenge challenge = getChallenge.get();

        List<Tree> treeList = treeRepository.findByChallengeId(challenge);
        HashMap<Long, Integer> sameTree = new HashMap<>();
        for(Tree tree : treeList){
            sameTree.put(tree.getTreeId(),tree.getTreeGrowth());
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
    public List<ChallengeListResponseDTO> getUserChallengeList(Long userId){

        Optional<User> getUser = userRepository.findById(userId);
        User user = getUser.get();
        HashMap<LocalDate, ChallengeListResponseDTO> sortChallengeResponseDTO = new HashMap<>();
        HashMap<Long, ChallengeListResponseDTO> unParticipateChallengeResponseDTO = new HashMap<>();

        List<Challenge> challengeList = challengeRepository.findAll(Sort.by(Sort.Direction.ASC, "challengeId"));
        List<ChallengeListResponseDTO> userChallengeList = new ArrayList<>();

        for(Challenge challenge : challengeList){
            ChallengeListResponseDTO challengeListResponseDTO =
            ChallengeListResponseDTO.builder()
                    .challengeId(Math.toIntExact(challenge.getChallengeId()))
                    .challengeName(challenge.getChallengeName())
                    .treeId(Integer.parseInt(getChallenge(challenge.getChallengeId()).getTreeId()))
                    .percent(0.0)
                    .rewordToken(challenge.getRewardToken())
                    .numberOfChallengers(participantRepository.countByChallengeId(challenge))
                    .isComplete(false)
                    .isParticipating(false)
                    .build();

            Participant challengeParticipant = participantRepository.findByUserIdAndChallengeId(user, challenge);
            if( challengeParticipant != null ){
                challengeListResponseDTO.setParticipating(true);
                List<TreeInstance> treeInstance = treeInstanceRepository.findByChallengeId(challenge);
                if(treeInstance != null){
                    challengeListResponseDTO.setPercent(
                            Double.valueOf(treeInstance.get(treeInstance.size()-1).getNumberOfLeaf()));
                }
                sortChallengeResponseDTO.put(challengeParticipant.getParticipateDate(), challengeListResponseDTO);
            } else {
                unParticipateChallengeResponseDTO.put(challenge.getChallengeId(), challengeListResponseDTO);
            }
        }

        List<LocalDate> participateDatekeyList = new ArrayList<>(sortChallengeResponseDTO.keySet());
        Collections.sort(participateDatekeyList, Collections.reverseOrder());
        List<Long> unParticipateDatekeyList = new ArrayList(unParticipateChallengeResponseDTO.keySet());
        unParticipateDatekeyList.sort(Long::compareTo);

        for (LocalDate localdate : participateDatekeyList){
            userChallengeList.add(sortChallengeResponseDTO.get(localdate));
        }

        for (Long challengeId : unParticipateDatekeyList){
            userChallengeList.add(unParticipateChallengeResponseDTO.get(challengeId));
        }

        return userChallengeList;
    }

    @Transactional
    public ChallengeShortResponseDTO getShortChallenge(Long userId){
        Optional<User> getUser = userRepository.findById(userId);
        User user = getUser.get();
        Long days = ChronoUnit.DAYS.between(user.getCreateDate(), LocalDate.now());
        List<Participant> userParticipant = participantRepository.findByUserId(user);

        int userPlantedTree = 0;
        HashSet<TreeInstance> userPlantedTreeInstance = new HashSet<>();

        for(Participant participant : userParticipant){
            List<DonationLog> userDonationList = donationLogRepository.findByParticipantId(participant);
            for(DonationLog userDonationlog : userDonationList){
                Optional<TreeInstance> userDonatedTree = treeInstanceRepository.findById(userDonationlog.getTreeInstanceId().getTreeInstanceId());
                if(!userPlantedTreeInstance.contains(userDonatedTree)){
                    userPlantedTreeInstance.add(userDonatedTree.get());
                }
                for(TreeInstance treeInstance : userPlantedTreeInstance){
                    if(treeInstance.getFinishedDate() != null){
                        userPlantedTree++;
                    }
                }
            }
        }

        ChallengeShortResponseDTO challengeShortDTO = ChallengeShortResponseDTO.builder()
                .dayOfChallenge(Math.toIntExact(days))
                .amountOfTree(userPlantedTree)
                .build();

        return challengeShortDTO;
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
    @Transactional
    public ChallengeDetailResponseDTO getChallengeDetail(ChallengeDetailRequestDTO challengeDetailRequestDTO){

        User getUser = userRepository.findById(Long.valueOf(challengeDetailRequestDTO.getUserId())).get();
        Challenge getChallenge = challengeRepository.findById(Long.valueOf(challengeDetailRequestDTO.getChallengeId())).get();

        Participant getParticipant = participantRepository.findByUserIdAndChallengeId(getUser, getChallenge);

        List<DonationLog> userDonationList = donationLogRepository.findByParticipantId(getParticipant);
        Double userGotLeaf =0.0;
        Double userLeftLeaf =0.0;
        if(userDonationList != null){
            userGotLeaf = getParticipant.getTotalDistance() / getChallenge.getGoalDistance();
            userLeftLeaf = userGotLeaf - userDonationList.size();
        } else {
            userGotLeaf = getParticipant.getTotalDistance() / getChallenge.getGoalDistance();
            userLeftLeaf = userGotLeaf;
        }

        if(getParticipant.getLeafCount() == userLeftLeaf.intValue()){
            ChallengeDetailResponseDTO challengeDetailResponseDTO = ChallengeDetailResponseDTO.builder()
                    .current(getParticipant.getTotalDistance()/getChallenge.getGoalDistance())
                    .goalDistance(getChallenge.getGoalDistance())
                    .leafCount(userLeftLeaf.intValue())
                    .build();
            return challengeDetailResponseDTO;
        } else {
            throw new CustomException(ErrorCode.LEAFCOUNT_ERROR);
        }

    }

    @Transactional
    public List<ChallengeChartResponseDTO> getChallengeChart(ChallengeChartRequestDTO challengeChartRequestDTO){
        User user = userRepository.findById(challengeChartRequestDTO.getUserId()).orElseThrow();
        Challenge challenge = challengeRepository.findById(challengeChartRequestDTO.getChallengeId()).orElseThrow();
        String trans = challenge.getTransportation();

        LocalDate start = LocalDate.now().withDayOfMonth(1);
        LocalDate end = LocalDate.now();

        List<MovementLog> nowMonth = movementLogRepository.findByDayGreaterThanAndDayLessThanEqualAndTransportationAndUser(start, end, trans, user)
                .stream().map(Optional::orElseThrow).collect(Collectors.toList());
        List<MovementLog> lastMonth = movementLogRepository.findByDayGreaterThanAndDayLessThanEqualAndTransportationAndUser(start.minusMonths(1), start, trans, user)
                .stream().map(Optional::orElseThrow).collect(Collectors.toList());

        List<ChallengeChartResponseDTO> list = new ArrayList<>();

        Transportation transportation = Transportation.valueOf(trans);

        list.add(ChallengeChartResponseDTO.builder()
                .date(start.minusMonths(1).format(DateTimeFormatter.ofPattern("yyyy-MM")))
                .value(lastMonth.stream().mapToDouble(MovementLog::getDistance).sum() * transportation.getCost())
                .build());
        list.add(ChallengeChartResponseDTO.builder()
                .date(start.format(DateTimeFormatter.ofPattern("yyyy-MM")))
                .value(nowMonth.stream().mapToDouble(MovementLog::getDistance).sum() * transportation.getCost())
                .build());

        return list;
    }

}