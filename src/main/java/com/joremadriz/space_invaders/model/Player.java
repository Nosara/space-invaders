package com.joremadriz.space_invaders.model;

import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
public class Player {
    private int x, y, width, height;

    public Player(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = 50;
        this.height = 20;
    }

    public void moveLeft() { x -= 5; }
    public void moveRight() { x += 5; }

    public void draw(Graphics g) {
        g.setColor(Color.GREEN);
        g.fillRect(x, y, width, height);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }
}