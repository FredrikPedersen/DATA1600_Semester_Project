package goty;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.input.KeyCombination;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import static goty.controllers.GameController.*;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

/**
 * <h1>Main</h1>
 * <p>Main-klassen som iverksetter hele programmet </p>
 *
 * @author Ole Østvold
 * @version 1.0
 * @since 10. februar 2018
 */
public class Main extends Application {

    /**
     * Starter spillet. Setter også i gang en tråd som initialiserer klasser som inneholder
     * statiske bilder, slik at bildene lastes opp til minnet.
     * 
     * {@inheritDoc}
     */
    @Override
    public void start(Stage primaryStage) {
        try {           
            Runnable runnable = Main::load;
            new Thread(runnable).start();
            
            Parent root = FXMLLoader.load(getClass().getResource("graphics/Main.fxml"));
            Scene scene = new Scene(root);
            scene.widthProperty().addListener((observable, oldValue, newValue) -> canvasWidth = newValue.doubleValue());
            scene.heightProperty().addListener((observable, oldValue, newValue) -> canvasHeight = newValue.doubleValue());
            Font.loadFont(getClass().getResourceAsStream("graphics/Games.ttf"), 0);
            
            primaryStage.setScene(scene);
            primaryStage.setTitle("Game Of The Year");
            primaryStage.setFullScreen(true);
            primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
            primaryStage.show();
            
        } catch (IOException e) {
            System.err.println("error loading game");
        }
    }

    /**
     *
     * Sjekker om tilstrekkelig minne er dedikert til java, og vil
     * restarte programmet om dette ikke er tilfelle.
     * 
     */
    public static void main(String[] args) throws IOException, URISyntaxException  {

        if (args.length == 0 && Runtime.getRuntime().maxMemory()/(1024*1024) < 900) {
            
           String currentPath = Main.class
                   .getProtectionDomain()
                   .getCodeSource().getLocation()
                   .toURI().getPath()
                   .replace('/', File.separator.charAt(0)).substring(1);         
            Runtime.getRuntime().exec("java -jar -Xmx1024m " + currentPath);
            System.exit(0);
        } 
        launch(args);
    }

    private static void load() {
        try {
            Class.forName("goty.logic.gameobjects.Player");
            Class.forName("goty.logic.gameobjects.Center");
            Class.forName("goty.logic.gameobjects.enemies.RegularEnemy");
            Class.forName("goty.logic.gameobjects.enemies.StalkerEnemy");
            Class.forName("goty.logic.gameobjects.enemies.BigEnemy");
            Class.forName("goty.logic.gameobjects.items.HealthItem");
            Class.forName("goty.logic.gameobjects.items.RapidFireItem");
            Class.forName("goty.logic.gameobjects.enemies.BigBoss");
            Class.forName("goty.logic.gameobjects.enemies.AngryBoss");
            Class.forName("goty.logic.gameobjects.enemies.ShieldBoss");
            Class.forName("goty.logic.gameobjects.projectiles.BigProjectile");
            Class.forName("goty.logic.gameobjects.items.EnergyShieldItem");
        } catch (ClassNotFoundException e) {
            System.err.println("class not found during load");
        }
    }
}
