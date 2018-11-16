package goty.logic;

import goty.controllers.GameController;
import goty.controllers.Gamepad;
import goty.controllers.SoundController;
import goty.logic.gameobjects.*;
import goty.logic.gameobjects.enemies.*;
import goty.logic.gameobjects.items.EnergyShieldItem;
import goty.logic.gameobjects.items.HealthItem;
import goty.logic.gameobjects.items.PulseBombItem;
import goty.logic.gameobjects.items.RapidFireItem;
import goty.logic.gameobjects.projectiles.PlayerProjectile;
import goty.logic.gameobjects.projectiles.Projectile;
import goty.logic.reader.GameReader;

import java.io.IOException;
import java.util.ArrayList;

import static goty.controllers.Controller.gameLoad;
import static goty.controllers.GameController.canvasWidth;

/**
 * <h1>Game Level</h1>
 * <p>Game level klassen oppretter alle objektene som skal være i spillet, spawner og oppdaterer dem, holder rede på om objekter kolliderer,
 * når spillet skal gå over i en boss-fase, setter spillet i game over-status når spilleren dør, og bestemmer hva de forksjellige pick-upsene gjør.</p>
 *
 * @author Erik Larsen, Fredrik Pedersen, Ole Østvold
 * @version 1.0
 * @since 22. april 2018
 */

public class GameLevel {

    /** Spillerobjektet */
    public Player player = new Player();
    /** Poengobjektet */
    public Points points = new Points();
    /** Center/Jordklode-objektet */
    public Center center = new Center();
    /** Objekt til spillerens helsebar */
    public PlayerHealthBar playerHealthBar = new PlayerHealthBar();
    /** Boss-objektet */
    public Enemy boss;
    /** Objekt for spillerens prosjektiler */
    public ArrayList<Projectile> playerProjectiles = new ArrayList<>(50);
    /** Objekt for fiendeprosjektiler */
    public ArrayList<Projectile> enemyProjectiles = new ArrayList<>();
    /** Objekt for Health-pickups */
    public ArrayList<HealthItem> healthItems = new ArrayList<>();
    /** Objekt for Rapid Fire pickups */
    public ArrayList<RapidFireItem> rapidFire = new ArrayList<>();
    /** Objekt for fiender */
    public ArrayList<Enemy> enemies = new ArrayList<>();
    /**  Objekt med pulsebomb-items */
    public ArrayList<PulseBombItem> pulseBombItems = new ArrayList<>();
    /** Objekt med pulsbomber */
    public ArrayList<PulseBomb> bombs = new ArrayList<>();
    /** Objekt med partikkel-eksplosjoner */
    public ArrayList<Explosion> explosions = new ArrayList<>();
    /** Objekt for itemet som fyller på jordklodens skjold */
    public EnergyShieldItem esi;
    
    /** Antall frames siden start av spillet, ikke inkludert pause */
    public static int time;
    
    /** Objekt som styrer lyd */
    public SoundController soundController;

    /** Objekt som styrer spillmenyen */
    public GameController gameController;

    /** Hvorvidt man er i en bossfight */
    public boolean bossFight;
    /** Teller antall bosser som har spawnet */
    private int bossCount;

    /**
     * Initierer spillet. Loader eventuelt spillet fra fil.
     * @throws IOException kaster feil til gamecontroller
     */

    public void initializeLevel() throws IOException {

        if(gameLoad) {
            gameLoad = false;
            GameReader.loadObjects(this);
        } else {
            time = 0;
            Enemy.setDeadEnemyCount(0);
            Enemy.setEnemyCount(0);
            Enemy.setSpawnRate(Enemy.getStartSpawnRate());
        }
        soundController.playMusic(soundController.bgMusic);

    }

    /** Oppdaterer spillobjekter */
    public void update() {

        // spawner fiender og prosjektiler
        if (time > 180) {
            enemyFire();
            spawnHealthItems();
            spawnRapidFireItems();
            spawnPulseBombItem();
            spawnEnemies();
        }
        playerFire();

        // oppdaterer posisjon/tilstand til spillobjekter
        updateProjectiles(playerProjectiles);
        updateProjectiles(enemyProjectiles);
        updateEnemies();
        updatePlayer();
        updateHealthItems();
        updateRapidFireItems();
        updatePulseBombItem();
        if(activatePulseBomb())
            updatepulseBomb();
        updateExplosions();
        updateEnergyShieldItem();

        time++;

    }

    /** metode for å kontrollere at vanlige fiender spawner */
    private void standardCheck() {
        if (Enemy.getDeadEnemyCount() == Enemy.getEnemyCount()) {
            bossFight = false;
            boss = null;
        }
    }

    /** Metode som spawner bosser og minker hvor mange frames det er mellom hver vanlige fiende som spawner */
    private Enemy spawnBoss() {
        Enemy enemy;
        switch (bossCount % 3){
            case 0: enemy = new BigBoss(); break;
            case 1: enemy = new AngryBoss(); break;
            case 2: enemy = new ShieldBoss(); break;
            default: enemy = new BigBoss(); break;
        }
        boss = enemy;
        bossCount++;
        Enemy.setSpawnRate(Enemy.getSpawnRate() - 10);        
        return enemy;
    }

    /** Metode som spawner vanlige fiender og sjekker om en boss skal spawne */
    private void spawnEnemies() {
        if (!bossFight && time % Enemy.getSpawnRate() == 0) {
            
            Enemy e;
            if (Enemy.getEnemyCount() % Enemy.bossRatio == Enemy.bossRatio-1) {
                e = spawnBoss();
                bossFight = true;
            }
            else if (Enemy.getEnemyCount() % Enemy.stalkerEnemyRatio == Enemy.stalkerEnemyRatio-1) {
                e = new StalkerEnemy();
                soundController.playSound(soundController.stalker);
            } else if (Enemy.getEnemyCount() % Enemy.bigEnemyRatio == Enemy.bigEnemyRatio-1) {
                e = new BigEnemy();
            } else e = new RegularEnemy();
            enemies.add(e);
        }
        standardCheck();
    }

    /** spawner Health-pickups */
    private void spawnHealthItems() {
        if (time % HealthItem.getSpawnRate() == 0) {
            HealthItem hp = new HealthItem();
            healthItems.add(hp);
            soundController.playSound(soundController.spawnitem);
        }
    }

    /** Oppdaterer Health-pickups */
    private void updateHealthItems() {
        for (HealthItem item : healthItems) {
            item.update();
        }
    }

    /** Oppdaterer Energy Shield-pickups */
    private void updateEnergyShieldItem() {
        if(esi != null) {
            if (esi.getAge() > 1000)
                esi = null;
            else
                esi.update();
        }
    }

    /** spawner RapidFire-pickups */
    private void spawnRapidFireItems() {
        if (time % RapidFireItem.getSpawnRate() == 0) {
            RapidFireItem rfi = new RapidFireItem();
            rapidFire.add(rfi);
            soundController.playSound(soundController.spawnitem);
        }
    }

    /** Oppdaterer RapidFire-pickups */
    private void updateRapidFireItems() {
        for (int i = 0; i < rapidFire.size(); i++) {
            rapidFire.get(i).update();
        }
    }

    /** Metode som gjør at spilleren skyter når venstre musetast trykkes inn */
    private void playerFire() {
        if (GameController.isMouseButtonDown || (Gamepad.rxAxis != 0 && Gamepad.ryAxis != 0)) {

            if (player.getAge() % player.getRateOfFire() == 0) {
                PlayerProjectile p = new PlayerProjectile(player);
                playerProjectiles.add(p);
            }
        }
    }

    /** Metode som gjør at fiender skyter */
    private void enemyFire() {
        for (Enemy enemy : enemies) {
            if (enemy.isFiring() && enemy.getAge() % enemy.getRateOfFire() == enemy.getRateOfFire()-1) {
                enemy.fire(player, enemyProjectiles);
            }
        }
    }
    
    private void updateExplosions() { 
        for(int i = 0; i < explosions.size(); i++) {
            explosions.get(i).update();
            if (explosions.get(i).getAge() > explosions.get(i).getLifeSpan()) explosions.remove(i);
        }    
    }

    /** Metode som oppdaterer spilleren */
    private void updatePlayer(){
        player.update();

        // sjekker om spiller blir truffet av fiendeprosjektiler
        for (Projectile projectile : enemyProjectiles) {
            if (isColliding(player, projectile)) {
                soundController.playSound(soundController.deathscream);
                player.setHealth(player.getHealth() - projectile.getDamage());
            }
        }

        // sjekker om fiender treffer spiller
        for (Enemy enemy : enemies) {
            if (isColliding(enemy, player) ) {
                soundController.playSound(soundController.deathscream);
                player.setHealth(player.getHealth()-1);
            }
        }

        // om spiller plukker opp hp
        for (int i = 0; i < healthItems.size(); i++) {
            HealthItem item = healthItems.get(i);
            if (isColliding(player, item) ) {
                player.setHealth(player.getHealth() + item.getHealthPoints());
                soundController.playSound(soundController.health);
                healthItems.remove(item);
            }
        }

        //om spiller plukker opp rapid fire
        for (int i = 0; i < rapidFire.size(); i++) {
            RapidFireItem rfi = rapidFire.get(i);
            if (isColliding(player, rfi) ) {
                player.activateRapidFireMode();
                player.setRateOfFire(player.getRateOfFire() - rfi.getFireRateIncrease());
                soundController.playSound(soundController.rapidfirepickup);
                rapidFire.remove(rfi);
            }
        }

        //om spiller plukker opp Pulsebomb
        for(int i = 0; i < pulseBombItems.size(); i++) {
            PulseBombItem pbi = pulseBombItems.get(i);
            if(isColliding(player, pbi)) {
                if(bombs.size()< 3) {
                    PulseBomb pb = new PulseBomb();
                    bombs.add(pb);
                }
                soundController.playSound(soundController.rapidfirepickup);
                pulseBombItems.remove(pbi);
            }
        }

        // om spiller plukker opp EnergyShieldItem
        if(esi != null && isColliding(player, esi)){
            soundController.playSound(soundController.health);
            center.setHealth(4);
            esi = null;
        }

        // Game Over
        if (player.getHealth() < 1 || center.getHealth() < 1) {
            soundController.endMusic();
            soundController.playSound(soundController.gameover);
            if (soundController.loopAudio != null) soundController.loopAudio.stop();
            gameController.enterGameOverMenu();
        }
}

    /** Oppdaterer posisjon/tilstand til prosjektiler */
    private void updateProjectiles(ArrayList<Projectile> projectiles) {

        for (int i = 0; i < projectiles.size(); i++) {
            Projectile projectile = projectiles.get(i);

            if (projectile.getAge() > projectile.getLifeSpan()) {
                projectiles.remove(projectile);
            } else {
                projectile.update();
            }
        }
    }

    /** Oppdaterer posisjon/tilstand til fiender */
    private void updateEnemies() {
        for (int i = 0; i < enemies.size(); i++) {
            Enemy enemy = enemies.get(i);
            enemy.update(player);

            // fjerner fiender om de treffer sentrum
            if (isColliding(enemy, center)) {
                explosions.add(new Explosion(enemy));
                enemies.remove(enemy);
                soundController.playSound(soundController.alarm);
                center.setHealth(center.getHealth() - 1);
                Enemy.setDeadEnemyCount(Enemy.getDeadEnemyCount() + 1);
            }
            else if(activatePulseBomb()) {
                if(isBombColliding(bombs.get(0), enemy)) {
                    enemy.setHealth(enemy.getHealth() - 1);
                    enemy.setShieldActive(false);
                    if (enemy.getHealth() == 0) {
                        explosions.add(new Explosion(enemy));
                        enemies.remove(enemy);
                        Enemy.setDeadEnemyCount(Enemy.getDeadEnemyCount() + 1);
                    }
                }
            }

            // fjerner fiender som blir truffet av prosjektiler
            else {
                for (int j = 0; j < playerProjectiles.size(); j++) {

                    if (isColliding(enemy, playerProjectiles.get(j)) ) {
                        if (enemy.isShieldActive()) playerProjectiles.get(j).invertDirection();
                        else {
                            enemy.setHealth(enemy.getHealth()-1);
                            playerProjectiles.remove(j);
                            if (enemy.getHealth() == 0) {
                                if(enemy instanceof BigBoss || enemy instanceof AngryBoss)
                                    esi = new EnergyShieldItem(enemy.getX() + enemy.getWidth()/2,
                                            enemy.getY() + enemy.getHeight()/2);
                                else if(enemy instanceof ShieldBoss) // awkward position of the shield boss, temporary solution!
                                    esi = new EnergyShieldItem(enemy.getX() + enemy.getWidth()/2,
                                            enemy.getY());
                                explosions.add(new Explosion(enemy));
                                enemies.remove(enemy);
                                soundController.playSound(soundController.splat);
                                Enemy.setDeadEnemyCount(Enemy.getDeadEnemyCount() + 1);
                            }
                        }
                    }
                }
            }
        }
    }

    /** Metode som beregner hvorvidt spillobjekter kolliderer */
    private boolean isColliding(GameObject gameObject1, GameObject gameObject2) {

        return gameObject1.getX() + gameObject1.getWidth() > gameObject2.getX()
                && gameObject1.getX() - gameObject2.getWidth() < gameObject2.getX()
                && gameObject1.getY() + gameObject1.getHeight() > gameObject2.getY()
                && gameObject1.getY() - gameObject2.getHeight() < gameObject2.getY();
    }

    /**
     * metode som tester som enemy kolliderer med pulsebomb eksplosjon
     * @param pulseBomb bomben
     * @param gameObject fiende objekt
     * @return boolean
     */
    private boolean isBombColliding(PulseBomb pulseBomb, GameObject gameObject) {
        return pulseBomb.getD()/2 > Math.abs(Math.sqrt(Math.pow((gameObject.getX() + (gameObject.getWidth()/2)) - pulseBomb.getCenterX(), 2) +
                Math.pow((gameObject.getY() + (gameObject.getHeight()/2)) - pulseBomb.getCenterY(), 2)));

    }

    /**
     * oppdaterer eksplosjon av PulseBomb
     */
    private void updatepulseBomb() {
        if(canvasWidth > bombs.get(0).getD())
            bombs.get(0).update();
        else {
            bombs.remove(0);
            GameController.spacePressed = false;
        }
    }

    /**
     * aktiverer PulseBomb
     * @return boolean
     */
    private boolean activatePulseBomb() {
        if(GameController.spacePressed) {
            return !bombs.isEmpty();
        } else
            return false;

    }

    /**
     * oppdatering for "animasjon" til PulseBombItem
     */
    private void updatePulseBombItem() {
        for (PulseBombItem pbi : pulseBombItems) {
            pbi.update();
        }
    }

    /**
     * spawner pulsebombitem
     */
    private void spawnPulseBombItem() {
        if(time % PulseBombItem.getSpawnRate() == 0) {
            PulseBombItem pb = new PulseBombItem();
            pulseBombItems.add(pb);
            soundController.playSound(soundController.spawnitem);
        }
    }

    /**
     * getter for bosscount
     * @return int bosscount
     */
    public int getBossCount() {
        return bossCount;
    }

    /**
     * setter for bosscount
     * @param bossCount int
     */
    public void setBossCount(int bossCount) {
        this.bossCount = bossCount;
    }
}
