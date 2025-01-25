package com.joremadriz.space_invaders.model.bullet;

import com.joremadriz.space_invaders.model.ObjectPosition;

import java.util.HashMap;
import java.util.Map;

public class BulletFactory {

         private static final Map<BulletType, Class<? extends Bullet>> registeredBullets = new HashMap<>();

        static {

            registeredBullets.put(BulletType.ENEMY, EnemyBullet.class);
            registeredBullets.put(BulletType.PLAYER, PlayerBullet.class);
    }

        public static Bullet createBullet(BulletType bulletType, ObjectPosition objectPosition) {
        Class<? extends Bullet> bulletClass = registeredBullets.get(bulletType);
        if(bulletClass == null) {
            throw new IllegalArgumentException("Unknown enemy type: " + bulletType);
        }

        try{
            Bullet bullet = bulletClass.getDeclaredConstructor().newInstance();
            bullet.setPosition(objectPosition);
            return bullet;
        } catch (Exception e) {
            throw new RuntimeException("Failed to create bullet instance. ", e);
        }
    }
}