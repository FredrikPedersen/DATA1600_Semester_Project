
package goty.logic.gameobjects.projectiles;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import static goty.controllers.GameController.canvasWidth;

/**
 * <h1>Simple Projectile</h1>
 * <p>Klasse som setter verdier til prosjektilene som Shield Boss bruker</p>
 *
 * @author Ole Østvold
 * @version 1.0
 * @since 29. april 2018
 */

public class SimpleProjectile extends Projectile {
    
    /** hastighet på gradient-effekt */
    private double speedX = 30;
    /** hastighet på gradient-effekt */
    private double speedY = 40;

    /**
     * Konstruerer et prosjektil.
     * @param x X-koordinatet til prosjektilets start-posisjon.
     * @param y Y-koordinatet til prosjektilets start-posisjon.
     * @param direction Retningen til prosjektilet.
     */
    public SimpleProjectile (double x, double y, Point2D direction) {
        
        this.x = x;
        this.y = y;
        
        this.direction = direction;
        width = canvasWidth * 0.025;
        height = canvasWidth * 0.025;
        speed = canvasWidth * 0.007;
        damage = 3;
        lifeSpan = 120;
    }
    
    @Override
    public void update() {
        
        super.update();
        
        double centerX = (age % (speedX*2+1))/speedX;
        if (centerX > 1.0) centerX = 2 - centerX;
        double centerY = (age % (speedY*2+1))/speedY;
        if (centerY > 1.0) centerY = 2 - centerY;
        paint = new RadialGradient(0, 0, centerX, centerY, 0.5, true, CycleMethod.NO_CYCLE, new Stop(0, new Color(0,0,0,0.7)), new Stop(1, new Color(1,0,0,0.7)));
    }
}
