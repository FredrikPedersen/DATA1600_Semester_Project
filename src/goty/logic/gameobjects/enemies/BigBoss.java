package goty.logic.gameobjects.enemies;

import goty.logic.gameobjects.Player;
import goty.logic.gameobjects.projectiles.BigBossProjectile;
import goty.logic.gameobjects.projectiles.Projectile;
import javafx.scene.image.Image;

import java.io.BufferedInputStream;
import java.util.ArrayList;

import static goty.controllers.GameController.canvasHeight;
import static goty.controllers.GameController.canvasWidth;

/**
 * <h1>Big Boss</h1>
 * <p>Klasse som setter verdier og tar inn bilder til fienden Big Boss, som er en boss-fiende skyter på spilleren i et bølgemønster og har mye helse.</p>
 *
 * @author Ole Østvold
 * @version 1.0
 * @since 29. april 2018
 */

public class BigBoss extends Enemy {
    
    private static final Image[] frames = new Image[20];

    static {
        for (int i = 0; i < frames.length; i++)
            frames[i] = new Image (new BufferedInputStream(BigBoss.class.getClassLoader().getResourceAsStream("Sprites/BigBoss/BigBoss_" + i + ".png")));
    }
    
    /**
     * Konstruktør.
     */
    public BigBoss() {
        
        width = canvasWidth * 0.30;
        height = canvasHeight*0.78;
        x = -width;
        y = canvasHeight/2 - height/2;
        maxHealth = 180;
        health = 200000; // udødelig i starten
        rateOfFire = 10;
        fireMode = false; // ingen prosjektiler i starten
        duration = 3; // hastighet på animasjon
        speed = canvasWidth * 0.0013;
    }
    
    @Override
    public void update(Player player) {
       
        if (age > 300 && x < 0) x+=speed;
        if (age == 500) {
             health = maxHealth;
             fireMode = true;
        }      
        age++;
    }
    
    @Override
    public void fire(Player player, ArrayList<Projectile> enemyProjectiles) {        
        Projectile p = new BigBossProjectile(player, this);
        enemyProjectiles.add(p);
    }

    @Override
    public Image getFrame() {
        int index = age/duration % frames.length;
        return frames[index];
    }

    @Override
    public String toString() {
        return "BigBoss" + "," + "X" + this.getX()/canvasWidth + "," + "Y" + this.getY()/canvasHeight  + "," + "A" + this.getAge() + "," + "H" + this.getHealth() +
                "," + "x" + this.getDirectionX()/canvasWidth + "," + "y" + this.getDirectionY()/canvasHeight + "," + "F" + this.isFiring() + "," + "B" + "\n";
    }
}