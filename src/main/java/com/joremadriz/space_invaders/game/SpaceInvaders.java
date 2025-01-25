package com.joremadriz.space_invaders.game;


import javax.swing.*;

public class SpaceInvaders extends JFrame {
    public SpaceInvaders() {
        setTitle("Space Invaders");
        setSize(800, 600); // Set resolution
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        add(new GameCanvas());
        setVisible(true);
    }

    public static void main(String[] args) {
        new SpaceInvaders();
    }
}