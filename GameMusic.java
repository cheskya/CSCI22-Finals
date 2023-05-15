/**
 * This class handles the music and sounds used in the game. There
 * are corresponding methods for playing these.
 *
 * @author Arthur Jed Lluisma (223729)
 * @author Francesca Dominique J. Reyes (225318)
 * @version May 15, 2023
 */

/*
    I have not discussed the Java language code in my program 
    with anyone other than my instructor or the teaching assistants 
    assigned to this course.

    I have not used Java language code obtained from another student, 
    or any other unauthorized source, either modified or unmodified.

    If any Java language code or documentation used in my program 
    was obtained from another source, such as a textbook or website, 
    that has been clearly noted with a proper citation in the comments 
    of my program.
*/

// code inspired by:
// https://www.muradnabizade.com/backgroundmusicjava

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.*;

public class GameMusic {

    private static Clip clip;
    private static AudioInputStream audioInput;

    // all music files
    public File bgm, hit1, lose, win;

    // bgm sound from: https://youtu.be/MK6TXMsvgQg
    // hit1 sound from: https://youtu.be/3w-2gUSus34
    // lose sound from: https://youtu.be/o84vJH19toI
    // win sound from: https://youtu.be/_Z3ra0CxCE0

    /**
     * This constructor fetches all the music files used in the game.
     * It assigns them to the corresponding variables.
     */

    public GameMusic() {
        bgm = new File("Assets/Sounds/bgm.wav");
        hit1 = new File("Assets/Sounds/hit1.wav");
        lose = new File("Assets/Sounds/lose.wav");
        win = new File("Assets/Sounds/win.wav");
    }

    /**
     * This method starts the background music. It should be called
     * when the game starts. It loops the music continuously.
     */

    public void playBGM() {

        try {
            if(bgm.exists()) {
                if (clip != null) {
                    clip.stop();
                }
                
                audioInput = AudioSystem.getAudioInputStream(bgm);
                clip = AudioSystem.getClip();

                clip.open(audioInput);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            }
            else {
                System.out.println("Couldn't find 'bgm' music file.");
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    /**
     * This method plays the jingle for when when a player is hit. It only plays once.
     */

    public void playHit1Jingle() {

        try {
            if(hit1.exists()) {
                
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(hit1);
                Clip shortClip = AudioSystem.getClip();

                shortClip.open(audioInput);
                shortClip.start();
            }
            else {
                System.out.println("Couldn't find 'hit1' music file.");
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    /**
     * This method plays the jingle for when a player is hit for the last time
     * and loses. It only plays once.
     */

    public void playLoseJingle() {

        try {
            if(lose.exists()) {
                
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(lose);
                Clip shortClip = AudioSystem.getClip();

                shortClip.open(audioInput);
                shortClip.start();
            }
            else {
                System.out.println("Couldn't find 'lose' music file.");
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }

    /**
     * This method plays the jingle for when a player wins. It only plays once.
     */

    public void playWinJingle() {

        try {
            if(win.exists()) {
                
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(win);
                Clip shortClip = AudioSystem.getClip();

                shortClip.open(audioInput);
                shortClip.start();
            }
            else {
                System.out.println("Couldn't find 'lose' music file.");
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }

}