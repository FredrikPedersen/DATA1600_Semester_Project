
package goty.logic.gameobjects;

import javafx.scene.image.Image;

import java.io.BufferedInputStream;
import static goty.controllers.GameController.canvasWidth;
import static goty.controllers.GameController.canvasHeight;

    /**
     * <h1>Center</h1>
     * <p>Center-klassen brukes for å sette opp jordkloden spilleren skal beskytte</p>
     *
     * @author Ole Østvold
     * @version 1.0
     * @since 14. mars 2018
     */
public class Center extends AnimatedObject {

    private int health = 4;
    
    private static final Image[] frames = new Image[5];

    static {
        frames[0] = new Image(new BufferedInputStream(Center.class.getClassLoader().getResourceAsStream("Sprites/Objective/Objective.png")));
        frames[1] = frames[0];
        frames[2] = new Image(new BufferedInputStream(Center.class.getClassLoader().getResourceAsStream("Sprites/Objective/ObjectiveDanger.png")));
        frames[3] = new Image(new BufferedInputStream(Center.class.getClassLoader().getResourceAsStream("Sprites/Objective/ObjectiveHalf.png")));    
        frames[4] = new Image(new BufferedInputStream(Center.class.getClassLoader().getResourceAsStream("Sprites/Objective/ObjectiveFull.png")));    
    }

    /** Konstruktør som setter høyde, bredde og X,Y-koordinatene for objektet */
    public Center() {   
        width = canvasWidth * 0.04;
        height = canvasWidth * 0.04;
        x = canvasWidth/2 - width/2;
        y = canvasHeight/2 - height/2;       
    }

    /** Getter for helsen til jordkloden
     * @return Returnerer den nåværende helsen til objektet som en int-verdi */
    public int getHealth() {
        return health;
    }

    /** Setter for helsen til jordkloden
     * @param health */
    public void setHealth(int health) {
        this.health = health;
    }
    
    @Override
    public Image getFrame() {
        return frames[health];
    }

}
