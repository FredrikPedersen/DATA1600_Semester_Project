package goty.logic.reader;

import goty.logic.GameLevel;
import goty.logic.gameobjects.PulseBomb;
import goty.logic.gameobjects.enemies.*;
import goty.logic.gameobjects.items.EnergyShieldItem;
import goty.logic.gameobjects.items.HealthItem;
import goty.logic.gameobjects.items.PulseBombItem;
import goty.logic.gameobjects.items.RapidFireItem;
import javafx.util.Duration;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static goty.controllers.GameController.canvasHeight;
import static goty.controllers.GameController.canvasWidth;

/**
 * <h1>Game Reader</h1>
 * <p>Klasse med statiske metoder for lagring og lasting av spillet</p>
 *
 * @author Erik Larsen
 * @version 1.0
 * @since 6. april 2018
 */

public class GameReader {

    private final static File save = new File("save.txt");
    public static boolean unRecognizedData;


    /**
     * Skriver objekter og status fra spillet til en textfil.
     * @param gl
     */
    public static void saveToFile(GameLevel gl) {
        unRecognizedData = false;
        try(BufferedWriter file = new BufferedWriter(new FileWriter(save))) {
            file.write(gl.player.toString());
            // Types of enemies: RegularEnemy, StalkerEnemy, BigEnemy, BigBoss, ShieldBoss, AngryBoss.
            for(Enemy e : gl.enemies) {
                file.write(e.toString());
            }
            for(HealthItem h : gl.healthItems) {
                file.write(h.toString());
            }
            for(RapidFireItem r: gl.rapidFire) {
                file.write(r.toString());
            }
            for(PulseBombItem pbi : gl.pulseBombItems) {
                file.write(pbi.toString());
            }
            if(gl.esi != null) {
                file.write(gl.esi.toString());
            }
            for(int i = 0; i < gl.bombs.size(); i++) {
                file.write("PulseBomb" + "\n");
            }
            // save status
            file.write("Center" + "," + gl.center.getHealth() + "\n");
            file.write("DeadEnemies" + "," + Enemy.getDeadEnemyCount() + "\n");
            file.write("Time" + "," + GameLevel.time + "\n");
            file.write("EnemySpawnRate" + "," + Enemy.getSpawnRate() + "\n");
            file.write("EnemyCount" + "," + Enemy.getEnemyCount() + "\n");
            file.write("BossCount" + "," + gl.getBossCount() + "\n");
            file.write("BossFight" + "," + gl.bossFight + "\n");
            file.write("Music" + "," + gl.soundController.musicAudio.mediaPlayer.currentTimeProperty().getValue() + "\n");
        } catch(IOException e) {
            e.printStackTrace();
        }
    }


    // reads text file and returns data as list of lists
    private static List<List<String>> readObjInf() throws IOException {
        List<List<String>> fileAsList = new ArrayList<>();
        try(BufferedReader reader = new BufferedReader(new FileReader(save))) {
            String input;
            while((input = reader.readLine()) != null) {
                String[] data = input.split(",");
                fileAsList.add(Arrays.asList(data));
            }
        }
        return fileAsList;
    }

    /**
     * Itererer gjennom listene, og initierer objekter med verdieriene i listene.
     * Om spilleren laster spillet med en ny vindustørrelse, blir dette skalert.
     * @param gl (GameLevel)
     * @throws IOException
     */
    public static void loadObjects(GameLevel gl) throws IOException {
        List<List<String>> fileAsList = readObjInf();
        int bombCount = 0;
        for(List<String> line : fileAsList) {
            String idCheck = line.get(0);
            switch (idCheck) {
                case "Player":
                    gl.player.setX(Double.parseDouble(line.get(1)) * canvasWidth);
                    gl.player.setY(Double.parseDouble(line.get(2)) * canvasHeight);
                    gl.player.setHealth(Integer.parseInt(line.get(3)));
                    break;
                case "HealthItem":
                    HealthItem healthItem = new HealthItem();
                    healthItem.setX(Double.parseDouble(line.get(1)) * canvasWidth);
                    healthItem.setY(Double.parseDouble(line.get(2)) * canvasHeight);
                    gl.healthItems.add(healthItem);
                    break;
                case "RapidFireItem":
                    RapidFireItem rapidFireItem = new RapidFireItem();
                    rapidFireItem.setX(Double.parseDouble(line.get(1)) * canvasWidth);
                    rapidFireItem.setY(Double.parseDouble(line.get(2)) * canvasHeight);
                    gl.rapidFire.add(rapidFireItem);
                    break;
                case "PulseBombItem":
                    PulseBombItem pulseBombItem = new PulseBombItem();
                    pulseBombItem.setX(Double.parseDouble(line.get(1)) * canvasWidth);
                    pulseBombItem.setY(Double.parseDouble(line.get(2)) * canvasHeight);
                    gl.pulseBombItems.add(pulseBombItem);
                    break;
                case "EnergyShield":
                    gl.esi = new EnergyShieldItem(Double.parseDouble(line.get(1)) * canvasWidth
                            , Double.parseDouble(line.get(2)) * canvasHeight);
                    gl.esi.setAge(Integer.parseInt(line.get(3)));
                    break;
                case "RegularEnemy":
                    setEnemy(line, gl, new RegularEnemy());
                    break;
                case "Stalker":
                    setEnemy(line, gl, new StalkerEnemy());
                    break;
                case "BigEnemy":
                    setEnemy(line, gl, new BigEnemy());
                    break;
                case "BigBoss":
                    setEnemy(line, gl, new BigBoss());
                    break;
                case "ShieldBoss":
                    setEnemy(line, gl, new ShieldBoss());
                    break;
                case "AngryBoss":
                    setEnemy(line, gl, new AngryBoss());
                    break;
                case "PulseBomb":
                    bombCount++;
                    break;
                case "Center":
                    gl.center.setHealth(Integer.parseInt(line.get(1)));
                    break;
                case "DeadEnemies":
                    Enemy.setDeadEnemyCount(Integer.parseInt(line.get(1)));
                    break;
                case "Time":
                    GameLevel.time = Integer.parseInt(line.get(1));
                    break;
                case "BossFight":
                    gl.bossFight = Boolean.parseBoolean(line.get(1));
                    break;
                case "EnemySpawnRate":
                    Enemy.setSpawnRate(Integer.parseInt(line.get(1)));
                    break;
                case "EnemyCount":
                    Enemy.setEnemyCount(Integer.parseInt(line.get(1)));
                    break;
                case "BossCount":
                    gl.setBossCount(Integer.parseInt(line.get(1)));
                    break;
                case "Music":
                    gl.soundController.startpoint = Duration.valueOf(line.get(1).replaceAll("\\s", ""));
                    break;
                default:
                    unRecognizedData = true;
                    throw new IOException("Unrecognized data");
            }

        }
        for(int i = 0; i < bombCount; i++)
            gl.bombs.add(new PulseBomb());
    }

    // Instantiating enemy objects with saved values
    private static void setEnemy(List<String> list, GameLevel gl, Enemy e) throws IOException {
        for(String s : list.subList(1, list.size())) {
            char valueID = s.charAt(0); // Value identifier
            String value = s.substring(1); // discarding ID of value
            switch (valueID) {
                case 'X': // x-position
                    e.setX(Double.parseDouble(value) * canvasWidth);
                    break;
                case 'Y': // y-position
                    e.setY(Double.parseDouble(value) * canvasHeight);
                    break;
                case 'A': // age of enemy
                    e.setAge(Integer.parseInt(value));
                    break;
                case 'H': // health of enemy
                    e.setHealth(Integer.parseInt(value));
                    break;
                case 'x': // x-direction
                    e.setDirectionX(Double.parseDouble(value) * canvasWidth);
                    break;
                case 'y': // y-direction
                    e.setDirectionY(Double.parseDouble(value) * canvasHeight);
                    break;
                case 'F': // firemode
                    e.setFireMode(Boolean.parseBoolean(value));
                    break;
                case 'S': // shield
                    e.setShieldActive(Boolean.parseBoolean(value));
                    break;
                case 'B': // is a boss
                    gl.boss = e;
                    break;
                default:
                    unRecognizedData = true;
                    throw new IOException("Unrecognized data");
            }
        }
        gl.enemies.add(e);
    }
}
