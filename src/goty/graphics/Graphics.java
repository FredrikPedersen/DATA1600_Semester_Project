package goty.graphics;


import goty.Main;

import goty.controllers.GameController;
import goty.logic.*;
import goty.logic.gameobjects.AnimatedObject;
import goty.logic.gameobjects.Explosion;
import goty.logic.gameobjects.GameObject;
import goty.logic.gameobjects.Player;
import goty.logic.gameobjects.PulseBomb;
import goty.logic.gameobjects.enemies.Enemy;
import goty.logic.gameobjects.items.HealthItem;
import goty.logic.gameobjects.items.PulseBombItem;
import goty.logic.gameobjects.items.RapidFireItem;
import goty.logic.gameobjects.projectiles.Projectile;
import goty.logic.gameobjects.projectiles.RotatingProjectile;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import static goty.controllers.GameController.canvasWidth;
import static goty.controllers.GameController.canvasHeight;
import java.net.URISyntaxException;

/**
 * <h1>Graphics Main</h1>
 * <p>Klasse som tegner alle objektene, bakgrunnen og User Interfacen i spillet</p>
 *
 * @author Fredrik Pedersen, Ole Østvold
 * @version 1.0
 * @since 10. februar 2018
 */

public class Graphics {
    
    /** Objekt som brukes til å tegne på canvas */
    public GraphicsContext gc;
    
    /** Bakgrunnsbildet */
    private static Image background;
    /** Scroll-hastigheten på den animerte bakgrunnen */
    private double scrollSpeed = 0.3;
    
    /** Fonter til GUI */
    private Font font = Font.loadFont(getClass().getResourceAsStream("Games.ttf"), canvasHeight/30);
    private Font font2 = Font.loadFont(getClass().getResourceAsStream("Games.ttf"), canvasHeight/7);

    /**
     * Game-level objektet som inneholder referanser til objektene som tegnes.
     */
    public GameLevel gameLevel;
    
    /**
     * Hvorvidt en "game saved"-melding skal tegnes.
     */
    public boolean saveMessageOn;
    
    /**
     * Timer til "game-saved"-meldingen
     */
    public int saveMessageTimer;
    
    /**
     * Konstruerer et graphics-objekt.
     */
    public Graphics() {    
        try {
            background = new Image(Main.class.getResource("graphics/nightsky.jpg").toURI().toString());
        } catch (URISyntaxException e) {
            System.err.println("error loading background-image");
        }
    }
    
    /** Tegner alle objekter i spillet */
    public void draw() {
        drawBackground();
        drawCenter();
        drawHealthItems();
        drawRapidFire();
        drawPulseBombItem();
        drawEnergyShieldItem();
        drawPlayer();
        drawEnemies();
        drawProjectiles(gameLevel.playerProjectiles);
        drawProjectiles(gameLevel.enemyProjectiles);
        drawWhip();
        drawGui();
        drawExplosion(gameLevel.bombs);
        drawEnemyExplosion();
    }
    
    private void drawEnemyExplosion() {
        if (!gameLevel.explosions.isEmpty() ) {
            for (Explosion explosion : gameLevel.explosions) {
                double height = explosion.getHeight();
                double width = explosion.getWidth();
                double x = explosion.getX();
                double y = explosion.getY();
                int age = explosion.getAge();
                double lifeSpan = (double) explosion.getLifeSpan();
                double radius = explosion.getRadius();
                gc.setFill(explosion.getPaint());
                gc.fillOval(x + (age/lifeSpan) * radius, y, width, height);
                gc.fillOval(x - (age/lifeSpan) * radius, y, width, height);
                gc.fillOval(x, y + (age/lifeSpan) * radius, width, height);
                gc.fillOval(x, y - (age/lifeSpan) * radius, width, height);
            }
        }    
    }
    
    private void drawBackground() {
        gc.drawImage(background, 0, 0, canvasWidth, canvasHeight);
        gc.setGlobalAlpha(0.4);
        gc.drawImage(background, -GameLevel.time * scrollSpeed, 0, canvasWidth * 10, canvasHeight);
        gc.setGlobalAlpha(1);
    }

    private void drawHealthItems() {
        if (!gameLevel.healthItems.isEmpty() ) {
            for (HealthItem hp : gameLevel.healthItems) {
                drawAnimatedObject(hp);
            }
        }
    }

    private void drawRapidFire() {
        if (!gameLevel.rapidFire.isEmpty() ) {
            for (RapidFireItem rfi : gameLevel.rapidFire) {
                drawAnimatedObject(rfi);
            }
        }
    }

    private void drawPulseBombItem() {
        if(!gameLevel.pulseBombItems.isEmpty()) {
            for(PulseBombItem pbi : gameLevel.pulseBombItems) {
                drawAnimatedObject(pbi);
            }
        }
    }

    private void drawEnergyShieldItem() {
        if(gameLevel.esi != null)
            drawAnimatedObject(gameLevel.esi);
    }

    private void drawProjectiles(ArrayList<Projectile> projectiles) {
        if (!projectiles.isEmpty() ) {
            for (Projectile p : projectiles) {
                drawCircle(p);
            }
        }
    }
    
    private void drawWhip() {
        if (!gameLevel.enemyProjectiles.isEmpty() && gameLevel.getBossCount() % 3 == 2) {
            for (int i = 1; i < gameLevel.enemyProjectiles.size() && (gameLevel.enemyProjectiles.get(i) instanceof RotatingProjectile); i++) {
                Projectile projectile = gameLevel.enemyProjectiles.get(i);
                Projectile projectile2 = gameLevel.enemyProjectiles.get(i-1);
                gc.setStroke(Color.BLACK);
                gc.setLineWidth(5);
                gc.strokeLine(projectile.getX()+projectile.getWidth()/2, projectile.getY()+projectile.getHeight()/2,
                        projectile2.getX()+projectile.getWidth()/2, projectile2.getY()+projectile.getHeight()/2);
            } 
        }
    }

    private void drawEnemies() {
        if (!gameLevel.enemies.isEmpty() ) {
            for (Enemy enemy : gameLevel.enemies) {
                drawAnimatedObject(enemy);
            }
        }
    }
    
    private void drawCenter() {
        drawAnimatedObject(gameLevel.center);
    }
    
    private void drawPlayer() {
        drawAnimatedObject(gameLevel.player);
    }
    
    private void drawGui() {
        drawPoints();
        drawPlayerHealthBar();
        drawPulseBombText();
        if (GameLevel.time < 100) drawIntro();
        if (Player.rapidFireMode)drawRapidFireText();
        drawBossHealthBar();
        if (saveMessageOn) drawSaveMessage();
    }

    /** Tegner en melding om at spillet er blitt lagret */
    public void drawSaveMessage() {
        gc.setFont(font);
        gc.setFill(new Color(1,0,0,0.4));
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText("game saved", canvasWidth * 0.07, canvasHeight * 0.97);
        saveMessageTimer++;
        if (saveMessageTimer > 60) {
            saveMessageTimer = 0;
            saveMessageOn = false;
        }
    }
    
    
    private void drawIntro() {
        gc.setFont(font2);
        gc.setFill(Color.WHITE);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText("SAVE THE EARTH!", canvasWidth/2, canvasHeight/2);
    }

    private void drawPlayerHealthBar() {
        gc.setGlobalAlpha(0.8);
        gc.drawImage(gameLevel.playerHealthBar.getImage((gameLevel.player.getHealth()+9)/10), gameLevel.playerHealthBar.getX(), gameLevel.playerHealthBar.getY(), gameLevel.playerHealthBar.getWidth(), gameLevel.playerHealthBar.getHeight());
        gc.setGlobalAlpha(1);
    }
    
    private void drawBossHealthBar() {
        if (gameLevel.boss != null && gameLevel.boss.getAge() > 500) {
            gc.setFill(new Color(1,0,0,0.4));
            gc.fillRect(0, 0, canvasWidth * gameLevel.boss.getHealth()/gameLevel.boss.getMaxHealth(), canvasHeight * 0.02);
        }
    }
    
    private void drawRapidFireText() {
        gc.setFont(font);
        gc.setFill(Color.RED);
        gc.fillText("Rapid Fire ON!", canvasWidth/2, canvasHeight * 0.1);
    }

    private void drawPulseBombText() {
        gc.setFont(font);
        gc.setFill(Color.GREEN);
        gc.fillText("PulseBombs: " + gameLevel.bombs.size() + "/" + 3, canvasWidth/2, canvasHeight * 0.97);
    }
    
    private void drawPoints() {

        gc.setFill(gameLevel.points.getColor());
        gc.setStroke(gameLevel.points.getStroke());
        gc.setFont(font);
        gc.setTextAlign(TextAlignment.CENTER);
       
        gc.setGlobalAlpha(0.8);
        gc.fillText("Points: " + Enemy.getDeadEnemyCount(), canvasWidth/2, canvasHeight * 0.07);
        gc.fillText("HP: " + gameLevel.player.getHealth() + "/" + gameLevel.player.getMaxHealth(), canvasWidth * 0.076, canvasHeight * 0.07);
        gc.setGlobalAlpha(1);
    }
    
    private void drawAnimatedObject(AnimatedObject animatedObject) {
        gc.drawImage(animatedObject.getFrame(), animatedObject.getX(), animatedObject.getY(), animatedObject.getWidth(), animatedObject.getHeight());
    }
    
    private void drawCircle(GameObject gameObject) {
        gc.setFill(gameObject.getPaint());
        gc.fillOval(gameObject.getX(), gameObject.getY(), gameObject.getWidth(), gameObject.getHeight());
    }

    /** Tegner en eksplosjon rundt spilleren når PulseBomb aktiveres */
    public void drawExplosion(List<PulseBomb> bomb) {
        if(GameController.spacePressed && !(bomb.isEmpty())) {
            gc.setStroke(Color.WHITE);
            gc.setLineWidth(10);
            gc.strokeOval(bomb.get(0).getCenterX() - bomb.get(0).getD()/2, bomb.get(0).getCenterY() - bomb.get(0).getD()/2, bomb.get(0).getD(), bomb.get(0).getD());
        }
    }
}