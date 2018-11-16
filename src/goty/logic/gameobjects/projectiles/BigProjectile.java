package goty.logic.gameobjects.projectiles;

import goty.logic.gameobjects.Player;
import goty.logic.gameobjects.enemies.Enemy;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.Paint;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import static goty.controllers.GameController.canvasWidth;

/**
 * <h1>Big Projectile</h1>
 * <p>Klasse som setter verdier og tar inn bilder til BigEnemy sine prosjektiler</p>
 *
 * @author Ole Østvold
 * @version 1.0
 * @since 29. april 2018
 */

public class BigProjectile extends Projectile {
    
    private static final Paint GRADIENT = new RadialGradient(
            0, 0, 0.5, 0.5, 0.5, true, CycleMethod.NO_CYCLE,
            new Stop(0, new Color(0,0,0,0.7)), new Stop(1, new Color(1,0,0,0.7)));
    
    /**
     * Konstruerer et prosjektil.
     * @param player Spillerobjektet som prosjektilet har retning mot.
     * @param enemy Fienden som prosjektilet kommer fra.
     */
    public BigProjectile(Player player, Enemy enemy) {
        
        this.x = enemy.getX() + enemy.getWidth()/2;
        this.y = enemy.getY() + enemy.getHeight()/2;
        
        direction = calculateDirection(x, y, player.getX(), player.getY());       
        paint = GRADIENT;
        damage = 10;
        width = canvasWidth * 0.02;
        height = canvasWidth * 0.02;
        speed = canvasWidth * 0.01;
        lifeSpan = 180;
    }

}
