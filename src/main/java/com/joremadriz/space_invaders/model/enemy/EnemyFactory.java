package com.joremadriz.space_invaders.model.enemy;

import com.joremadriz.space_invaders.model.ObjectPosition;

import java.util.HashMap;
import java.util.Map;

public class EnemyFactory {
    private static final Map<EnemyType, Class<? extends Enemy>> registeredEnemies = new HashMap<>();

    static {
        registeredEnemies.put(EnemyType.COMMON, CommonEnemy.class);
        registeredEnemies.put(EnemyType.UNCOMMON, UncommonEnemy.class);
        registeredEnemies.put(EnemyType.RARE, RareEnemy.class);
        registeredEnemies.put(EnemyType.EPIC, EpicEnemy.class);
    }

    public static Enemy createEnemy(EnemyType enemyType, ObjectPosition objectPosition) {
        Class<? extends Enemy> enemyClass = registeredEnemies.get(enemyType);
        if(enemyClass == null) {
            throw new IllegalArgumentException("Unknown enemy type: " + enemyType);
        }

        try{
            Enemy enemy = enemyClass.getDeclaredConstructor().newInstance();
            enemy.setPosition(objectPosition);
            enemy.setLastBulletShoot(System.currentTimeMillis());
            return enemy;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create enemy instance. ", e);
        }
    }
}
