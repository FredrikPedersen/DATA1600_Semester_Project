package goty;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

/**
 * <h1>SourceWriter</h1>
 * <p>Klasse med en metode som skriver ut et dokumenter med kilder til bilder, lyd og andre elementer vi bruker i programmet</p>
 *
 * @author Fredrik Pedersen
 * @version 1.0
 * @since 15. mai 2018
 */

public class SourceWriter {

    /** Metode som oppretter et sources-dokument i den overordnede programmappen og skriver kildene våre til dokumentet */
    public static void writeSources() {
    String sources = "sources.txt";
        try {
        PrintWriter outputStream = new PrintWriter(sources);
        outputStream.printf("Images:%n" +
                "Background-image: http://nebohotel.com.ua/ %n" +
                "%n" +
                "Sprites: %n" +
                "All sprites by Fredrik Pedersen %n" +
                "%n" +
                "Sound Effects: %n" +
                "Rapid Fire Pickup: https://www.youtube.com/watch?v=Yt0_CsnJqqA %n" +
                "Stalker Spawn: https://www.youtube.com/watch?v=tqUfK1Xyy-o %n" +
                "The Essential Retro Video Game Sound Effects Collection By Juhani Junkala. %n" +
                " %n" +
                "Music: %n" +
                "Miami Disco - Perturbator (from the Hotline Miami OST) %n" +
                "Factory on Mercury (http://soundimage.org/sci-fi/) %n" +
                "%n" +
                "Font: %n" +
                "https://www.dafont.com/games.font");
        outputStream.close();
    } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
