/**
 * This class creates the canvas of the game. It merges the screen
 * and the players together.
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

import java.awt.*;
import javax.swing.*;

public class GameCanvas extends JComponent {

    public int width, height;

    public Player p1, p2; // p1 is you, p2 is the other player

    public ScreenGame game;
    // originally, there were supposed to be different screens

    /**
     * This constructor creates all the needed objects and variables for
     * GameCanvas. It also sets the size of the canvas.
     * 
     * @param w     the width of the canvas
     * @param h     the height of the canvas
     */

    public GameCanvas(int w, int h) {

        width = w;
        height = h;
        setPreferredSize(new Dimension(width, height));

        game = new ScreenGame();
    
    }

    /**
     * This method is from the JComponent class. It takes a Graphics
     * object and draws the canvas accordingly.
     * 
     * @param g   the Graphics object used to draw the canvas
     */

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        game.draw(g2d);
        p2.draw(g2d);
        p1.draw(g2d);

    }

    /**
     * This method gets the 1st player in the game. Then, returns it.
     * 
     * @return the 1st player
     */

    public Player getPlayer1() {
        return p1;
    }

    /**
     * This method gets the 2nd player in the game. Then, returns it.
     * 
     * @return the 2nd player
     */

    public Player getPlayer2() {
        return p2;
    }

    /**
     * This method gets the current screen used in the game. Then, returns it.
     * 
     * @return the current screen
     */

    public ScreenGame getCurrentScreen() {
        return game;
    }

}