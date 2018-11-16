package goty.logic.gameobjects.projectiles;

import goty.logic.gameobjects.Player;
import goty.logic.gameobjects.enemies.Enemy;
import javafx.scene.paint.Color;
import static goty.controllers.GameController.canvasWidth;

/**
 * <h1>Rotating Projectile</h1>
 * <p>Klasse som setter verdier og tar inn bilder til Angry Boss sine prosjektiler</p>
 *
 * @author Ole Østvold
 * @version 1.0
 * @since 29. april 2018
 */

public class RotatingProjectile extends Projectile {

    /** X-koordinatet som prosjektilene roterer rundt */
    private double centerX;
    /** Y-koordinat som prosjektilene roterer rundt */
    private double centerY;

    /** Justerer skaden til disse prosjektilene. Høyere verdi gir mindre skade */
    private int damageAdjustment = 5;
    
    private final double RADIUS = canvasWidth/25.0;
    private final double ROTATION_SPEED = 0.2;

    /**
     * Konstruerer et prosjektil.
     * @param player Spillerobjektet som prosjektilet har retning mot.
     * @param enemy Fienden som prosjektilet kommer fra.
     */
    public RotatingProjectile(Player player, Enemy enemy) {

        this.x = enemy.getX() + enemy.getWidth() * 0.5;
        this.y = enemy.getY() + enemy.getWidth() * 0.3;

        centerX = x;
        centerY = y;

        direction = calculateDirection(x, y, player.getX(), player.getY());

        damage = 1;
        lifeSpan = 300;
        width = canvasWidth * 0.006;
        height = canvasWidth * 0.006;
        speed = canvasWidth * 0.002;
        paint = Color.RED;
    }
        
    @Override
    public void update() {
            
        x = centerX;
        y = centerY;
        
        x += direction.getX() * speed;
        y += direction.getY() * speed;
        
        centerX = x;
        centerY = y;
        
        x += Math.sin(age * ROTATION_SPEED) * RADIUS;
        y += Math.cos(age * ROTATION_SPEED) * RADIUS;
        
        if (age % damageAdjustment == 0) damage = 1;
        if (age % damageAdjustment == 1) damage = 0;
        
        age++;
    }
}
