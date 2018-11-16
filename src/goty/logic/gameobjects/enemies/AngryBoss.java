
package goty.logic.gameobjects.enemies;

import goty.logic.gameobjects.Player;
import goty.logic.gameobjects.projectiles.Projectile;
import goty.logic.gameobjects.projectiles.RotatingProjectile;
import javafx.scene.image.Image;

import java.io.BufferedInputStream;
import java.util.ArrayList;

import static goty.controllers.GameController.canvasHeight;
import static goty.controllers.GameController.canvasWidth;

/**
 * <h1>Angry Boss</h1>
 * <p>Klasse som setter verdier og tar inn bilder til fienden Angry Boss, som er en boss-fiende som går i bane rundt sentrum og lager en pisk av prosjektiler som spilleren må unngå</p>
 *
 * @author Ole Østvold
 * @version 1.0
 * @since 29. april 2018
 */

public class AngryBoss extends Enemy {
    
    private static final Image[] frames = new Image[10];
    private final double ROTATION_SPEED = 0.025;
    private final double NORMAL_SIZE = canvasWidth * 0.24;
       
    static {
        for (int i = 0; i < frames.length; i++)
        frames[i] = new Image (new BufferedInputStream(AngryBoss.class.getClassLoader().getResourceAsStream("Sprites/AngryBoss/AngryBoss_" + i +".png")));
    }
    
    /**
     * Konstruerer bossen.
     */
    public AngryBoss() {
        
        x = -canvasWidth;
        y = -canvasHeight;
        maxHealth = 150;
        health = 200000; // udødelig i starten
        rateOfFire = 1;
        fireMode = false;
        duration = 1;
    }
    
    @Override
    public void update(Player player) {
       
        if (age > 240 && age < 440) {
            width = NORMAL_SIZE * (age-241)/200.0;
            height = width;
        }
            
        if (age > 240) {
            x = canvasWidth/2 - width/2 + Math.sin(age * ROTATION_SPEED) * canvasWidth/2;
            y = canvasHeight/2 - height/2 + Math.cos(age * ROTATION_SPEED) * canvasWidth/5;
        }
        
        if (age == 500) {
            health = maxHealth;
            fireMode = true;
            width = NORMAL_SIZE;
            height = NORMAL_SIZE;
        }
        
        age++; 
    }
    
    @Override
    public void fire(Player player, ArrayList<Projectile> enemyProjectiles) {        
        Projectile p = new RotatingProjectile(player, this);
        enemyProjectiles.add(p);
    }
    
    @Override
    public Image getFrame() {
        int index = age/duration % frames.length;
        return frames[index];
    }

    @Override
    public String toString() {
        return "AngryBoss" + "," + "X" + this.getX()/canvasWidth + "," + "Y" + this.getY()/canvasHeight  + "," + "A" + this.getAge() + "," + "H" + this.getHealth() +
                "," + "x" + this.getDirectionX()/canvasWidth + "," + "y" + this.getDirectionY()/canvasHeight + "," + "B" + "\n";
    }
}
