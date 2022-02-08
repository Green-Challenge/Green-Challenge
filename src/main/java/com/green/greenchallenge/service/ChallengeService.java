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
            if(challenge.getFinishDate().isAfter(LocalDate.now())){
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
        Optional<Challenge> challenge = challengeRepository.findById(addRecordDTO.getChallengeId());
        Optional<User> user = userRepository.findById(addRecordDTO.getUserId());
        Participant participant = participantRepository.findByUserIdAndChallengeId(user.get(), challenge.get());
        double challengeGoalDistance = challenge.get().getGoalDistance();
        int addLeaves =  ((int) ((participant.getTotalDistance() + addRecordDTO.getAchieved()) / challengeGoalDistance)) - ((int) (participant.getTotalDistance() / challengeGoalDistance));

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
                                .donationDate(LocalDate.now())
                                .build()
                );

            } else { // 시작할 인스턴스가 있는 경우 해당 인스턴스에 값을 저장한다.
                treeInstance.get().setNumberOfLeaf(treeInstance.get().getNumberOfLeaf() + 1); // 나무인스턴스의 모인나뭇잎 수를 1 증가시킨다.
                donationLogRepository.save( // 해당 나무인스턴스의 기부로그를 추가한다.
                        DonationLog.builder()
                                .treeInstanceId(treeInstance.get())
                                .participantId(participant)
                                .donationDate(LocalDate.now())
                                .build()
                );
                if (treeInstance.get().getNumberOfLeaf() == challenge.get().getGoalLeaves()) {
                    // 만약 모인 나뭇잎수가 챌린지의 목표 나뭇잎수에 도달할 경우 해당 인스턴스를 종료하고, 새로운 인스턴스를 생성한다.
                    // 나무인스턴스가 완료되고 새로운 나무인스턴스가 생성된다.
                    treeInstance.get().setFinishedDate(LocalDate.now());
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
                        .day(LocalDate.now())
                        .transportation(challenge.get().getTransportation())
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
    public ChallengeTreeGrowthDTO getChallengeTreeGrowth(Long challengeId) {
        Optional<Challenge> challenge = challengeRepository.findById(challengeId);
        List<TreeInstance> treeInstances = treeInstanceRepository.findByChallengeId(challenge.get());
        ChallengeTreeGrowthDTO challengeTreeGrowthDTO = new ChallengeTreeGrowthDTO();
        for (TreeInstance treeInstance : treeInstances) {
            if (treeInstance.getFinishedDate() == null) {
                challengeTreeGrowthDTO.setNumberOfLeaf(treeInstance.getNumberOfLeaf());
                if(treeInstance.getNumberOfLeaf() < challenge.get().getGoalLeaves() / 3) {
                    challengeTreeGrowthDTO.setTreeGrowth(1);
                    challengeTreeGrowthDTO.setTreeId(treeRepository.findByChallengeIdAndTreeGrowth(challenge.get(), 1).getTreeId());
                } else if (treeInstance.getNumberOfLeaf() < challenge.get().getGoalLeaves() * 2 / 3) {
                    challengeTreeGrowthDTO.setTreeGrowth(2);
                    challengeTreeGrowthDTO.setTreeId(treeRepository.findByChallengeIdAndTreeGrowth(challenge.get(), 2).getTreeId());
                } else {
                    challengeTreeGrowthDTO.setTreeGrowth(3);
                    challengeTreeGrowthDTO.setTreeId(treeRepository.findByChallengeIdAndTreeGrowth(challenge.get(), 3).getTreeId());
                }
                break;
            }
        }

        return challengeTreeGrowthDTO;
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


    @Transactional
    public TodayRecordDTO getTodayRecord(TodayRecordDTO todayRecordDTO) {
        List<MovementLog> movementLogs = movementLogRepository.findByUserId(todayRecordDTO.getUserId())
                .stream().map(Optional::orElseThrow).collect(Collectors.toList());
        String transportation = challengeRepository.findById(todayRecordDTO.getChallengeId()).get().getTransportation();

        for(MovementLog movementLog : movementLogs) {
            if(movementLog.getTransportation().equals(transportation) && movementLog.getDay().equals(LocalDate.now())) {
                todayRecordDTO.setDistance(todayRecordDTO.getDistance() + movementLog.getDistance());
            }
        }
        todayRecordDTO.setReducedCarbon(Transportation.valueOf(transportation.toUpperCase(Locale.ROOT)).getCost() * todayRecordDTO.getDistance());

        return TodayRecordDTO.builder()
                .distance(todayRecordDTO.getDistance())
                .reducedCarbon(todayRecordDTO.getReducedCarbon())
                .build();
    }
}