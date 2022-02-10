package com.green.greenchallenge.api;

import com.green.greenchallenge.dto.AddRecordDTO;
import com.green.greenchallenge.dto.DummyAddRecordDTO;
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
    public void dummyAddRecord(@RequestBody DummyAddRecordDTO dummyAddRecordDTO) {
        long duration = ChronoUnit.DAYS.between(LocalDate.of(dummyAddRecordDTO.getYear(), dummyAddRecordDTO.getMonth(), dummyAddRecordDTO.getDay()), LocalDate.now());
        for (int i = 0; i < duration; i++) {

            double rand = Math.round((Math.random() * dummyAddRecordDTO.getDefaultDistance() + dummyAddRecordDTO.getRange()) * 1000.0) / 1000.0;

            dummyChallengeService.addRecord(AddRecordDTO.builder()
                    .userId(dummyAddRecordDTO.getUserId())
                    .challengeId(dummyAddRecordDTO.getChallengeId())
                    .achieved(rand).build(), LocalDate.of(dummyAddRecordDTO.getYear(), dummyAddRecordDTO.getMonth(), dummyAddRecordDTO.getDay()).plusDays(i));
        }
    }
}
