package goty.logic.gameobjects.enemies;

import java.util.ArrayList;

import goty.logic.gameobjects.AnimatedObject;
import goty.logic.gameobjects.Player;
import goty.logic.gameobjects.projectiles.Projectile;
import static goty.controllers.GameController.canvasWidth;
import static goty.controllers.GameController.canvasHeight;

/**
 * <h1>Enemy</h1>
 * <p>Abstrakt klasse som inneholder alle felles metoder og funksjoner for de forskjellige fiendetypene</p>
 *
 * @author Ole Østvold
 * @version 2.0
 * @since 16. april 2018
 */

public abstract class Enemy extends AnimatedObject {

    /** Antall fiender som har spawnet */
    private static int enemyCount;
    /** Antall fiender som er blitt drept */
    private static int deadEnemyCount;

    /**  Antall frames mellom hver ny fiende */
    private static int startSpawnRate = 90;
    private static int spawnRate;

    /** Betyr at hver x fiende er av denne typen */
    public static int stalkerEnemyRatio = 11;
    /** Betyr at hver x fiende er av denne typen */
    public static int bigEnemyRatio = 13;
    /** Betyr at hver x fiende er en boss */
    public static int bossRatio = 50;

    /** Hvorvidt fienden skyter prosjektiler */
    protected boolean fireMode;
    /** Antall frames mellom hvert skudd */
    protected int rateOfFire;

    /** Om skjoldet til fienden er aktivt */
    protected boolean shieldActive;
    /** Maksimum helse fienden kan ha */
    protected int maxHealth;
    /** Den nåværende helsen til fienden */
    protected int health;

    /**
     * Konstruerer en fiende, plasserer den på et tilfeldig punkt
     * langs kanten av brettet, og gir den retning mot sentrum av brettet.
     */
    public Enemy() {
        
        double z = Math.random();
        if (z > 0.75) {x = 0; y = (z*4 - 3) * canvasHeight;}
        else if (z > 0.5) {x = canvasWidth; y = (z*4 - 2) * canvasHeight;}
        else if (z > 0.25) {y = 0; x = (z*4 - 1) * canvasWidth;}
        else {y = canvasHeight; x = z*4 * canvasWidth;}
        
        direction = calculateDirection(x, y, canvasWidth/2, canvasHeight/2);
        
        enemyCount++;

    }

    /** Setter så fienden skyter prosjektiler */
    public void fire(Player player, ArrayList<Projectile> enemyprojectiles) {
    }

    /**
     * Oppdaterer posisjon og alder.
     *
     * @param player
     */
    public void update(Player player) {
        
        x += direction.getX() * speed;
        y += direction.getY() * speed;  
        
        age++;
    }

    /** Henter spawnraten til fienden */
    public static int getSpawnRate() {
        return spawnRate;
    }

    /** Setter spawnraten til fienden */
    public static void setSpawnRate(int rate) {
        if (rate != 0) spawnRate = rate;
    }

    /** Henter den initielle spawnraten til fienden */
    public static int getStartSpawnRate() {
        return startSpawnRate;
    }

    /** Henter hvor mange frame det er mellom hver skudd for fienden */
    public int getRateOfFire() {
        return rateOfFire;
    }

    /** Metode som henter om fienden skal skyte eller ikke */
    public boolean isFiring() {
        return fireMode;
    }

    /** Henter hvor mange fiender som har spawnet totalt siden spillet ble startet */
    public static int getEnemyCount() {
        return enemyCount;
    }

    /** Setter fiendens skjold til å være aktivt */
    public void setShieldActive(boolean shieldActive) {
        this.shieldActive = shieldActive;
    }

    /** Henter om skjoldet til fienden skal være aktivt */
    public boolean isShieldActive() {
        return shieldActive;
    }

    /** Setter om fienden skal skyte eller ikke */
    public void setFireMode(boolean fireMode) {
        this.fireMode = fireMode;
    }

    /** Setter hvor mange fiender som har spawnet siden spillet ble startet */
    public static void setEnemyCount(int count) {
        enemyCount = count;
    }

    /** Henter hvor mange fiender som har blitt drept siden spillet startet */
    public static int getDeadEnemyCount() {
        return deadEnemyCount;
    }

    /** Setter hvor mange fiender som har blitt drept siden spillet startet */
    public static void setDeadEnemyCount(int count) {
        deadEnemyCount = count;
    }

    /** Henter den nåværende helsen til fienden */
    public int getHealth() {
        return health;
    }

    /** Setter den nåværende helsen til fienden */
    public void setHealth(int health) {
        this.health = health;
    }

    /** Henter den maksimale helsen til fienden */
    public int getMaxHealth() {
         return maxHealth;
    }
}
