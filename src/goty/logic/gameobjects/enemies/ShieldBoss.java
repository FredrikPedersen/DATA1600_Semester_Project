package goty.logic.gameobjects.enemies;

import goty.logic.gameobjects.Player;
import goty.logic.gameobjects.projectiles.Projectile;
import goty.logic.gameobjects.projectiles.SimpleProjectile;
import javafx.geometry.Point2D;
import javafx.scene.image.Image;

import java.io.BufferedInputStream;
import java.util.ArrayList;

import static goty.controllers.GameController.canvasHeight;
import static goty.controllers.GameController.canvasWidth;

/**
 * <h1>Shield Boss</h1>
 * <p>Klasse som setter verdier og tar inn bilder til fienden Shield Boss, som er en boss-fiende som skyter på spilleren i tilfeldige mønstere, og som krever en pulse bomb for å fjerne skjoldet</p>
 *
 * @author Ole Østvold
 * @version 1.0
 * @since 29. april 2018
 */

public class ShieldBoss extends Enemy {
    
    private static final Image[] frames = new Image[2];
    private boolean movingLeft;
         
    static {
        frames[0] = new Image (new BufferedInputStream(ShieldBoss.class.getClassLoader().getResourceAsStream("Sprites/ShieldBoss/ShieldBoss_0.png")));
        frames[1] = new Image (new BufferedInputStream(ShieldBoss.class.getClassLoader().getResourceAsStream("Sprites/ShieldBoss/ShieldBoss_1.png")));
    }
    
    /**
     * Konstruerer en shield-boss.
     */
    public ShieldBoss() {
        
        width = canvasWidth * 0.25;
        height = canvasWidth * 0.25;
        x = canvasWidth*10;
        y = canvasHeight*10;
        speed = canvasWidth * 0.01;
        maxHealth = 100;
        health = 200000; // udødelig i starten
        rateOfFire = 20;
        fireMode = false;
        duration = 30;
        shieldActive = true;
    }
    
    @Override
    public void update(Player player) {
       
        if (age == 300) {
            x = (canvasWidth-width)/2;
            y = canvasHeight;
        }
        
        if (y > canvasHeight-height/2) {
            y -= speed/12;
        }
        
        if (age > 600) {
            if (!movingLeft) x +=speed; else x -=speed;
            if (x > canvasWidth - width) movingLeft = true;
            if (x < 0) movingLeft = false;
        }
        
        if (age == 600) {
            health = maxHealth;
            fireMode = true;
        }
        
        age++;
    }
    
    @Override
    public void fire(Player player, ArrayList<Projectile> enemyProjectiles) {

        Projectile p = new SimpleProjectile(x+width/2, y+height/2, new Point2D(0,-1));
        enemyProjectiles.add(p);
      
        if (Math.random() > 0.7) {
            direction = calculateDirection(x, y, player.getX(), player.getY());
            Projectile p2 = new SimpleProjectile(x, y, direction);
            enemyProjectiles.add(p2);
        }
    }
    
    @Override
    public Image getFrame() {
        if (shieldActive) return frames[0]; else return frames[1];
    }

    @Override
    public String toString() {
        return "ShieldBoss" + "," + "X" + this.getX()/canvasWidth + "," + "Y" + this.getY()/canvasHeight + "," + "A" + this.getAge() + "," +
                "H" + this.getHealth() + "," + "x" + this.getDirectionX()/canvasWidth + "," + "y" + this.getDirectionY()/canvasHeight + "," +
                "F" + this.isFiring() + "," + "S" + this.isShieldActive() + "," + "B" + "\n";
    }
}
