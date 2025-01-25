package com.joremadriz.space_invaders.controller;

import com.joremadriz.space_invaders.common.GameState;
import com.joremadriz.space_invaders.model.bullet.Bullet;
import com.joremadriz.space_invaders.model.bullet.EnemyBullet;
import com.joremadriz.space_invaders.model.bullet.PlayerBullet;
import com.joremadriz.space_invaders.model.enemy.Enemy;

import java.awt.*;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class CollisionDetector {

    public Optional<Boolean> checkBulletCollisionWithPlayer(List<Bullet> bullets, Rectangle playerBounds){
       return bullets.stream().map(b -> b.getBounds().intersects(playerBounds)).findFirst();
    }


//    private void checkCollisionsPlayerEnemies(List<Bullet> bullets) {
//        for (int i = 0; i < bullets.size(); i++) {
//            Bullet bullet = bullets.get(i);
//            for (int j = 0; j < enemies.size(); j++) {
//                Enemy enemy = enemies.get(j);
//                if(enemy.getBounds().intersects(player.getBounds())) {
//                    gameState = GameState.GAME_OVER;
//                }
//                if (bullet.getClass() == PlayerBullet.class && bullet.getBounds().intersects(enemy.getBounds())) {
//
//                    bullets.remove(i);
//                    if(
//                            enemies.get(j).reduceLifePoints() <= 0
//                    ) {
//                        enemies.remove(j);
//                    }
//
//                    i--;
//                    break;
//                }
//
//                if(bullet.getClass() == EnemyBullet.class && bullet.getBounds().intersects(player.getBounds())){
//                    gameState = GameState.GAME_OVER;
//                }
//
//                if (bullet.getBounds().y > player.getBounds().y){
//                    bullets.remove(i);
//                    i--;
//                    break;
//                }
//            }
//        }
//
//        checkBulletsCollisions(bullets);
//    }


    private List<Bullet> checkBulletsCollisions(List<Bullet> bullets) {
        List<Bullet> enemyBullets = bullets.stream()
                .filter(EnemyBullet.class::isInstance)
                .toList();

        List<Bullet> playerBullets = bullets.stream()
                .filter(PlayerBullet.class::isInstance)
                .toList();

        Set<Bullet> bulletsToRemove = new HashSet<>();

        for (Bullet eb : enemyBullets) {
            for (Bullet pb : playerBullets) {
                if (pb.getBounds().intersects(eb.getBounds())) {
                    bulletsToRemove.add(eb);
                    bulletsToRemove.add(pb);
                }
            }
        }

        bullets.removeAll(bulletsToRemove);

        return bullets;
    }
}
