package com.joremadriz.space_invaders.model.bullet;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
public class EnemyBullet extends BaseBullet{

    public EnemyBullet(){
        super(5, 10, Color.RED, BulletType.ENEMY);
    }

}
