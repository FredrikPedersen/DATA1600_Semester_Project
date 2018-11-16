package goty.logic.gameobjects;

/**
 * <h1>Pulse Bomb</h1>
 * <p>Klasse som oppretter logikken bak Pulse Bomb.</p>
 *
 * @author Erik Larsen
 * @version 1.0
 * @since 25. april 2018
 */

public class PulseBomb {

    private int d = 1;
    private final int deltaD = 100;

    private double centerX;
    private double centerY;

    /**
     * Setter for centerX og centerY
     * @param centerX double, x-koordinaten til midtpunktet for eksplosjonen.
     * @param centerY double, y-koordinaten til midtpunktet for eksplosjonen.
     */
    public void setCenterLoc(double centerX, double centerY) {
        this.centerX = centerX;
        this.centerY = centerY;
    }

    /**
     * Getter for centerX
     * @return double, x-koordinaten til midtpunktet for eksplosjonen.
     */
    public double getCenterX() {
        return centerX;
    }

    /**
     *  Getter-metode for centerY.
     * @return double, y-koordinaten til midtpunktet for eksplosjonen.
     */
    public double getCenterY() {
        return centerY;
    }

    /**
     * Getter for d
     * @return int, dimateren til eksplosjonen.
     */
    public int getD() {
        return d;
    }

    /**
     * når bomben er blitt aktivert av spiller oppdateres "eksplosjonen"
     * så lenge diameteren er mindere enn bredden på spillvinduet.
     * d inkrementeres med deltaD for hver frame.
     */
    public void update() {
        d += deltaD;
    }

}
