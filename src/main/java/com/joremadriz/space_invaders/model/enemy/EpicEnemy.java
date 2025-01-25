package com.joremadriz.space_invaders.model.enemy;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
public class EpicEnemy extends BaseEnemy {

    public EpicEnemy() {
        super(40, 20, 3, Color.ORANGE, EnemyType.EPIC);
    }
}
