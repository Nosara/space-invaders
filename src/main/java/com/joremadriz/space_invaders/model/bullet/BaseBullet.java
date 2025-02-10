package com.joremadriz.space_invaders.model.bullet;

import com.joremadriz.space_invaders.model.ObjectPosition;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;

@Getter
@Setter
@Slf4j
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
        log.info("START: Updating bullet's position from {}.", this.getType());
        int oldYPosition = getPosition().getY();
        int newYPosition = type == BulletType.ENEMY ? oldYPosition + 5 : oldYPosition - 5;
        getPosition().setY(newYPosition);
        log.info("Update bullet position from {} to {}.", oldYPosition, newYPosition);
        log.info("END: Updating bullet's position from {}.", this.getType());
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
