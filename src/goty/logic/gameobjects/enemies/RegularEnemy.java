package goty.logic.gameobjects.enemies;

import goty.logic.gameobjects.Player;
import javafx.scene.image.Image;

import java.io.BufferedInputStream;

import static goty.controllers.GameController.canvasHeight;
import static goty.controllers.GameController.canvasWidth;

/**
 * <h1>Regular Enemy</h1>
 * <p>Klasse som setter verdier og tar inn bilder til de vanlige fiendene</p>
 *
 * @author Ole Østvold
 * @version 2.0
 * @since 16. april 2018
 */


public class RegularEnemy extends Enemy {

    /** Variabler som trengs for å ha en animert sprite, laster inn bildene. */
    private static final Image[] frames = new Image[7];
        
    static {
        for (int i = 0; i < frames.length; i++)
        frames[i] = new Image (new BufferedInputStream(Player.class.getClassLoader().getResourceAsStream("Sprites/Enemies/enemysmall_" + i +".png")));
    }

    /**
     * Konstruktør.
     */
    public RegularEnemy() {
        width = canvasWidth * 0.017;
        height = canvasWidth * 0.017;
        speed = canvasWidth * 0.001;
        health = 1;
        duration = 10; //hastighet på animasjon
    }

    /** metode som brukes til animasjon av sprites */
    @Override
    public Image getFrame() {
        int index = age/duration % frames.length;
        return frames[index];       
    }

    @Override
    public String toString() {
        return "RegularEnemy" + "," + "X" + this.getX()/canvasWidth + "," + "Y" + this.getY()/canvasHeight  + "," + "A" + this.getAge() +
                "," + "x" + this.getDirectionX()/canvasWidth + "," + "y" + this.getDirectionY()/canvasHeight + "\n";
    }
}
