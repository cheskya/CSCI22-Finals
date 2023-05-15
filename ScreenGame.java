/**
 * This class creates the main screen of the game. It does so by merging
 * the HUD and the map together.
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

import java.io.*;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;

// theme of map: dream-like, cloudy

public class ScreenGame implements DrawingObject {

    private BufferedImage map;
    private GameHUD hud;

    /**
     * This constructor creates a new HUD object to be used by the screen.
     * It also fetches the map of the game.
     */

    public ScreenGame() {

        // fetch the HUD
        hud = new GameHUD(640, 0, 160, 640);

        // fetch the current map
        try {
            map = ImageIO.read( new File("Assets/Graphics/Maps/map-stage1.png"));
        }
        catch (IOException ex) {
            System.out.println("Stage 1 Map not found!");
        }

    }

    // --- draw method ---

    /**
     * This method is from the DrawingObject interface. It takes a Graphics2D
     * object and draws the HUD and the map accordingly.
     * 
     * @param g2d   the Graphics2D object used to draw the screen elements
     */

    @Override
    public void draw(Graphics2D g2d) {
        // draw the map
        g2d.drawImage(map, 0, 0, null);
        // draw the HUD
        hud.draw(g2d);
    }

    // --- misc methods ---

    /**
     * This method returns the HUD being used by the screen.
     * This is mostly for allowing other classes to access various elements in the HUD
     * of the current screen.
     * 
     * @return  the current GameHUD object used by the screen
     */

    public GameHUD getHUD() {
        return hud;
    }
    
}