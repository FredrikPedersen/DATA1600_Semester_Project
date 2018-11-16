package goty.logic.gameobjects;

import javafx.scene.image.Image;

/**
 * <h1>Animated Object</h1>
 * <p>Abstrakt Klasse som muligjør animasjon av spillobjekter ved å ta inn flere bilder og sette hvor lenge hvert bilde skal stå</p>
 *
 * @author Fredrik Pedersen, Ole Østvold
 * @version 2.0
 * @since 8. april 2018
 */

public abstract class AnimatedObject extends GameObject {

    /** Array med bilder som brukes til animasjon av sprites */
    public Image[] frames;

    /** Antallet frames i spillet før man bytter bilde i animasjonen */
    public int duration;

    /** Metode som brukes til animasjon av sprites, henter ut bilde X
     * @return Ett bilde fra listen av bilder */
    public Image getFrame() {
        int index = age/duration % frames.length;
        return frames[index];
    }


}
