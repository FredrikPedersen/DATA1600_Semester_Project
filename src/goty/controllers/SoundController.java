package goty.controllers;

import goty.Main;
import goty.logic.GameLevel;
import java.net.URISyntaxException;
import javafx.scene.control.Slider;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import static javafx.scene.media.MediaPlayer.INDEFINITE;

/**
 * <h1>Sound Controller</h1>
 * <p>Kontroller som laster inn og muligjør avspilling av lydeffekter og musikk</p>
 *
 * @author Erik Larsen, Ole Østvold
 * @version 1.0
 * @since 10. februar 2018
 */


public class SoundController {
    
    /**
     * Audio-objekt som brukes til bakgrunnsmusikk
     */
    public Audio musicAudio;
    
    /**
     * Audioclip som benyttes til skytelyd. Skytelyden går i loop
     * og fungerer derfor på en annerledes måte enn de andre lydene.
     */
    public AudioClip loopAudio;
    
    // volum er en verdi mellom 0 og 1
    private static double soundEffectVolume = 1;
    private static double musicVolume = 1;
    
    /**
     * Hvorvidt musikken er mutet
     */
    public static boolean muteMusic;
    
    /**
     * Hvorvidt lydeffektene er mutet
     */
    public static boolean muteEffects;

    /**
     * Startpunktet til musikken
     */
    public Duration startpoint;

    // for å unngå for mange lyder oppå hverandre
    private int lastDeathScream;
    private int deathScreamDelay = 10; // antall frames mellom lydene
    
    /**
     * Skytelyd
     */
    public AudioClip zap;

    /**
     * Lyd som betyr at kloden er truffet.
     */
    public AudioClip alarm;

    /**
     * Lyd som betyr at spilleren er truffet.
     */
    public AudioClip deathscream;

    /**
     * Lyd når fiender dør.
     */
    public AudioClip splat;

    /**
     * Game-over lyd.
     */
    public AudioClip gameover;

    /**
     * Pause-lyd
     */
    public AudioClip pause;

    /**
     * Lyd når man plukker opp helse.
     */
    public AudioClip health;

    /**
     * Lyd når items spawner
     */
    public AudioClip spawnitem;

    /**
     * Lyd når man plukker opp rapidfire-item.
     */
    public AudioClip rapidfirepickup;

    /**
     * Lyd når stalker-enemy spawner.
     */
    public AudioClip stalker;

    /**
     * Meny-lyd
     */
    public AudioClip menu;

    /**
     * Pulsebomb-lyd.
     */
    public AudioClip shockwave;

    /**
     * Bakgrunnsmusikk i spillet.
     */
    public Audio bgMusic;

    /**
     * Bakgrunnsmusikk i menyen.
     */
    public Audio mainBgMusic;

    /**
     * Konstruerer lydkontrolleren.
     */
    public SoundController() {
        
        try {
            zap = new AudioClip(Main.class.getResource("Sounds/zap.wav").toURI().toString());
            alarm = new AudioClip(Main.class.getResource("Sounds/alarm.wav").toURI().toString());
            deathscream = new AudioClip(Main.class.getResource("Sounds/deathscream.wav").toURI().toString());
            splat = new AudioClip(Main.class.getResource("Sounds/splat.wav").toURI().toString());
            gameover = new AudioClip(Main.class.getResource("Sounds/gameover.wav").toURI().toString());
            pause = new AudioClip(Main.class.getResource("Sounds/pause.wav").toURI().toString());
            health = new AudioClip(Main.class.getResource("Sounds/health.wav").toURI().toString());
            spawnitem = new AudioClip(Main.class.getResource("Sounds/spawnitem.wav").toURI().toString());
            rapidfirepickup = new AudioClip(Main.class.getResource("Sounds/rapidfirepickup.mp3").toURI().toString());
            menu = new AudioClip(Main.class.getResource("Sounds/menu.wav").toURI().toString());
            bgMusic = new Audio(Main.class.getResource("Sounds/perturbator.mp3").toURI().toString());
            mainBgMusic = new Audio(Main.class.getResource("Sounds/mainmenu.mp3").toURI().toString());
            shockwave = new AudioClip(Main.class.getResource("Sounds/shockwave.wav").toURI().toString());
            stalker = new AudioClip(Main.class.getResource("Sounds/stalker.mp3").toURI().toString());
        } catch (URISyntaxException e) {
            System.err.println("error loading sounds");
        }

        startpoint = Duration.ZERO;
   }

    /**
     * Spiller av lydeffekt.
     * 
     * @param audioClip Lydeffekten som spilles av.
     */
    public void playSound(AudioClip audioClip) {
        if (!muteEffects && audioClip != null) {
            if (audioClip != deathscream) {
                audioClip.setVolume(soundEffectVolume);
                audioClip.play();
            }
            else if (GameLevel.time > lastDeathScream + deathScreamDelay) {
                audioClip.setVolume(soundEffectVolume);
                audioClip.play();
                lastDeathScream = GameLevel.time;
            }   
        }
    }
    
    /**
     * Spiller av lydeffekt som går i loop.
     * 
     * @param audioClip Lydeffekten som spilles av.
     */
    public void playLoopedSound(AudioClip audioClip) {
        if (!muteEffects && audioClip != null) {
            loopAudio = audioClip;
            loopAudio.setVolume(0.3 * soundEffectVolume); // justert volum pga høy skytelyd
            loopAudio.setCycleCount(INDEFINITE);
            loopAudio.setPriority(1);
            loopAudio.play();
        }
    }

    /**
     * Spiller av musikk som går i loop.
     * 
     * @param audio Lyden som spilles av.
     */
    public void playMusic(Audio audio) {
        if (audio !=null) {
            musicAudio = audio;
            musicAudio.mediaPlayer.setVolume(musicVolume);
            musicAudio.mediaPlayer.setCycleCount(INDEFINITE);

            musicAudio.mediaPlayer.setStartTime(startpoint);

            musicAudio.mediaPlayer.play();
        }
    }
    
    /**
     * Setter musikken på pause.
     */
    public void pauseMusic() {
        if (musicAudio != null) {
            musicAudio.mediaPlayer.pause();
        }     
    }
    
    /**
     * Starter musikken.
     */
    public void resumeMusic() {
        if (musicAudio != null) {
            musicAudio.mediaPlayer.play();
        }
    }
    
    /**
     * Stopper musikken.
     */
    public void endMusic() {
        if (musicAudio != null) musicAudio.mediaPlayer.dispose();
    }
    
    /**
     * Setter volumet på musikken.
     * 
     * @param volume Volumnivået. Verdien er mellom 0 og 1.
     */
    public void setMusicVolume(double volume) {
        musicVolume = volume;
        if (musicAudio != null) {
            musicAudio.mediaPlayer.setVolume(volume);
        }
    }
    
    /**
     * Muter musikken.
     * 
     */
    public void toggleMuteMusic() {
        if (musicAudio != null) {
            musicAudio.mediaPlayer.setMute(!muteMusic);
        }
    }

    /**
     * Knytter volumnivået til volumsliderne i spillmenyen.
     * 
     * @param m Slider til musikk.
     * @param e Slider til effekter.
     */
    public void sliderConnect(Slider m, Slider e) {
        m.setValue(musicVolume);
        m.valueProperty().addListener(observable -> setMusicVolume(m.getValue()));
        e.setValue(soundEffectVolume);
        e.valueProperty().addListener(observable -> soundEffectVolume = e.getValue());
    }

    /**
     * Lydklasse.
     */
    public class Audio {

        private Media media;

        /**
         * Objekt som brukes til å spille av en lyd.
         */
        public MediaPlayer mediaPlayer;

        /**
         * Konstruerer et audio-objekt.
         * @param s Streng som refererer til en lydfil.
         */
        public Audio(String s) {
            media = new Media(s);
            mediaPlayer = new MediaPlayer(media);
        }
    }
    
}
