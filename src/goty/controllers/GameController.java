package goty.controllers;

import goty.Main;
import goty.graphics.Graphics;
import goty.logic.GameLevel;
import goty.logic.Score;
import goty.logic.gameobjects.enemies.Enemy;
import goty.logic.reader.GameReader;
import goty.logic.reader.HighScore;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.util.Duration;

import java.io.IOException;



/**
 * <h1>Game Controller</h1>
 * <p>Kontroller med metoder, logikk og innlastning av FXMLene til selve spillet</p>
 *
 * @author Erik Larsen, Ole Østvold
 * @version 1.0
 * @since 10. februar 2018
 */

public class GameController {

    //Containers
    @FXML StackPane gameRoot; @FXML Canvas canvas;
    @FXML VBox pauseContainer; @FXML VBox controlBox;
    @FXML VBox audioBox; @FXML VBox highscoreSubmitBox;
    @FXML VBox gameOverMenu;
    //Sliders
    @FXML Slider mSlider; @FXML Slider eSlider;
    //Buttons
    @FXML Button submitButton; @FXML Button skipButton;
    //text
    @FXML TextField submitField;

    // frames per second
    private static final double FPS = 60;

    // skjermbredde

    public static double canvasWidth = Screen.getPrimary().getBounds().getWidth();
    public static double canvasHeight = Screen.getPrimary().getBounds().getHeight();

    // museposisjon
    public static double mouseX;
    public static double mouseY;

    // museknapp
    public static boolean isMouseButtonDown;
    //spacebar
    public static boolean spacePressed;

    // hovedklasse for å muliggjøre animasjon
    private Timeline timeline;

    // objekt som styrer grafikk
   private Graphics graphics = new Graphics();

    // objekt som styrer lyd
    private SoundController soundController = new SoundController();
    
    // objekt som styrer gamepad
    private Gamepad gamepad = new Gamepad();
    private boolean triggerActive;

    // objekt som styrer spill-logikk
    private GameLevel gameLevel = new GameLevel();

    // PUNTOS
    private Score score;
    private HighScore hs = HighScore.HIGH_SCORE;


    /**
     * metoden blir kalt når fxml filen blir lastet inn.
     * @throws IOException kaster eventuel feil tilbake til metoden som starter spillet.
     */

    public void initialize() throws IOException {

        gameRoot.setStyle("-fx-background-image: null;");
        graphics.gc = canvas.getGraphicsContext2D();
        graphics.gameLevel = gameLevel;
        gameLevel.soundController = soundController;
        gameLevel.gameController = this;

        //setter størrelse på canvas lik skjermstørrelse/vindustørrelse

        canvas.setWidth(canvasWidth);
        canvas.setHeight(canvasHeight);

        spacePressed = false;

        canvas.setCursor(Cursor.CROSSHAIR);

        gameLevel.initializeLevel();

        Duration duration = Duration.millis(1000/FPS);
        KeyFrame keyframe = new KeyFrame(duration, e -> updateGame());
        timeline = new Timeline();
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.getKeyFrames().add(keyframe);
        timeline.play();
    }

    // metode som kjører 60 ganger i sekunder (= FPS).
    private void updateGame() {
        gameLevel.update(); // oppdaterer spillobjekter
        graphics.draw(); // tegner objekter
        
        if (Gamepad.isGamepadConnected) getGamepadInput();
    }
    
    private void getGamepadInput() {
        gamepad.poll();
        
        // emulerer space-knapp
        if (Gamepad.zAxis < -0.8 && !spacePressed) {
            if(!(gameLevel.bombs.isEmpty())) {
                spacePressed = true;
                gameLevel.bombs.get(0).setCenterLoc(gameLevel.player.getX() + gameLevel.player.getWidth() / 2,
                        gameLevel.player.getY() + gameLevel.player.getHeight() / 2);
                soundController.playSound(soundController.shockwave);
            }
        }
        
        // emulerer museknapp
        if (Gamepad.rxAxis != 0 && Gamepad.ryAxis != 0 && !triggerActive) {
           soundController.playLoopedSound(soundController.zap);
           triggerActive = true;
        } else if (Gamepad.rxAxis == 0 && Gamepad.ryAxis == 0 && triggerActive) {
            if (soundController.loopAudio != null) soundController.loopAudio.stop();
            triggerActive = false;
        }        
    }
    

    /**
     * Stopper spillet og viser enten en Game-over meny eller en
     * Highscore-submit-boks.
     */
    public void enterGameOverMenu() {
        timeline.stop();
        canvas.setCursor(Cursor.DEFAULT);
        score = new Score(Enemy.getDeadEnemyCount(), GameLevel.time);
        if(score.compareTo(hs.getScores().get(hs.MAXCAP-1)) > 0) {
            highscoreSubmitBox.setVisible(true);
        } else {
            gameOverMenu.setVisible(true);
        }
    }

    /**
     * Setter spillet på pause og viser en pausemeny.
     */
    private void enterPauseMenu() {
        gameLevel.player.stopMotion();
        timeline.pause();
        soundController.pauseMusic();
        if (soundController.loopAudio != null) soundController.loopAudio.stop();
        soundController.playSound(soundController.pause);
        canvas.setCursor(Cursor.DEFAULT);
        pauseContainer.setVisible(true);
    }

    /**
     * metode som knyttet til "save" knappen
     */
    public void saveScoreName() {
        score.setName(submitField.getText());
        hs.addScore(score);
        highscoreSubmitBox.setVisible(false);
        gameOverMenu.setVisible(true);
    }

    /**
     * metode knyttet til "skip" knappen
     */
    public void skipHighscore() {
        highscoreSubmitBox.setVisible(false);
        gameOverMenu.setVisible(true);
    }

    /**
     * metode knyttet til menyvalg "resume"
     */
    public void resumeGame() {
        timeline.play();
        soundController.resumeMusic();
        pauseContainer.setVisible(false);
        canvas.setCursor(Cursor.CROSSHAIR);
    }

    /**
     * metode knyttet til menyvalg "restart"
     * @throws IOException
     */
    public void restart() throws IOException {
        soundController.endMusic();
        Parent root = FXMLLoader.load(Main.class.getResource("graphics/Game.fxml"));
        gameRoot.getScene().setRoot(root);
    }

    /**
     * metode knyttet til menyvalg "exit to menu"
     * @throws IOException
     */
    public void exitGame() throws IOException {
        Parent root = FXMLLoader.load(Main.class.getResource("graphics/Main.fxml"));
        gameRoot.getScene().setRoot(root);
    }

    /**
     * metode knyttet til menyvalg "controls"
     */
    public void showControls() {
        pauseContainer.setVisible(false);
        controlBox.setVisible(true);

    }

    /**
     * metode knyttet til "back" i alle menyvalg i pausemenyen
     * @param e museklikk
     */
    public void backToPause(MouseEvent e) {
        e.getPickResult().getIntersectedNode().getParent().getParent().setVisible(false);
        pauseContainer.setVisible(true);
    }

    /**
     * metode knyttet til menyvalg "Audio"
     */
    public void sound() {
        pauseContainer.setVisible(false);
        audioBox.setVisible(true);
        soundController.sliderConnect(mSlider, eSlider);
    }

    /**
     * metode knyttet til "save and exit"
     */
    public void saveClick() {
        GameReader.saveToFile(gameLevel);
        try {
            exitGame();
        } catch (IOException ignored) {}
    }


    /**
     * Registrerer tastetrykk
     *
     */
    public void handlePressedKeyEvent(KeyEvent e) {

        // sjekker hvilken tast
        KeyCode key = e.getCode();

        if (timeline.getStatus() == Animation.Status.RUNNING) {

            // bevegelsestaster
            if(key == KeyCode.LEFT || key == KeyCode.A) {
                gameLevel.player.setLeft(true);
            }

            if(key == KeyCode.RIGHT || key == KeyCode.D) {
                gameLevel.player.setRight(true);
            }
            if(key == KeyCode.UP || key == KeyCode.W) {
                gameLevel.player.setUp(true);
            }
            if(key == KeyCode.DOWN || key == KeyCode.S) {
                gameLevel.player.setDown(true);
            }

            // pause
            if(key == KeyCode.ESCAPE) {
                enterPauseMenu();
            }

            if(key == KeyCode.SPACE) {
                if(!(gameLevel.bombs.isEmpty())) {
                    spacePressed = true;
                    gameLevel.bombs.get(0).setCenterLoc(gameLevel.player.getX() + gameLevel.player.getWidth() / 2,
                            gameLevel.player.getY() + gameLevel.player.getHeight() / 2);
                    soundController.playSound(soundController.shockwave);
                }
            }

            // save
            if (key == KeyCode.F5) {
                GameReader.saveToFile(gameLevel);
                graphics.saveMessageOn = true;
            }
            
        } else {
            if(key == KeyCode.ESCAPE) {
                resumeGame();
            }
        }
    }

    /**
     * Registrerer når man slipper en tast
     *
     */
    public void handleReleasedKeyEvent(KeyEvent e) {

        if (timeline.getStatus() == Animation.Status.RUNNING) {

            if(e.getCode() == KeyCode.LEFT || e.getCode() == KeyCode.A) {
                gameLevel.player.setLeft(false);
            }

            if(e.getCode() == KeyCode.RIGHT || e.getCode() == KeyCode.D) {
                gameLevel.player.setRight(false);
            }

            if(e.getCode() == KeyCode.UP || e.getCode() == KeyCode.W) {
                gameLevel.player.setUp(false);
            }

            if(e.getCode() == KeyCode.DOWN || e.getCode() == KeyCode.S) {
                gameLevel.player.setDown(false);
            }
        }
    }

    /**
     * Registrerer bevegelse med mus.
     */
    public void handleMouseMovedEvent(MouseEvent e) {
        // registrerer hvor pekeren er
        mouseX = e.getX();
        mouseY = e.getY();
    }


    /**
     * Registrerer musebevegelse mens museknapp er trykket inn.
     */
    public void handleMouseDraggedEvent(MouseEvent e) {
        // registrerer hvor pekeren er
        mouseX = e.getX();
        mouseY = e.getY();

    }

     /**
     * Registrerer museklikk.
     */
    public void handleMousePressedEvent() {
        isMouseButtonDown = true;
        if (timeline.getStatus() == Animation.Status.RUNNING) {
            soundController.playLoopedSound(soundController.zap);
        }
    }

    /**
     * Registrerer når man slipper museknapp.
     */
    public void handleMouseReleasedEvent() {
        isMouseButtonDown = false;
        if (timeline.getStatus() == Animation.Status.RUNNING) {
            if (soundController.loopAudio != null) soundController.loopAudio.stop();
        }
    }

}
