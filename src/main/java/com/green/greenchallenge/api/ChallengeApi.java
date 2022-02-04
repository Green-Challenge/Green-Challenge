package com.green.greenchallenge.api;

import com.green.greenchallenge.dto.ChallengeJoinRequestDTO;
import com.green.greenchallenge.dto.ChallengeResponseDTO;
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

}