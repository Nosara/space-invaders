package com.joremadriz.space_invaders.game;

import com.joremadriz.space_invaders.common.GameState;
import com.joremadriz.space_invaders.controller.CollisionDetector;
import com.joremadriz.space_invaders.controller.GameRenderer;
import com.joremadriz.space_invaders.model.Player;
import com.joremadriz.space_invaders.model.bullet.Bullet;
import com.joremadriz.space_invaders.model.bullet.BulletFactory;
import com.joremadriz.space_invaders.model.bullet.BulletType;
import com.joremadriz.space_invaders.model.ObjectPosition;
import com.joremadriz.space_invaders.model.enemy.Enemy;
import com.joremadriz.space_invaders.model.enemy.EnemyFactory;
import com.joremadriz.space_invaders.model.enemy.EnemyType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class GameCanvas extends JPanel implements Runnable, KeyListener {
    private GameState gameState = GameState.MENU;
    private boolean running = true;
    private Player player;
    private List<Enemy> enemies;
    private  List<Bullet> bullets;

    public GameCanvas() {
        addKeyListener(this);
        setFocusable(true);
        new Thread(this).start();
    }

    @Override
    public void run() {
        while (running) {
            if (gameState == GameState.PLAYING) {
                updateGame();
            }
            repaint();
            try {
                Thread.sleep(16); // ~60 FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateGame() {
        this.addNewEnemyBullets(enemies);
        enemies.forEach(Enemy::update);
        bullets.forEach(Bullet::update);
        this.checkCollisions();

    }

    private void addNewEnemyBullets(List<Enemy> enemies){
        enemies.stream().filter(e -> e.getType() != EnemyType.COMMON).forEach(
                e ->
                {
                    if(this.addBullet(e.getType(), e.getLastBulletShoot(), e.getPosition())){
                        e.setLastBulletShoot(System.currentTimeMillis());
                    }
                }
        );
    }

    private boolean addBullet(EnemyType type, Long lastBulletShoot, ObjectPosition position){
        if(System.currentTimeMillis()- lastBulletShoot >= type.getShootFrequency()){
            bullets.add(BulletFactory.createBullet(BulletType.ENEMY, ObjectPosition.builder().x(position.getX() + 22).y(position.getY()).build()));
            return true;
        }

        return false;
    }

    private void checkCollisions() {
        if (CollisionDetector.checkPlayerEnemyCollision(player, enemies) ||
                CollisionDetector.checkPlayerBulletCollision(player, bullets)) {
            gameState = GameState.GAME_OVER;
        }

        CollisionDetector.checkBulletEnemyCollisions(bullets, enemies);
        CollisionDetector.checkBulletsCollisions(bullets);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        GameRenderer.render(g, gameState, player, enemies, bullets, this);
    }

    public void startGame() {
        player = new Player(375, 550);
        enemies = new ArrayList<>();
        bullets = new ArrayList<>();

        Random random = new Random();

//        for (int row = 0; row < 5; row++) {
//            for (int col = 0; col < 10; col++) {
//                EnemyType randomEnemyType = EnemyType.values()[random.nextInt(EnemyType.values().length)];
//                enemies.add(EnemyFactory.createEnemy( randomEnemyType, ObjectPosition.builder().x(50 + col * 60).y(50 + row * 40 ).build()));
//            }
//        }

        enemies.add(EnemyFactory.createEnemy(EnemyType.EPIC, ObjectPosition.builder().x(50+60).y(50+40).build()));
        enemies.add(EnemyFactory.createEnemy(EnemyType.UNCOMMON, ObjectPosition.builder().x(50+(60*2)).y(50+40).build()));
        enemies.add(EnemyFactory.createEnemy(EnemyType.COMMON, ObjectPosition.builder().x(50+(60*3)).y(50+40).build()));
        enemies.add(EnemyFactory.createEnemy(EnemyType.RARE, ObjectPosition.builder().x(50+(60*4)).y(50+40).build()));
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (gameState == GameState.MENU) {
            if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                startGame();
                gameState = GameState.PLAYING;
            }
        } else if (gameState == GameState.PLAYING) {
            if (e.getKeyCode() == KeyEvent.VK_LEFT) player.moveLeft();
            if (e.getKeyCode() == KeyEvent.VK_RIGHT) player.moveRight();
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {

                bullets.add(BulletFactory.createBullet(BulletType.PLAYER, ObjectPosition.builder().x(player.getX() + 22).y(player.getY()).build()));
            }
        } else if (gameState == GameState.GAME_OVER) {
            if(e.getKeyCode() == KeyEvent.VK_ENTER ){
                gameState = GameState.MENU;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void keyTyped(KeyEvent e) {}
}
