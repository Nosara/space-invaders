package com.joremadriz.space_invaders.model.bullet;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
public class PlayerBullet extends BaseBullet{

    public PlayerBullet(){
        super(5, 10, Color.BLUE, BulletType.PLAYER);
    }
}
