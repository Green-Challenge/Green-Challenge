package com.green.greenchallenge.repository;

import com.green.greenchallenge.domain.Challenge;
import com.green.greenchallenge.domain.TreeInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TreeInstanceRepository extends JpaRepository<TreeInstance, Long> {

    TreeInstance findByChallengeId(Long challengeId);

}
