package goty.logic.gameobjects.items;

import goty.logic.gameobjects.AnimatedObject;
import javafx.scene.image.Image;

import java.io.BufferedInputStream;

import static goty.controllers.GameController.canvasHeight;
import static goty.controllers.GameController.canvasWidth;

/**
 * <h1>Energy Shield Item</h1>
 * <p>Klasse som setter verdier og tar inn bilder til Energy Shield-itemet som fyller på jordklodens skjold.
 * Denne klassen har ikke en egen spawn-timer, men spawner etter hver boss man bekjempet.</p>
 *
 * @author Erik Larsen
 * @version 1.0
 * @since 29. april 2018
 */

public class EnergyShieldItem extends AnimatedObject {

    private static final Image[] frames = new Image[8];

    static {
        for (int i = 0; i < frames.length; i++)
            frames[i] = new Image (new BufferedInputStream(EnergyShieldItem.class.getClassLoader().getResourceAsStream("Sprites/Energy/EnergyShieldPickUp_" + i + ".png")));
    }

    /**
     * Konstruktør for EnergyShieldItem
     * @param xloc double, x-koordinaten til spawn av EnergyShieldItem.
     * @param yloc double, y-koordinaten til spawn av EnergyShieldItem.
     */
    public EnergyShieldItem(double xloc, double yloc) {
        width = canvasWidth * 0.02;
        height = canvasWidth * 0.02;
        x = xloc;
        y = yloc;

        duration = 3;
    }

    /**
     * Inkrementerer alderen på objektet med én for hver frame
     */
    public void update() {
        age++;
    }

    /**
     * Getter for frame[x]
     * @return ett bilde fra listen av bilder.
     */
    public Image getFrame() {
        int index = age/duration % frames.length;
        return frames[index];
    }

    /**
     * toString-metode for EnergyShieldItem objekt
     * @return String representasjon av objektet
     */
    @Override
    public String toString() {
        return "EnergyShield" + "," + this.getX()/canvasWidth + "," + this.getY()/canvasHeight + "," + this.getAge() + "\n";
    }
}
