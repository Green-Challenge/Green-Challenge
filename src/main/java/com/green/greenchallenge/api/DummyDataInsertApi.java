package com.green.greenchallenge.api;

import com.green.greenchallenge.dto.AddRecordDTO;
import com.green.greenchallenge.dto.ChallengeJoinRequestDTO;
import com.green.greenchallenge.dto.DummyDTO;
import com.green.greenchallenge.dto.UserDTO;
import com.green.greenchallenge.service.ChallengeService;
import com.green.greenchallenge.service.DummyChallengeService;
import com.green.greenchallenge.service.ParticipantService;
import com.green.greenchallenge.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@RestController
@RequestMapping(value = "/api/dummy", produces = "application/json; charset=utf8")
@RequiredArgsConstructor
public class DummyDataInsertApi {
    private final DummyChallengeService dummyChallengeService;
    private final UserService userService;
    private final ParticipantService participantService;

    @PostMapping("/challenging/done")
    public void dummyAddRecord(@RequestBody DummyDTO dummyDTO) {
        long duration = ChronoUnit.DAYS.between(LocalDate.of(dummyDTO.getYear(), dummyDTO.getMonth(), dummyDTO.getDay()), LocalDate.now());
        for (int i = 0; i < duration; i++) {

            double rand = Math.round((Math.random() * dummyDTO.getRange() + dummyDTO.getDefaultDistance()) * 1000.0) / 1000.0;

            dummyChallengeService.addRecord(AddRecordDTO.builder()
                    .userId(dummyDTO.getUserId())
                    .challengeId(dummyDTO.getChallengeId())
                    .achieved(rand).build(), LocalDate.of(dummyDTO.getYear(), dummyDTO.getMonth(), dummyDTO.getDay()).plusDays(i));
        }
    }

    @PostMapping("/auth")
    public void dummyCreateUser(@RequestBody DummyDTO dummyDTO) {
        for (int i = 0; i < dummyDTO.getHowMany(); i++) {
            userService.createUser(UserDTO.builder()
                    .name("dummyName" + i)
                    .email("dummy" + i + "@example.com")
                    .password("password")
                    .createDate(LocalDate.now())
                    .build());
        }
    }

    @PostMapping("/challenge")
    public void dummyJoinChallenge(@RequestBody DummyDTO dummyDTO) {
        for (int i = dummyDTO.getUserIdRangeStart(); i <= dummyDTO.getUserIdRangeEnd(); i++) {
            participantService.createParticipant(ChallengeJoinRequestDTO.builder()
                    .userId((long) i)
                    .challengeId(dummyDTO.getChallengeId()).build());
        }
    }
}
