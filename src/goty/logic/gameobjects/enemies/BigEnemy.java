package goty.logic.gameobjects.enemies;

import goty.logic.gameobjects.Player;
import goty.logic.gameobjects.projectiles.BigProjectile;
import goty.logic.gameobjects.projectiles.Projectile;
import javafx.scene.image.Image;

import java.io.BufferedInputStream;
import java.util.ArrayList;

import static goty.controllers.GameController.canvasHeight;
import static goty.controllers.GameController.canvasWidth;

/**
 * <h1>Big Enemy</h1>
 * <p>Klasse som setter verdier og tar inn bilder til de større fiendene som skyter på spilleren.</p>
 *
 * @author Ole Østvold
 * @version 2.0
 * @since 29. april 2018
 */


public class BigEnemy extends Enemy {

    // variabler som trengs for å ha en animert sprite, laster inn bildene.
    private static final Image[] frames = new Image[16];
    
    static {
        for (int i = 0; i < frames.length; i++)
        frames[i] = new Image (new BufferedInputStream(BigEnemy.class.getClassLoader().getResourceAsStream("Sprites/BigEnemy/BigEnemy_" + i +".png")));
    }

    /**
     * konstruktør
     */
    public BigEnemy() {
        width = canvasWidth * 0.064;
        height = canvasWidth * 0.064;
        speed = canvasWidth * 0.001;
        health = 20;
        duration = 10; // hastighet på animasjon  
        fireMode = true;
        rateOfFire = 72;
    }
    
        
    @Override
    public void fire(Player player, ArrayList<Projectile> enemyProjectiles) {        
        Projectile p = new BigProjectile(player, this);
        enemyProjectiles.add(p);
    }


    // metode som brukes til animasjon av sprites
    @Override
    public Image getFrame() {   
        int index = age/duration % frames.length;
        return frames[index];
    }

    @Override
    public String toString() {
        return "BigEnemy" + "," + "X" + this.getX()/canvasWidth + "," + "Y" + this.getY()/canvasHeight  + "," + "A" + this.getAge() + "," + "H" + this.getHealth() +
                "," + "x" + this.getDirectionX()/canvasWidth + "," + "y" + this.getDirectionY()/canvasHeight + "\n";
    }
}

