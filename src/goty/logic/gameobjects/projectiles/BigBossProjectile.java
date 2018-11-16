package goty.logic.gameobjects.projectiles;

import goty.logic.gameobjects.Player;
import goty.logic.gameobjects.enemies.Enemy;
import java.io.BufferedInputStream;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import static goty.controllers.GameController.canvasWidth;

/**
 * <h1>Big Boss Projectile</h1>
 * <p>Klasse som setter verdier og tar inn bilder til Big Boss sine prosjektiler</p>
 *
 * @author Ole Østvold
 * @version 1.0
 * @since 29. april 2018
 */

public class BigBossProjectile extends Projectile {
    
    private static Image image = new Image (new BufferedInputStream(BigBossProjectile.class.getClassLoader().getResourceAsStream("Sprites/Enemies/enemysmall_0.png")));
    private static ImagePattern imagePattern = new ImagePattern(image, 0, 0, 2, 1, true);
    
    /**
     * Konstruerer et prosjektil.
     * @param player Spillerobjektet som prosjektilet har retning mot.
     * @param enemy Fienden som prosjektilet kommer fra.
     */
    public BigBossProjectile(Player player, Enemy enemy) {
        
        this.x = enemy.getX() + enemy.getWidth()/2;
        this.y = enemy.getY() + enemy.getHeight()*0.6;
        
        direction = calculateDirection(x, y, player.getX(), player.getY());
        
        width = canvasWidth * 0.035;
        height = canvasWidth * 0.035;
        speed = canvasWidth * 0.01;
        damage = 3;
        paint = imagePattern;
        lifeSpan = 120;
    }

}
