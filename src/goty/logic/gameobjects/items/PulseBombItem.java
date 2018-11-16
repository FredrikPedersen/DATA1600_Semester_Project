package goty.logic.gameobjects.items;

import goty.logic.gameobjects.AnimatedObject;
import javafx.scene.image.Image;

import java.io.BufferedInputStream;

import static goty.controllers.GameController.canvasHeight;
import static goty.controllers.GameController.canvasWidth;

/**
 * <h1>Pulse Bomb Item</h1>
 * <p>Klasse som setter verdier og laster inn bilder til Pulse Bomb-items</p>
 *
 * @author Erik Larsen
 * @version 1.0
 * @since 25. april 2018
 */

public class PulseBombItem extends AnimatedObject {

    private static int spawnRate = 2700;

    private static final Image[] frames = new Image[2];
    static {
        for(int  i = 0; i < frames.length; i++)
            frames[i] = new Image(new BufferedInputStream(PulseBombItem.class.getClassLoader().getResourceAsStream("Sprites/Bomb/Bomb_" + i + ".png")));
    }

    /**
     * Konstruktør for PulseBombItem.
     */
    public PulseBombItem() {
        width = canvasWidth * 0.02;
        height = canvasWidth * 0.02;
        x = (canvasWidth - width) * Math.random();
        y = (canvasHeight - height) * Math.random();

        duration = 10;
    }

    /**
     * Inkrementerer alderen til objektet med én for hver frame.
     */
    public void update() {
        age++;
    }

    /**
     * Getter-metode for spawnRate.
     * @return int, spawnraten til objektet.
     */
    public static int getSpawnRate() {
        return spawnRate;
    }

    /**
     * Getter-metode for Frame[x]
     * @return Image, ett bilde fra listen av bilder
     */
    public Image getFrame() {
        int index = age/duration % frames.length;
        return frames[index];
    }

    /**
     * toString-metode for objektet
     * @return String-representasjon for objektet
     */
    @Override
    public String toString() {
        return "PulseBombItem" + "," + this.getX()/canvasWidth + "," + this.getY()/canvasHeight + "\n";
    }
}
