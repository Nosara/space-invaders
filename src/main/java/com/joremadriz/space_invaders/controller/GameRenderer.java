package com.joremadriz.space_invaders.controller;

import com.joremadriz.space_invaders.common.GameState;
import com.joremadriz.space_invaders.model.Player;
import com.joremadriz.space_invaders.model.bullet.Bullet;
import com.joremadriz.space_invaders.model.enemy.Enemy;

import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;


public class GameRenderer {

    public static void render(Graphics g, GameState gameState, Player player, List<Enemy> enemies, List<Bullet> bullets, JPanel panel) {
        switch (gameState) {
            case MENU -> drawMenu(g, panel);
            case PLAYING -> drawGame(g, player, enemies, bullets, panel);
            case GAME_OVER -> drawGameOver(g, panel);
        }
    }

    private static void drawMenu(Graphics g, JPanel panel) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, panel.getWidth(), panel.getHeight());

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 36));
        g.drawString("SPACE INVADERS", 250, 200);

        g.setFont(new Font("Arial", Font.PLAIN, 24));
        g.drawString("Press ENTER to Start", 280, 300);
    }

    private static void drawGame(Graphics g, Player player, List<Enemy> enemies, List<Bullet> bullets, JPanel panel) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, panel.getWidth(), panel.getHeight());

        // Draw player
        if (player != null) {
            player.draw(g);
        }

        // Draw bullets
        bullets.forEach(bullet -> bullet.draw(g));

        // Draw enemies
        enemies.forEach(enemy -> enemy.draw(g));
    }

    private static void drawGameOver(Graphics g, JPanel panel) {
        g.setColor(Color.RED);
        g.fillRect(0, 0, panel.getWidth(), panel.getHeight());

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 36));
        g.drawString("GAME OVER!", 250, 200);
        g.setFont(new Font("Arial", Font.PLAIN, 24));
        g.drawString("Press ENTER to return to Menu", 280, 300);
    }
}
