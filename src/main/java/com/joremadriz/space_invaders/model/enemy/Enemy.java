package com.joremadriz.space_invaders.model.enemy;

import com.joremadriz.space_invaders.model.ObjectPosition;

import java.awt.*;

public interface Enemy {
     void draw(Graphics g);
     void update();
     Rectangle getBounds();
     void setPosition(ObjectPosition position);
     ObjectPosition getPosition();
     int reduceLifePoints();
     void setLastBulletShoot(Long timestamp);
     Long getLastBulletShoot();
     EnemyType getType();

}
