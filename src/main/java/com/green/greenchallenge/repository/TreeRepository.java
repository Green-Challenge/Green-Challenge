package com.green.greenchallenge.repository;

import com.green.greenchallenge.domain.Tree;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TreeRepository extends JpaRepository<Tree, Long> {
}
