import java.io.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import javax.swing.*;
import javax.swing.plaf.metal.MetalPopupMenuSeparatorUI;
import javax.imageio.*;

// holds all graphic elements and etc. for level 1

// STAGE 1
// THEME: clouds
// ENEMIES: bird, mole, bunny

import java.util.*;

public class MapStage1 implements Map {

    private int numObjects = 3;
    
    private BufferedImage stage;
    private Player player;
    private MapStage1Obstacles obstacles;
    private ArrayList<MapObstacles> mapObstacles;

    public MapStage1() {
        try {
            stage = ImageIO.read( new File("Assets/Graphics/stage-1.png"));
        }
        catch (IOException ex) {
            System.out.println("Stage 1 map not found!");
        }

        player = new Player();
        obstacles = new MapStage1Obstacles();

        mapObstacles = new ArrayList<>();

        mapObstacles.add(obstacles.getObstacle1());
        mapObstacles.add(obstacles.getObstacle2());
        mapObstacles.add(obstacles.getObstacle3());
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(stage, 0, 0, null);
    }

    public void isPlayerColliding(Player player) {

        // will need method for Player class that returns player coordinates first
        // TODO: Watch the Drawing Tiles & Collision Detection videos in the playlist

    }
}