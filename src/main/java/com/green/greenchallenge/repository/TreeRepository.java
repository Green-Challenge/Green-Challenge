package com.green.greenchallenge.repository;

import com.green.greenchallenge.domain.Challenge;
import com.green.greenchallenge.domain.Tree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TreeRepository extends JpaRepository<Tree, Long> {
    List<Tree> findByChallengeId(Challenge challenge);
}
