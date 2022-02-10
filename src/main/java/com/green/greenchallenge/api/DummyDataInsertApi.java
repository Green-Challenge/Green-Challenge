package com.green.greenchallenge.api;

import com.green.greenchallenge.dto.AddRecordDTO;
import com.green.greenchallenge.service.DummyChallengeService;
import lombok.RequiredArgsConstructor;
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

    @PostMapping("/challenging/done")
    public void dummyAddRecord(@RequestBody AddRecordDTO addRecordDTO) {
        long duration = ChronoUnit.DAYS.between(LocalDate.of(2022, 1, 1), LocalDate.now());
        for (int i = 0; i < duration; i++) {

            double rand = Math.round((Math.random() * 5 + 1) * 1000.0) / 1000.0;

            dummyChallengeService.addRecord(AddRecordDTO.builder()
                    .userId(addRecordDTO.getUserId())
                    .challengeId(addRecordDTO.getChallengeId())
                    .achieved(rand).build(), LocalDate.of(2022, 1, 1).plusDays(i));
        }
    }
}
