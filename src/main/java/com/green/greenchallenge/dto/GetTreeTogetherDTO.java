package com.green.greenchallenge.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class GetTreeTogetherDTO {

    private Long challengeId;
    private String challengeName;
    private int numberOfCompletions;
    private Long treeId;
    private int numberOfLeaf;
}
