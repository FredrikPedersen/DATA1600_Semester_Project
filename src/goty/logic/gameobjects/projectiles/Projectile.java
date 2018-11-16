package goty.logic.gameobjects.projectiles;

import goty.logic.gameobjects.AnimatedObject;
import javafx.geometry.Point2D;

/**
 * <h1>Projectile</h1>
 * <p>Base-klasse til prosjektiler</p>
 *
 * @author Ole Østvold
 * @version 1.0
 * @since 29. april 2018
 */


public abstract class Projectile extends AnimatedObject {

    /**antall frames prosjektilet lever. Tilsvarer "range" */
    protected int lifeSpan;
    /** Hvor mye skade prosjektilet gjør når det treffer spilleren eller en fiende */
    protected int damage;
    protected boolean invertedDirection;

    /**
     * Oppdaterer prosjektilets posisjon og alder (antall frames siden spawn).
     */
    public void update() {   
        x += direction.getX() * speed;
        y += direction.getY() * speed;
        age++;    
    }

    /**
     *
     * @return Antall frames prosjektilet lever.
     */
    public int getLifeSpan() {
        return lifeSpan;
    }
    
    /**
     *
     * @return Hvor mye skade prosjektilet gjør i form av helsepoeng.
     */
    public int getDamage() {
        return damage;
    }
    
    /**
     * Reverserer retningen. Dette skjer kun når prosjektilet treffer skjoldet
     * til ShieldBoss.
     */
    public void invertDirection() {
        if (!invertedDirection) {
            direction = new Point2D(-direction.getX(), -direction.getY());
            invertedDirection = true;
        }
    }  
}
