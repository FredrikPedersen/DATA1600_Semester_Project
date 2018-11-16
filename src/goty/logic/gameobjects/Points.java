package goty.logic.gameobjects;

import javafx.scene.paint.Color;

/**
 * <h1>Points</h1>
 * <p>Klasse som setter farge og stroke til poengtelleren</p>
 *
 * @author Fredrik Pedersen
 * @version 1.0
 * @since 20. mars 2018
 */

public class Points {

    /** Tekstfarge */
    private Color color = Color.WHITE;
    /** Stroke farge */
    private Color stroke = Color.BLACK;

    public Points() {
    }

    /** Henter fargen til poengtelleren */
    public Color getColor(){
        return color;
    }

    /** Henter stroke-fargen til poengtellern */
    public Color getStroke () {
        return stroke;
    }
}
