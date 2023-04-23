import java.io.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.*;

// holds all graphic elements and etc. for level 1

// STAGE 1
// THEME: clouds
// ENEMIES: bird, mole, bunny

public class MapStage1 implements Map {

    private BufferedImage stage;

    public MapStage1() {
        try {
            stage = ImageIO.read( new File("Assets/Graphics/stage-1.png"));
        }
        catch (IOException ex) {
            System.out.println("Stage 1 map not found!");
        }
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(stage, 0, 0, null);
    }

    public boolean isPlayerColliding(Player player) {

        // will need method for Player class that returns player coordinates first
        return true;

    }
    
}