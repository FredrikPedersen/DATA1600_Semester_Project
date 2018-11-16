package goty.logic.gameobjects.items;

import javafx.scene.image.Image;

import java.io.BufferedInputStream;

import goty.logic.gameobjects.AnimatedObject;
import static goty.controllers.GameController.canvasWidth;
import static goty.controllers.GameController.canvasHeight;

 /**
 *<h1>Rapid Fire Item</h1>
 *<p>Rapid Fire Itemet er en pickup i spillet som reduserer antall frames mellom hvert skudd fra spilleren.</p>
 *
 *@author Fredrik Pedersen, Ole Østvold
 *@version 1.0
 *@since 4. april 2018
 */

public class RapidFireItem extends AnimatedObject {

    /** Antall frames mellom hvert nye item */
    private static int spawnRate = 1800;
    /** Hvor mye fireraten økes med når spilleren plukker opp objektet */
    private static int fireRateIncrease = 4;
    /** Hvor lenge effekten av pickupen varer */
    private static int effectTimer = 300;

    /** variabler som trengs for å ha en animert sprite, laster inn bildene */
    private static final Image[] frames = new Image[6];

    static {
        for (int i = 0; i < frames.length; i++)
            frames[i] = new Image (new BufferedInputStream(RapidFireItem.class.getClassLoader().getResourceAsStream("Sprites/RapidFire/RapidFire_" + i +".png")));
    }

     /** Konstruktør som setter høyde, bredde, X,Y-koordinatene og varigheten pr frame for objektet */
    public RapidFireItem() {
        width = canvasWidth * 0.015;
        height = canvasWidth * 0.03;
        x = (canvasWidth-width) * Math.random();
        y = (canvasHeight-height) * Math.random();

        duration = 6;
    }


     /** Metode som oppdaterer hvor mange frames det har gått siden objektet spawnet */
    public void update() {
        age++;
    }

     /** Henter spawnraten til objektet
      * @return SpawnRaten til objektet som en int-verdi */
    public static int getSpawnRate() {
        return spawnRate;
    }

     /** Henter hvor mye objektet skal redusere antall frames mellom hvert skudd med
      * @return Hvor mye FireRate skal øke med som en int-verdi */
    public int getFireRateIncrease() {
        return fireRateIncrease;
    }

     /** Henter hvor lenge effekten av objektet skal være aktiv fra det blir plukket opp
      * @return Hvor lenge effekten av pickupen varer som en int-verdi*/
    public static int getEffectTimer() {
        return effectTimer;
    }

     /** Metode som brukes til animasjon av sprites, henter ut bilde X
      * @return Ett bilde fra listen av bilder */
    public Image getFrame() {
        int index = age/duration % frames.length;
        return frames[index];
    }

     /** toString-emtode for RapidFireItem objekter
      * @return String representasjon av objektet */
     @Override
     public String toString() {
         return "RapidFireItem" + "," + this.getX()/canvasWidth + "," + this.getY()/canvasHeight + "\n";
     }
 }
