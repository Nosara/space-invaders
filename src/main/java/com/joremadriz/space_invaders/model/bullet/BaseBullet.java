package com.joremadriz.space_invaders.model.bullet;

import com.joremadriz.space_invaders.model.ObjectPosition;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Getter
@Setter
public abstract class BaseBullet implements Bullet {
    private int width;
    private int height;
    private ObjectPosition position;
    private Color color;
    private BulletType type;

    public BaseBullet(int width, int height, Color color, BulletType type) {
        this.width = width;
        this.height = height;
        this.color = color;
        this.type = type;
    }


    @Override
    public void update() {
        System.out.println("Updating bullet");
        int y = getPosition().getY();
        y = type == BulletType.ENEMY ? y + 5 : y - 5;
        getPosition().setY(y);
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(this.getColor());
        g.fillRect(getPosition().getX(), getPosition().getY(), width, height);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(getPosition().getX(), getPosition().getY(), width, height);
    }
}
