package goty.logic.gameobjects.enemies;

import goty.logic.gameobjects.Player;
import javafx.scene.image.Image;

import java.io.BufferedInputStream;

import static goty.controllers.GameController.canvasHeight;
import static goty.controllers.GameController.canvasWidth;

/**
 * <h1>Big Enemy</h1>
 * <p>Klasse som setter verdier og logikken, samt tar inn bilder til de små raske fiendene som følger etter spilleren.</p>
 *
 * @author Ole Østvold
 * @version 2.0
 * @since 29. april 2018
 */

public class StalkerEnemy extends Enemy {
    
    private static final Image[] frames = new Image[2];

    /** Antall frames mellom hver gang retningen til stalkeren oppdateres */
    private final double STALKING_RATE;

    static {
        for (int i = 0; i < frames.length; i++)
        frames[i] = new Image (new BufferedInputStream(StalkerEnemy.class.getClassLoader().getResourceAsStream("Sprites/Stalker/stalker_" + i +".png")));
    }

    /**
     * Konstruerer en stalker.
     */
    public StalkerEnemy() {
        width = canvasWidth * 0.014;
        height = canvasWidth * 0.014;
        speed = canvasWidth * 0.0053;
        STALKING_RATE = 20;
        health = 1;
        duration = 10; // hastighet på animasjon
    }
    
    @Override
    public void update(Player player) {
        
        if (age % STALKING_RATE == 0) {
            direction = calculateDirection(x, y, player.getX(), player.getY());
        }
        
        super.update(player);      
    }
    
    @Override
    public Image getFrame() {   
        int index = age/duration % frames.length;
        return frames[index];
    }

    @Override
    public String toString() {
        return "Stalker" + "," + "X" + this.getX()/canvasWidth + "," + "Y" + this.getY()/canvasHeight  + "," + "A" + this.getAge() +
                "," + "x" + this.getDirectionX()/canvasWidth + "," + "y" + this.getDirectionY()/canvasHeight + "\n";
    }
}
