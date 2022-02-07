package com.green.greenchallenge.api;

import com.green.greenchallenge.dto.*;
import com.green.greenchallenge.service.ChallengeService;
import com.green.greenchallenge.service.ParticipantService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/challenge", produces = "application/json; charset=utf8")
@RequiredArgsConstructor
public class ChallengeApi {
    private final ChallengeService challengeService;
    private final ParticipantService participantService;

    @GetMapping("/{challengeId}")
    public ResponseEntity<ChallengeResponseDTO> getChallenge(@PathVariable Long challengeId) {
        return new ResponseEntity(challengeService.getChallenge(challengeId), HttpStatus.OK);//참여자수 처리 필요
    }

    @PostMapping("")
    public ResponseEntity joinChallenge(@RequestBody ChallengeJoinRequestDTO challengeJoinRequestDTO){
        participantService.createParticipant(challengeJoinRequestDTO);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PostMapping("/challenging/done")
    public void addRecord(@RequestBody AddRecordDTO addRecordDTO) {
        challengeService.addRecord(addRecordDTO);
    }

    @GetMapping("/list/{userId}")
    public ResponseEntity<ChallengeListResponseDTO> getUserChallengeList(@PathVariable Long userId){
        return new ResponseEntity(challengeService.getUserChallengeList(userId), HttpStatus.OK);
    }

    @GetMapping("/short/{userId}")
    public ResponseEntity shortChallenge (@PathVariable Long userId){
        return new ResponseEntity(challengeService.getShortChallenge(userId), HttpStatus.OK);
    }

    @PostMapping("/challenging/detail")
    public ResponseEntity detailChallenge(@RequestBody ChallengeDetailRequestDTO challengeDetailRequestDTO){
        return new ResponseEntity(challengeService.getChallengeDetail(challengeDetailRequestDTO), HttpStatus.OK);
    }

    @GetMapping("/tree/{challengeId}")
    public ResponseEntity<ChallengeTreeGrowthDTO> getChallengeTreeGrowth (@PathVariable Long challengeId) {
        return new ResponseEntity(challengeService.getChallengeTreeGrowth(challengeId), HttpStatus.OK);
    }

}