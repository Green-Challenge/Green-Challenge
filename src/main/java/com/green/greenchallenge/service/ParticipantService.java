package com.green.greenchallenge.service;

import com.green.greenchallenge.domain.Participant;
import com.green.greenchallenge.repository.ParticipantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ParticipantService {

    private ParticipantRepository participantRepository;

    @Transactional
    public List<Participant> getUserParticipant(Long userId){
        return null;
    }

}
