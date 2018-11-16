package goty.logic.gameobjects;

import javafx.geometry.Point2D;
import javafx.scene.image.Image;
import javafx.scene.paint.Paint;

/**
 * <h1>Game Object</h1>
 * <p>Abstrakt klasse som oppretter variabler, getters og setters til alle objekter i spillet.</p>
 *
 * @author Ole Østvold
 * @version 1.0
 * @since 28. mars 2018
 */

public abstract class GameObject {

    /** Bredden til objektet i piksler */
    protected double width;
    /** Høyden til objektet i piksler */
    protected double height;
    /** Fargen til objekter uten en sprite */
    protected Paint paint;
    /** Hastigheten til objektet i pixler/frame */
    protected double speed;
    /** Antall frames siden objektet spawnet */
    protected int age;
    /** Spriten til objektet */
    protected Image image;
    /** x-posisjon */
    protected double x;
    /** y-posisjon */
    protected double y;

    /**
     * Objekt som inneholder to verdier som representerer en retnings x- og y-
     * komponent. Verdiene varierer mellom -1 (venstre/opp) og 1 (høyre/ned).
     */
    protected Point2D direction;

    /** Beregner retning basert på to koordinater
     * @param x X-koordinat til source-objektet
     * @param y Y-koordinat til source-objektet
     * @param targetX X-koordinat til target-objektet
     * @param targetY Y-koordinat til target-objektet
     * @return Retningens x- og y-komponent */
    public static Point2D calculateDirection(double x, double y, double targetX, double targetY) {
        double direction = Math.atan2(targetX - x, targetY - y);
        return new Point2D(Math.sin(direction), Math.cos(direction));
    }

    /** Henter X-posisjonen til objektet */
    public double getX() {
        return x;
    }

    /** Setter X-posisjonen til objektet */
    public void setX(double x) {
        this.x = x;
    }

    /** Henter Y-posisjonen til objektet */
    public double getY(){
        return y;
    }

    /** Setter Y-posisjonen til objektet */
    public void setY(double y) {
        this.y = y;
    }

    /** Setter størrelsen til objektet */
    public void setSize(double size) {
        width = size;
        height = size;
    }

    /** Henter fargen til objektet */
    public Paint getPaint(){
        return paint;
    }

    /** Setter fargen til objektet */
    public void setPaint(Paint paint) {
        this.paint = paint;
    }

    /** Henter spriten til objektet */
    public Image getImage() {
        return image;
    }

    /** Henter hvor mange frames det er gått siden objektet spawnet */
    public int getAge(){
        return age;
    }

    /** Setter hvor mange frames det er gått siden objektet spawnet */
    public void setAge(int age) {
        this.age = age;
    }

    /** Henter bredden til objektet */
    public double getWidth() {
        return width;
    }

    /** Setter bredden til objektet */
    public void setWidth(double width) {
        this.width = width;
    }

    /** Henter høyden til objektet */
    public double getHeight(){
        return height;
    }

    /** Setter høyden til objektet */
    public void setHeight(double height) {
        this.height = height;
    }

    /** Henter X-retningen til objektet */
    public double getDirectionX() {
        return direction.getX();
    }

    /** Setter X-retningen til objektet */
    public void setDirectionX(double directionX) {
        direction = new Point2D(directionX, direction.getY());
    }

    /** Henter Y-retningen til objektet */
    public double getDirectionY() {
        return direction.getY();
    }

    /** Setter Y-retnigen til objektet */
    public void setDirectionY(double directionY) {
        direction = new Point2D(direction.getX(), directionY);
    }

    /** Henter retningen til objektet */
    public Point2D getDirection() {
        return direction;
    }

    /** Setter retningen til objektet */
    public void setDirection(Point2D direction) {
        this.direction = direction;
    }

    /**
     *
     * @return Hastigheten til objektet.
     */
    public double getSpeed() {
        return speed;
    }

    /**
     *
     * @param speed Hastigheten til spillobjektet.
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
