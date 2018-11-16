package goty.logic.gameobjects.projectiles;

import static goty.controllers.GameController.mouseX;
import static goty.controllers.GameController.mouseY;
import goty.logic.gameobjects.Player;
import javafx.scene.paint.Color;
import static goty.controllers.GameController.canvasWidth;
import goty.controllers.Gamepad;
import static goty.logic.gameobjects.GameObject.calculateDirection;

/**
 * <h1>Player Projectile</h1>
 * <p>Klasse som setter verdier til spillerens prosjektiler</p>
 *
 * @author Ole Østvold
 * @version 1.0
 * @since 29. april 2018
 */


public class PlayerProjectile extends Projectile {
    
    /**
     * Konstruerer et prosjektil.
     * @param player Spillerobjektet som prosjektilet kommer fra.
     */
    public PlayerProjectile(Player player) {
        
        this.x = player.getX() + player.getWidth()/2;
        this.y = player.getY();
        
        direction = calculateDirection(x, y, mouseX, mouseY);
        
        if (Gamepad.isGamepadConnected && Gamepad.rxAxis != 0 && Gamepad.ryAxis != 0) {
            direction = calculateDirection(0, 0, (double) Gamepad.rxAxis, (double) Gamepad.ryAxis);
        }

        width = canvasWidth * 0.0044;
        height = canvasWidth * 0.0044;
        speed = canvasWidth * 0.013;
        paint = Color.WHITE;   
        lifeSpan = 120;
    }

}
