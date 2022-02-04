package com.green.greenchallenge.service;

import com.green.greenchallenge.domain.*;
import com.green.greenchallenge.dto.GetTreeTogetherDTO;
import com.green.greenchallenge.dto.UserDTO;
import com.green.greenchallenge.exception.CustomException;
import com.green.greenchallenge.exception.ErrorCode;
import com.green.greenchallenge.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyService {

    private final UserRepository userRepository;
    private final ParticipantRepository participantRepository;
    private final TreeInstanceRepository treeInstanceRepository;
    private final TreeRepository treeRepository;

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
    public ArrayList<GetTreeTogetherDTO> getTreeTogether(User user) {
        List<Participant> participants = participantRepository.findByUserId(user);
        ArrayList<GetTreeTogetherDTO> getTreeTogethers = new ArrayList<>();

        if(participants.isEmpty()) {
            throw new CustomException(ErrorCode.PARTICIPANT_EMPTY);
        }

        for (int i = 0; i < participants.size(); i++) {

            int numberOfCompletions = 0;
            List<TreeInstance> instances = treeInstanceRepository.findByChallengeId(participants.get(i).getChallengeId());
            for(int j = 0; j < instances.size(); j++) {
                if(instances.get(j).getFinishedDate() != null) {
                    numberOfCompletions++;
                }
            }

            if(treeRepository.findByChallengeId(participants.get(i).getChallengeId()).isEmpty()) {
                throw new CustomException(ErrorCode.TREE_EMPTY);
            }

            Long treeId = treeRepository.findByChallengeId(participants.get(i).getChallengeId()).get(0).getTreeId();

            GetTreeTogetherDTO togetherDTO = new GetTreeTogetherDTO(
                    participants.get(i).getChallengeId().getChallengeId(),
                    participants.get(i).getChallengeId().getChallengeName(),
                    numberOfCompletions,
                    treeId,
                    (int)(participants.get(i).getTotalDistance() / participants.get(i).getChallengeId().getGoalDistance())
            );

            getTreeTogethers.add(togetherDTO);

            numberOfCompletions = 0;
        }

        return getTreeTogethers;
    }
}
