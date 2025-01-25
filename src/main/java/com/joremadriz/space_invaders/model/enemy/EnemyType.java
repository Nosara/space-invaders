package com.joremadriz.space_invaders.model.enemy;

import lombok.Getter;

@Getter
public enum EnemyType {
    COMMON(0L),
    UNCOMMON(1900L),
    RARE(900L),
    EPIC(500L);

    private final Long shootFrequency;

    EnemyType(Long shootFrequency){
        this.shootFrequency = shootFrequency;
    }
}
