package com.joremadriz.space_invaders.model.enemy;

import com.joremadriz.space_invaders.model.ObjectPosition;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;

@Getter
@Setter
@Slf4j
public abstract class BaseEnemy implements Enemy {

    private int width;
    private int height;
    private int lifePoints;
    private boolean movingRight = true;
    private ObjectPosition position;
    private Long lastBulletShoot;
    private Color color;
    private EnemyType type;

    public BaseEnemy(int width, int height, int lifePoints, Color color, EnemyType type) {
        this.width = width;
        this.height = height;
        this.lifePoints = lifePoints;
        this.color = color;
        this.type = type;
    }

    @Override
    public void draw(Graphics g) {
        g.setColor(this.color);
        g.fillRect(this.position.getX(), this.position.getY(), this.width, this.height);
    }

    @Override
    public void update() {
        int x = getPosition().getX();
        int y = getPosition().getY();

        x += movingRight ? 2 : -2;
        if (x <= 0 || x + width >= 800) {
            movingRight = !movingRight;
            y += 20;
        }

        getPosition().setX(x);
        getPosition().setY(y);
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle(this.position.getX(), this.position.getY(), this.width, this.height);
    }

    @Override
    public int reduceLifePoints() {
        log.info("START: Reducing enemy {} lifePoints.", this.getType());
        log.info("Reducing lifePoints from {}", this.getLifePoints());
        this.setLifePoints(getLifePoints() - 1);
        log.info("lifePoints reduced to {}.", this.getLifePoints());
        log.info("END: Reducing enemy {} lifePoints.", this.getType());
        return this.getLifePoints();
    }
}
