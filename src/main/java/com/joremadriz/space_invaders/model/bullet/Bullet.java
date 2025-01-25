package com.joremadriz.space_invaders.model.bullet;

import com.joremadriz.space_invaders.model.ObjectPosition;

import java.awt.*;

public interface Bullet {
    void setPosition(ObjectPosition position);
    void update();
    void draw(Graphics g);
    Rectangle getBounds();
}
