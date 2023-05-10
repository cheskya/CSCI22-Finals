// screen for stage 1

import java.io.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.*;

// STAGE 1
// THEME: bright cloudy day
// ENEMIES: bird, mole, bunny

public class ScreenStage1 implements Screen {

    private BufferedImage map;
    private GameHUD hud;

    // fetch elements included in the screen
    public ScreenStage1() {

        // fetch current HUD
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

    // public boolean isPlayerColliding(Player player) {

    //     // will need method for Player class that returns player coordinates first
    //     return true;

    // }
    
}