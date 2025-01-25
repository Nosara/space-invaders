package com.joremadriz.space_invaders.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Builder
public class ObjectPosition {
    private int x;
    private int y;
}
