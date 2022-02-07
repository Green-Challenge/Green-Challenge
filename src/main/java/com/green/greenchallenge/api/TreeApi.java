package com.green.greenchallenge.api;

import com.green.greenchallenge.service.TreeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/tree", produces = "application/json; charset=utf8")
@RequiredArgsConstructor
public class TreeApi {

    private final TreeService treeService;

    @GetMapping("/{treeId}")
    public ResponseEntity getTree(@PathVariable Long treeId){
        return new ResponseEntity(treeService.getTree(treeId), HttpStatus.OK);
    }

}
