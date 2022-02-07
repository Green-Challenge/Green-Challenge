package com.green.greenchallenge.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.green.greenchallenge.domain.Challenge;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class TreeResponseDTO {
    private String treeName;
}
