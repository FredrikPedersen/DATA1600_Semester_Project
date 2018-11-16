package goty.logic.gameobjects;


import javafx.scene.image.Image;

import java.io.BufferedInputStream;

import goty.logic.gameobjects.items.RapidFireItem;
import static goty.controllers.GameController.canvasWidth;
import static goty.controllers.GameController.canvasHeight;
import goty.controllers.Gamepad;

/**
 * <h1>Player</h1>
 * <p>Klasse som inneholder alle verdier, metoder og begrensinger for spiller-objektet.</p>
 *
 * @author Fredrik Pedersen, Ole Østvold
 * @version 1.0
 * @since 10. februar 2018
 */

public class Player extends AnimatedObject{

    /** Initielle antall frames mellom hvert skudd */
    private final int INITIAL_RATE_OF_FIRE = 7;
    /** Antall frames mellom hvert skudd */
    private int rateOfFire;

    /** Hvorvidt rapid fire er aktiv eller ikke */
    public static boolean rapidFireMode;
    /** Int-verdi som brukes for å kontrollere hvor lenge rapid fire mode skal være aktiv */
    private int timer;

    /** Den maksimale helsen til spilleren */
    private final int MAX_HEALTH = 100;
    /** Den nåvæerende helsen til spilleren */
    private int health;
    
    // tastetrykk
    private boolean left;
    private boolean right;
    private boolean up;
    private boolean down;
    
    private static final Image[] frames = new Image[4];

    static {
        for (int i = 0; i < frames.length; i++)
            frames[i] = new Image (new BufferedInputStream(Player.class.getClassLoader().getResourceAsStream("Sprites/Player/player_" + i + ".png")));
    }

    /**
     * Konstruerer spiller-objektet og plasserer det midt på brettet.
     */
    public Player() {
        x = canvasWidth/2;
        y = canvasHeight/2;
        width = canvasWidth * 0.020;
        height = canvasWidth * 0.020;
        speed = canvasWidth * 0.006;
        health = MAX_HEALTH;
        rateOfFire = INITIAL_RATE_OF_FIRE;
        
        duration = 30;
    }

    /**
     * Oppdaterer spillerens posisjon/tilstand basert på input.
     */
    public void update() {

        if (left) { x-=speed; }
        if (right) { x+=speed; }
        if (down) {y+=speed; }
        if (up) { y-=speed; }
          
        if (Gamepad.isGamepadConnected) {
            x += speed * Gamepad.xAxis;
            y += speed * Gamepad.yAxis;
        }
           
        // hindrer at spiller går utenfor brettet
        if (y > canvasHeight - height ) { y = canvasHeight - height;}
        else if (y < 0 ) { y = 0;}
        if (x > canvasWidth - width) { x = canvasWidth - width;}
        else if (x < 0 ) { x = 0;}
        
        if (rapidFireMode) {
            timer++;
            if (timer > RapidFireItem.getEffectTimer()) {
                rapidFireMode = false;
                timer = 0;
                rateOfFire = INITIAL_RATE_OF_FIRE;
            }            
        }
        
        age++;
    }

    /**
     * Stopper all bevegelse.
     */
    public void stopMotion() {
        right = false; left = false;
        up = false; down = false;
    }

    /**
     * 
     * @param b Hvorvidt spiller skal bevege seg mot venstre.
     */
    public void setLeft(boolean b) {
        left = b;
    }
    
    /**
     *
     * @param b Hvorvidt spiller skal bevege seg mot høyre.
     */
    public void setRight(boolean b) {
        right = b;
    }
    
    /**
     *
     * @param b Hvorvidt spiller skal bevege seg opp.
     */
    public void setUp(boolean b) {
        up = b;
    }
    
    /**
     *
     * @param b Hvorvidt spiller skal bevege seg ned.
     */
    public void setDown(boolean b) {
        down = b;
    }

    /**
     *
     * @return Antall frames mellom hvert skudd ved skyting. 
     */
    public int getRateOfFire() {
        return rateOfFire;
    }
    
    /**
     *
     * @return Antall helsepoeng.
     */
    public int getHealth() {
        return health;
    }
    
    /**
     *
     * @param health Helsepoeng.
     */
    public void setHealth(int health) {
        if (health < 0) {this.health = 0;}
        else if (health > MAX_HEALTH) {this.health = MAX_HEALTH;}
        else {this.health = health;}
    }

    /**
     *
     * @param rateOfFire Antall frames mellom hvert skudd ved skyting. 
     */
    public void setRateOfFire (int rateOfFire) {
        if (rateOfFire < 1) this.rateOfFire = 1;
        else this.rateOfFire = rateOfFire;
    }
    
    /**
     *
     * @return Max helse.
     */
    public int getMaxHealth() {
        return MAX_HEALTH;
    }
    
    /**
     * Aktiverer RapidFire.
     */
    public void activateRapidFireMode() {
        rapidFireMode = true;
        timer = 0;
    }
    /** metode som brukes til animasjon av sprites */
    @Override
    public Image getFrame() {
        int index = age/duration % frames.length;
        return frames[index];
    }

    @Override
    public String toString() {
        return "Player" + "," + this.getX()/canvasWidth + ","
                + this.getY()/canvasHeight + "," + this.getHealth() + "\n";
    }
}
