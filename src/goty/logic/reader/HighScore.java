package goty.logic.reader;

import goty.logic.Score;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * <h1>Highsore</h1>
 * <p>Sortering av poeng fra spillet.
 *    HighScore instansieres kun en gang</p>
 *
 * @author Erik Larsen
 * @version 1.0
 * @since 17. april 2018
 */

public enum HighScore {

    HIGH_SCORE; // instance;

    private String fileName = "Highscore.dat";
    public final int MAXCAP = 5;
    private List<Score> scores = new ArrayList<>(MAXCAP);

    // sort ascending
    private void sortHS() {
        scores.sort(Score::compareTo);
    }

    // sort descending
    private void reverseSortHS() {
        scores.sort(Comparator.reverseOrder());
    }

    // removes lowest score
    private void removeLowestScore() {
        reverseSortHS();
        if(scores.size() > MAXCAP) {
            scores.remove(MAXCAP);
        }
    }

    /**
     * Legger til ny Score i listen, fjerner den laveste, laster endring til fil
     * @param score Score
     */
    public void addScore(Score score) {
        scores.add(score);
        removeLowestScore();
        hsToFile();
    }

    /**
     * Skriver Scores til Fil av .dat format
     * Score er Serializable.
     */
    public void hsToFile() {
        try(ObjectOutputStream file = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)))) {
            file.writeObject(scores);
        }catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Leser highscore listen fra fil,
     * om filen ikke blir funnet, kalles defaultload.
     */
    @SuppressWarnings("all")
    public void readHighscore() {
        try(ObjectInputStream file = new ObjectInputStream(new BufferedInputStream(new FileInputStream(fileName)))) {
            scores = (List<Score>) file.readObject();
        } catch (FileNotFoundException e) {
            defaultLoad();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Getter for scores
     * @return liste av Score objekter
     */
    public List<Score> getScores() {
        return scores;
    }


    //creating "empty" scores and read them to file
    private void defaultLoad() {
        for(int i = 0; i < MAXCAP; i++) {
            scores.add(new Score());
        }
        hsToFile();
    }


}
