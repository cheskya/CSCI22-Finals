// the class that puts the game screen together for the player
// this includes the map and the hud

import java.io.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.*;

// the map may look relaxing but... it is a FIGHT TO THE DEATH !!!

public class GameScreen {

    private BufferedImage map;
    private GameHUD hud;

    // fetch elements included in the screen
    public GameScreen() {

        // fetch current HUD
        // hard-coded values for now, unsure if it'll be changed
        hud = new GameHUD(640, 0, 160, 640);

        // fetch current map
        try {
            map = ImageIO.read( new File("Assets/Graphics/Maps/map-stage1.png"));
        }
        catch (IOException ex) {
            System.out.println("Stage 1 Map not found!");
        }

    }

    // draw the screen elements
    public void draw(Graphics2D g2d) {
        // draw the map
        g2d.drawImage(map, 0, 0, null);
        // draw the HUD
        hud.draw(g2d);
    }
    
}