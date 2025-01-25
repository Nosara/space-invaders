package com.joremadriz.space_invaders.model.enemy;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Setter
@Getter
public class RareEnemy extends BaseEnemy {

    public RareEnemy() {
        super(40, 20, 3, Color.YELLOW, EnemyType.RARE);
    }
}
