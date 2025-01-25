package com.joremadriz.space_invaders.view;

import com.joremadriz.space_invaders.model.enemy.BaseEnemy;
import com.joremadriz.space_invaders.model.enemy.Enemy;

import java.awt.*;

public interface Renderer {
    void draw (Enemy enemy, Graphics g);
}
