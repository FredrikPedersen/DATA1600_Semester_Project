package goty.logic.gameobjects;

import javafx.scene.image.Image;

import java.io.BufferedInputStream;
import static goty.controllers.GameController.canvasWidth;
import static goty.controllers.GameController.canvasHeight;

/**
 * <h1>Player Healthbar</h1>
 * <p>Klasse som brukes for å hente inn bilder til og plassere spillerens helsebar</p>
 *
 * @author Fredrik Pedersen
 * @version 1.0
 * @since 28. mars 2018
 */

public class PlayerHealthBar extends GameObject {

    private Image[] image = new Image[11];

    public PlayerHealthBar() {
        image[0] = new Image(new BufferedInputStream(getClass().getClassLoader().getResourceAsStream("Sprites/PlayerHealth/PlayerHealth0.png")));
        image[1] = new Image(new BufferedInputStream(getClass().getClassLoader().getResourceAsStream("Sprites/PlayerHealth/PlayerHealth1.png")));
        image[2] = new Image(new BufferedInputStream(getClass().getClassLoader().getResourceAsStream("Sprites/PlayerHealth/PlayerHealth2.png")));
        image[3] = new Image(new BufferedInputStream(getClass().getClassLoader().getResourceAsStream("Sprites/PlayerHealth/PlayerHealth3.png")));
        image[4] = new Image(new BufferedInputStream(getClass().getClassLoader().getResourceAsStream("Sprites/PlayerHealth/PlayerHealth4.png")));
        image[5] = new Image(new BufferedInputStream(getClass().getClassLoader().getResourceAsStream("Sprites/PlayerHealth/PlayerHealth5.png")));
        image[6] = new Image(new BufferedInputStream(getClass().getClassLoader().getResourceAsStream("Sprites/PlayerHealth/PlayerHealth6.png")));
        image[7] = new Image(new BufferedInputStream(getClass().getClassLoader().getResourceAsStream("Sprites/PlayerHealth/PlayerHealth7.png")));
        image[8] = new Image(new BufferedInputStream(getClass().getClassLoader().getResourceAsStream("Sprites/PlayerHealth/PlayerHealth8.png")));
        image[9] = new Image(new BufferedInputStream(getClass().getClassLoader().getResourceAsStream("Sprites/PlayerHealth/PlayerHealth9.png")));
        image[10] = new Image(new BufferedInputStream(getClass().getClassLoader().getResourceAsStream("Sprites/PlayerHealth/PlayerHealthFull.png")));
        x = canvasWidth * 0.024;
        y = canvasHeight * 0.08;
        width = canvasWidth * 0.15;
        height = canvasHeight * 0.03;
    }

    public Image getImage(int i) {
        if (i < 0) {i = 0;}
        if (i >= image.length) {i = image.length-1;}
        return image[i];
    }
}
