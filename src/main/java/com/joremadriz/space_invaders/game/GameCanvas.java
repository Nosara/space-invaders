package com.joremadriz.space_invaders.game;

import com.joremadriz.space_invaders.common.GameState;
import com.joremadriz.space_invaders.model.Player;
import com.joremadriz.space_invaders.model.bullet.Bullet;
import com.joremadriz.space_invaders.model.bullet.BulletFactory;
import com.joremadriz.space_invaders.model.bullet.BulletType;
import com.joremadriz.space_invaders.model.bullet.EnemyBullet;
import com.joremadriz.space_invaders.model.bullet.PlayerBullet;
import com.joremadriz.space_invaders.model.ObjectPosition;
import com.joremadriz.space_invaders.model.enemy.Enemy;
import com.joremadriz.space_invaders.model.enemy.EnemyFactory;
import com.joremadriz.space_invaders.model.enemy.EnemyType;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

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
        for (int i = 0; i < bullets.size(); i++) {
            Bullet bullet = bullets.get(i);
            for (int j = 0; j < enemies.size(); j++) {
                Enemy enemy = enemies.get(j);
                if(enemy.getBounds().intersects(player.getBounds())) {
                    gameState = GameState.GAME_OVER;
                }
                if (bullet.getClass() == PlayerBullet.class && bullet.getBounds().intersects(enemy.getBounds())) {

                    bullets.remove(i);
                    if(
                            enemies.get(j).reduceLifePoints() <= 0
                    ) {
                        enemies.remove(j);
                    }

                    i--;
                    break;
                }

                if(bullet.getClass() == EnemyBullet.class && bullet.getBounds().intersects(player.getBounds())){
                    gameState = GameState.GAME_OVER;
                }

                if (bullet.getBounds().y > player.getBounds().y){
                    bullets.remove(i);
                    i--;
                    break;
                }
            }
        }

        checkBulletsCollisions();
    }


    private void checkBulletsCollisions() {
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
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (gameState == GameState.MENU) {
            drawMenu(g);
        } else if (gameState == GameState.PLAYING) {
            drawGame(g);
        } else if( gameState == GameState.GAME_OVER) {
            drawGameOver(g);
        }
    }

    private void drawMenu(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 36));
        g.drawString("SPACE INVADERS", 250, 200);

        g.setFont(new Font("Arial", Font.PLAIN, 24));
        g.drawString("Press ENTER to Start", 280, 300);
    }

    private void drawGame(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        player.draw(g);
        bullets.forEach(bullet -> bullet.draw(g));

        enemies.forEach(enemy -> enemy.draw(g));
    }

    private void drawGameOver(Graphics g){
        g.setColor(Color.RED);
        g.fillRect(0, 0, getWidth(), getHeight());

        g.setColor(Color.WHITE);
        g.setFont(new Font("Ariel", Font.BOLD, 36));
        g.drawString("GAME OVER!", 250, 200);
        g.setFont(new Font("Arial", Font.PLAIN, 24));
        g.drawString("Press ENTER to return to Menu", 280, 300);
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
