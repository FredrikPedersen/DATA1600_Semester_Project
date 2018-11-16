package goty.controllers;


import goty.Main;
import goty.SourceWriter;
import goty.logic.reader.GameReader;
import goty.logic.reader.HighScore;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import static goty.graphics.MenuAnimation.enterGameAnimation;
import static goty.graphics.MenuAnimation.fadeIn;


/**
 * <h1>Controller</h1>
 * <p>Kontroller med metoder, logikk og innlastning av FXMLene til hovedmenyen</p>
 *
 * @author Erik Larsen
 * @version 1.0
 * @since 10. februar 2018
 */

public class Controller {

    // Containers
    @FXML
    StackPane menuRoot; @FXML VBox mainMenu; @FXML StackPane menuStack;
    @FXML VBox menuBox; @FXML VBox optionBox; @FXML VBox audioBox;
    @FXML VBox howToPlayBox; @FXML VBox resolutionBox; @FXML VBox highscoreBox;
    @FXML VBox errorBox; @FXML VBox creditBox;
    @FXML VBox controlBox; @FXML TilePane pickupBox;

    // Text
    @FXML Label loadGame; @FXML Label creditText;
    @FXML Label scoresText; @FXML Label errorText;
    @FXML Label pickups; @FXML Label controls;
    // Image
    @FXML ImageView gameTitle; @FXML ImageView earth;
    // Sliders
    @FXML Slider mSlider; @FXML Slider eSlider;



    //soundController object
    private SoundController soundController = new SoundController();
    //HighScore object
    private HighScore hs = HighScore.HIGH_SCORE;
    //boolean for whether entering game with loading save file or not
    public static boolean gameLoad = false;

    /**
     * metoden blir kalt når fxml fil blir lastet inn av Main.
     */
    public void initialize() {

        soundController.playMusic(soundController.mainBgMusic);
        fadeIn(Duration.seconds(1), menuRoot);

        menuRoot.layoutBoundsProperty().addListener((o, ov, n) -> {
            menuRoot.setStyle("-fx-font-size: " + n.getWidth()/70);
            gameTitle.setFitWidth(n.getWidth()/2);
            for(Node box : menuStack.getChildren()) {
                if(box instanceof VBox)
                    ((VBox) box).setSpacing(n.getHeight()/20);
            }

        });
        SourceWriter.writeSources();

        hs.readHighscore();

    }


    /**
     * metode bundet til menyvalg "new game" og "load game"
     * @param e mouseevent, hvis museklikk på "load game" vil spill bli lastet fra fil.
     *          om feil oppstår popper en feilmelding opp.
     */
    public void enterGame(MouseEvent e) {
        soundController.pauseMusic();
        soundController.playSound(soundController.shockwave);
        menuRoot.setDisable(true);
        enterGameAnimation(earth, event -> {
            if(e.getPickResult().getIntersectedNode().getParent().equals(loadGame))
                gameLoad = true;
            try {
                Parent root = FXMLLoader.load(Main.class.getResource("graphics/Game.fxml"));
                e.getPickResult().getIntersectedNode().getScene().setRoot(root);
            } catch(IOException error) {
               error();
            }
            menuRoot.setDisable(false);
        });
    }

    /**
     * metode for visning av feilmelding
     */
    public void error() {
        soundController.playSound(soundController.alarm);
        menuBox.setVisible(false);
        fadeIn(Duration.seconds(0.5), errorBox);
        errorBox.setVisible(true);
        if(GameReader.unRecognizedData)
            errorText.setText("Unrecognized data in file!");
        else
            errorText.setText("Save file could not be found!");
    }


    /**
     * metode knyttet til menyvalg "settings".
     */
    public void enterOptions () {
        soundController.playSound(soundController.menu);
        menuBox.setVisible(false);
        fadeIn(Duration.seconds(0.5), optionBox);
        optionBox.setVisible(true);
    }

    /**
     * medote knyttet til menyvalg "Score board".
     * metoden viser de alle resultatene fra highscore listen som er 5 plasser.
     */
    public void toHighscore() {
        soundController.playSound(soundController.menu);
        menuBox.setVisible(false);
        highscoreBox.setVisible(true);
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < hs.getScores().size(); i++) {
            sb.append(i+1).append(": ").append(hs.getScores().get(i)).append("\n");
        }
        scoresText.setText(sb.toString());
    }

    /**
     * metode knyttet til menyvalg "exit"
     * Avslutter applikasjonen
     */
    public void exitApp () {
        System.exit(0);
    }

    /**
     * metode knyttet til menyvalg "how to play"
     */
    public void howToPlay() {
        controls.underlineProperty().bind(controlBox.visibleProperty());
        pickups.underlineProperty().bind(pickupBox.visibleProperty());
        soundController.playSound(soundController.menu);
        optionBox.setVisible(false);
        fadeIn(Duration.seconds(0.5), howToPlayBox);
        howToPlayBox.setVisible(true);
    }

    /**
     * metode knyttet til menyvalg "resolution"
     */
    public void resolutionOptions() {
        soundController.playSound(soundController.menu);
        optionBox.setVisible(false);
        fadeIn(Duration.seconds(0.5), resolutionBox);
        resolutionBox.setVisible(true);
    }

    /**
     * metode knyttet til menyvalg "Audio"
     */
    public void soundOptions() {
        soundController.playSound(soundController.menu);
        optionBox.setVisible(false);
        fadeIn(Duration.seconds(0.5), audioBox);
        audioBox.setVisible(true);
        soundController.sliderConnect(mSlider, eSlider);
    }

    /**
     * metode knyttet til "controls" i "how to play"
     */
    public void showControls() {
        pickupBox.setVisible(false);
        controlBox.setVisible(true);
    }

    /**
     * metode knyttet til "pickups" i "how to play"
     */
    public void showPickups() {
        controlBox.setVisible(false);
        pickupBox.setVisible(true);
    }

    /**
     * metode knyttet til "back" i alle menyvalg i optionBox
     * @param e museklikk
     */
    public void backToOptions(MouseEvent e) {
        soundController.playSound(soundController.menu);
        e.getPickResult().getIntersectedNode().getParent().getParent().setVisible(false);
        fadeIn(Duration.seconds(0.5), optionBox);
        optionBox.setVisible(true);
    }

    /**
     * metode knyttet til "back" i alle menyvalg i menuBox.
     * @param e museklikk
     */
    public void backToMain(MouseEvent e) {
        soundController.playSound(soundController.menu);
        e.getPickResult().getIntersectedNode().getParent().getParent().setVisible(false);
        menuBox.setVisible(true);
    }

    /**
     * metode knyttet til menyvalg "credits"
     * viser kilder
     */
    public void credits() {
        soundController.playSound(soundController.menu);
        StringBuilder sb = new StringBuilder();
        try(BufferedReader reader = new BufferedReader(new FileReader("sources.txt"))) {
            String input;
            while((input = reader.readLine()) != null) {
                sb.append(input).append("\n");
            }
        }catch(IOException ignored) {}
        creditText.setText(sb.toString());

        menuBox.setVisible(false);
        fadeIn(Duration.seconds(0.5), creditBox);
        fadeIn(Duration.seconds(0.5), menuBox);
        creditBox.setVisible(true);
    }

    /**
     * metode knyttet til valg av "resolution"
     * @param e
     */
    public void changeResolution(MouseEvent e) {
        final int MINH = 648;
        final int MINW= 1152;
        Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
        switch(e.getPickResult().getIntersectedNode().getParent().getAccessibleText()) {
            case "Full":
                window.setFullScreen(true);
                break;
            case "fullwindow":
                window.setFullScreen(false);
                window.setMaximized(true);
                break;
            case "window":
                window.setFullScreen(false);
                window.setMaximized(false);
                window.setWidth(MINW);
                window.setHeight(MINH);
                window.centerOnScreen();

                break;
        }
        window.setResizable(false);
    }


}
