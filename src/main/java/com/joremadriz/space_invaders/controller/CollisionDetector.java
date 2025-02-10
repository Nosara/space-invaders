package com.joremadriz.space_invaders.controller;

import com.joremadriz.space_invaders.model.Player;
import com.joremadriz.space_invaders.model.bullet.Bullet;
import com.joremadriz.space_invaders.model.bullet.EnemyBullet;
import com.joremadriz.space_invaders.model.bullet.PlayerBullet;
import com.joremadriz.space_invaders.model.enemy.Enemy;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CollisionDetector {

    public static boolean checkPlayerEnemyCollision(Player player, List<Enemy> enemies) {
        for (Enemy enemy : enemies) {
            if (enemy.getBounds().intersects(player.getBounds())) {
                return true; // Player collides with an enemy
            }
        }
        return false;
    }

    public static boolean checkPlayerBulletCollision(Player player, List<Bullet> bullets) {
        for (Bullet bullet : bullets) {
            if (bullet instanceof EnemyBullet && bullet.getBounds().intersects(player.getBounds())) {
                return true; // Player was hit
            }
        }
        return false;
    }

    public static void checkBulletEnemyCollisions(List<Bullet> bullets, List<Enemy> enemies) {
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            if (bullet instanceof PlayerBullet) {
                for (int j = 0; j < enemies.size(); j++) {
                    Enemy enemy = enemies.get(j);
                    if (bullet.getBounds().intersects(enemy.getBounds())) {
                        bullets.remove(i);
                        if (enemy.reduceLifePoints() <= 0) {
                            enemies.remove(j);
                        }
                        i--;
                        break;
                    }
                }
            }
        }
    }

    public static void checkBulletsCollisions(List<Bullet> bullets) {
        Set<Bullet> bulletsToRemove = new HashSet<>();

        List<Bullet> enemyBullets = bullets.stream().filter(EnemyBullet.class::isInstance).toList();
        List<Bullet> playerBullets = bullets.stream().filter(PlayerBullet.class::isInstance).toList();

        for (Bullet eb : enemyBullets) {
            for (Bullet pb : playerBullets) {
                if (pb.getBounds().intersects(eb.getBounds())) {
                    bulletsToRemove.add(eb);
                    bulletsToRemove.add(pb);
                }
            }
        }

        bullets.removeAll(bulletsToRemove);
    }
}