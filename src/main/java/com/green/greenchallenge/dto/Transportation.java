package com.green.greenchallenge.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Transportation {
    /*
    gram / km
     */
    CAR("car", 0.0),
    BUS("bus", 96.9),
    WALK("walk", 147.5),
    SUBWAY("subway", 113.9),
    KICKBOARD("kickboard", 21.5),
    BIKE("bike", 147.5);

    private String name;
    private double cost;
}
