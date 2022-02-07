package com.green.greenchallenge.service;

import com.green.greenchallenge.domain.Tree;
import com.green.greenchallenge.dto.TreeResponseDTO;
import com.green.greenchallenge.exception.CustomException;
import com.green.greenchallenge.exception.ErrorCode;
import com.green.greenchallenge.repository.TreeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TreeService {

    private final TreeRepository treeRepository;

    @Transactional
    public TreeResponseDTO getTree(Long treeId){
        Optional<Tree> getTree = treeRepository.findById(treeId);
        if(getTree.isEmpty()) throw new CustomException(ErrorCode.TREE_EMPTY);
        Tree tree = getTree.get();
        TreeResponseDTO treeDTO = TreeResponseDTO.builder()
                .treeName(tree.getTreeName())
                .build();
        return treeDTO;
    }

}
