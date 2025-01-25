package com.joremadriz.space_invaders.model.enemy;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
public class UncommonEnemy extends BaseEnemy {
    public UncommonEnemy() {
        super(40, 20, 2, Color.gray, EnemyType.UNCOMMON);
    }
}