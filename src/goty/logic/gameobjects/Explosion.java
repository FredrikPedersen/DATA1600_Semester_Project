
package goty.logic.gameobjects;

import goty.logic.gameobjects.enemies.Enemy;
import javafx.scene.paint.Color;
import static goty.controllers.GameController.canvasWidth;

/**
 * <h1>Explosion</h1>
 * <p>Klasse som setter verdiene til eksplosjonen som animeres hver gang en fiende dør</p>
 *
 * @author Ole Østvold
 * @version 1.0
 * @since 26. april 2018
 */

public class Explosion extends GameObject {
    
    private final int LIFE_SPAN;
    private final double RADIUS;
    
    /**
     * Oppretter en eksplosjon i samme posisjon som fienden som døde.
     *
     * @param enemy Fienden som dør.
     */
    public Explosion(Enemy enemy) {
        x = enemy.x + enemy.width/2;
        y = enemy.y + enemy.height/2;
        width = canvasWidth * 0.002;
        height = canvasWidth * 0.002;
        paint = Color.WHITE;
        LIFE_SPAN = 50;
        RADIUS = canvasWidth * 0.25;
    }
    
    /**
     * Oppdaterer alderen til objektet (antall frames siden spawn)
     */
    public void update() {
        age++;
    }
    
    /**
     *
     * @return Hvor lenge eksplosjonen varer (antall frames)
     */
    public int getLifeSpan() {
        return LIFE_SPAN;
    }
    
    /**
     *
     * @return Eksplosjonens RADIUS.
     */
    public double getRadius() {
        return RADIUS;
    }

}
