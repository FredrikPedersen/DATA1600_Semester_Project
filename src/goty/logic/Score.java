package goty.logic;

import java.io.Serializable;

/**
 * <h1>Score</h1>
 * <p>Klasse for poeng. Klassen implementerer Comparable for sammenligning mot andre Score objekter,
 *    og markert som Serializable for lagring av Objektet</p>
 *
 * @author Erik Larsen
 * @version 1.0
 * @since 17. april 2018
 */

public class Score implements Comparable<Score>, Serializable {

    private String name;
    private int score;
    private int timePlayed;

    /**
     * konstruktør nummer 1
     * @param score int poeng
     * @param timePlayed int tid spilt
     */
    public Score(int score, int timePlayed) {
        this.score = score;
        this.timePlayed = timePlayed;
    }

    /**
     * konstruktør nummer 2, brukes til laste tomme poengscorer til highscore
     */
    public Score() {
        this(0, 0);
        this.name = ".....";
    }

    /**
     * Getter-metode for navn
     * @return String, navn
     */
    public String getName() { return name; }

    /**
     * Getter-metode for poeng
     * @return int, antall poeng
     */
    public int getScore() {
        return score;
    }

    /**
     * Setter-metode for navn
     * @param name String, navn
     */
    public void setName(String name) { this.name = name; }

    /**
     * Setter-metode for poeng
     * @param score int, poeng
     */
    public void setScore(int score) { this.score = score; }


    /**
     * Sammenligner de ulike score-objektene.
     * Om to peongsummer er like baseres sammenligningen på tid.
     * @param other Score-objekt
     * @return int, negativ returverdi betyr at Score er lavere rangert enn other,
     *              positiv returverdi betyr at Score er høyere rangert enn other,
     *              0 returverdi betyr at Score og Score other er like
     */
    @Override
    public int compareTo(Score other) {
        int check = Integer.compare(this.getScore(), other.getScore());
        if(check == 0)
            check = Integer.compare(this.getTime(), other.getTime());
        return check;
    }

    /**
     * Getter-metode for timePlayed
     * @return int, tid spilt
     */
    public int getTime() {
        return timePlayed;
    }

    /**
     * toString-mdtode for Score
     * @return String representasjon av objektet.
     *         navn; venstre-orientert 13 "spaces" allokert. 10 bokstaver eller nummer blir vist.
     *         score; høyre-orientert , 4 nummer.
     */
    @Override
    public String toString() {

        return String.format("%-13s %4d",
                name = (name.length() > 10) ? name.substring(0, 9) : name, score);

    }


}
