package goty.logic.gameobjects.items;

import javafx.scene.image.Image;

import java.io.BufferedInputStream;

import goty.logic.gameobjects.AnimatedObject;
import static goty.controllers.GameController.canvasWidth;
import static goty.controllers.GameController.canvasHeight;

/**
 * <h1>Health Item</h1>
 * <p>Klasse som setter verdier og tar inn bilder til helse-pickupsene.</p>
 *
 * @author Fredrik Pedersen, Ole Østvold
 * @version 1.0
 * @since 29. mars 2018
 */

public class HealthItem extends AnimatedObject {
    
    /** antall frames mellom hvert nye item */
    private static final int SPAWN_RATE = 900;

    /** hvor mye helse hver pickup gir */
    private final int HEALTH_POINTS = 20;

    // variabler som trengs for å ha en animert sprite, laster inn bildene.
    private static final Image[] frames = new Image[8];
    
    static {
        for (int i = 0; i < frames.length; i++)
            frames[i] = new Image (new BufferedInputStream(HealthItem.class.getClassLoader().getResourceAsStream("Sprites/HealthPickup/HealthPickup_" + i + ".png")));
    }

    /** Konstruktør som setter høyde, bredde, X,Y-koordinatene og varigheten pr frame for objektet */
    public HealthItem() {
        width = canvasWidth * 0.02;
        height = canvasWidth * 0.02;
        x = (canvasWidth-width) * Math.random();
        y = (canvasHeight-height) * Math.random();
        
        duration = 3;
    }

    /** Oppdaterer hvor lenge det er siden objektet spawnet */
    public void update() {
        age++;
    }

    /** Henter hvor mange frames det skal gå mellom hver gang et objekt av denne typen spawner
     * @return Spawnraten til objektet som en int-verdi */
    public static int getSpawnRate() {
        return SPAWN_RATE;
    }

    /** Henter hvor mange helsepoeng pickupen skal gi til spilleren
     * @return Hvor mye helse pickupen gir som en int-verdi */
    public int getHealthPoints() {
        return HEALTH_POINTS;
    }

    /** Metode som brukes til animasjon av sprites, henter ut bilde X
     * @return Ett bilde fra listen av bilder */
    @Override
    public Image getFrame() {   
        int index = age/duration % frames.length;
        return frames[index];
    }

    /** toString-emtode for HealthItem objekter
     * @return String representasjon av objektet */
    @Override
    public String toString() {
        return "HealthItem" + "," + this.getX()/canvasWidth + "," + this.getY()/canvasWidth + "\n";
    }
}
