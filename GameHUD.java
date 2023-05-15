/**
 * This class handles the HUD of the game. It displays information like
 * lives and winning status to the user. It has the neccessary methods
 * to get and set this information.
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
import java.awt.geom.*;
import java.awt.image.*;
import javax.imageio.*;

public class GameHUD implements DrawingObject {

    int x, y;
    int width, height;

    Font pixelFont;

    // the terms "playerSister" and "playerBrother" are used here instead of "p1" and "p2"
    // for easier categorizing according to sprite...

    // sprites
    private BufferedImage playerSister, playerBrother;
    private BufferedImage playerSisterIdle, playerBrotherIdle, playerSisterWin, playerBrotherWin, playerSisterLose, playerBrotherLose;
    private BufferedImage playerSisterLife, playerBrotherLife;
    private BufferedImage playerSister5Life, playerSister4Life, playerSister3Life, playerSister2Life, playerSister1Life, playerSister0Life;
    private BufferedImage playerBrother5Life, playerBrother4Life, playerBrother3Life, playerBrother2Life, playerBrother1Life, playerBrother0Life;
    
    /**
     * This constructor assigns the passed values to their corresponding variables.
     * It also fetches the requires sprites for the HUD.
     * 
     * @param x     x-coordinate of the HUD
     * @param y     y-coordinate of the HUD
     * @param w     width of the HUD
     * @param h     height of the HUD
     */
    
    public GameHUD(int x, int y, int w, int h) {
        
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;

        // pixel font name is "Arcade Pix"
        // font taken from https://www.dafont.com/arcadepix.font

        // code inspired by https://stackoverflow.com/questions/5652344/how-can-i-use-a-custom-font-in-java
        // further modification from https://stackoverflow.com/questions/24800886/how-to-import-a-custom-java-awt-font-from-a-font-family-with-multiple-ttf-files

        // fetch pixel font
        try {
            pixelFont = Font.createFont(Font.TRUETYPE_FONT, new File("Assets/Fonts/Pixel.ttf")).deriveFont(30f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(pixelFont); //register the font
        }
        catch (IOException ex) {
            System.out.println("Pixel Font not found!");
        }
        catch (FontFormatException ex) {
            System.out.println("FontFormatException from GameHUD!");
        }

        // fetch player icons
        try {
            playerSisterIdle = ImageIO.read(new File("Assets/Graphics/HUD/Sister/ui-sister1.png"));
            playerSisterWin = ImageIO.read(new File("Assets/Graphics/HUD/Sister/ui-sister2.png"));
            playerSisterLose = ImageIO.read(new File("Assets/Graphics/HUD/Sister/ui-sister3.png"));
            playerBrotherIdle = ImageIO.read(new File("Assets/Graphics/HUD/Brother/ui-brother1.png"));
            playerBrotherWin = ImageIO.read(new File("Assets/Graphics/HUD/Brother/ui-brother2.png"));
            playerBrotherLose = ImageIO.read(new File("Assets/Graphics/HUD/Brother/ui-brother3.png"));
        }
        catch (IOException ex) {
            System.out.println("Player Icons for HUD not found!");
        }

        // fetch lives icons
        try {
            playerSister5Life = ImageIO.read(new File("Assets/Graphics/HUD/Sister/ui-sisterhearts1.png"));
            playerSister4Life = ImageIO.read(new File("Assets/Graphics/HUD/Sister/ui-sisterhearts2.png"));
            playerSister3Life = ImageIO.read(new File("Assets/Graphics/HUD/Sister/ui-sisterhearts3.png"));
            playerSister2Life = ImageIO.read(new File("Assets/Graphics/HUD/Sister/ui-sisterhearts4.png"));
            playerSister1Life = ImageIO.read(new File("Assets/Graphics/HUD/Sister/ui-sisterhearts5.png"));
            playerSister0Life = ImageIO.read(new File("Assets/Graphics/HUD/Sister/ui-sisterhearts6.png"));
            playerBrother5Life = ImageIO.read(new File("Assets/Graphics/HUD/Brother/ui-brotherhearts1.png"));
            playerBrother4Life = ImageIO.read(new File("Assets/Graphics/HUD/Brother/ui-brotherhearts2.png"));
            playerBrother3Life = ImageIO.read(new File("Assets/Graphics/HUD/Brother/ui-brotherhearts3.png"));
            playerBrother2Life = ImageIO.read(new File("Assets/Graphics/HUD/Brother/ui-brotherhearts4.png"));
            playerBrother1Life = ImageIO.read(new File("Assets/Graphics/HUD/Brother/ui-brotherhearts5.png"));
            playerBrother0Life = ImageIO.read(new File("Assets/Graphics/HUD/Brother/ui-brotherhearts6.png"));
        }
        catch (IOException ex) {
            System.out.println("Lives Icons for HUD not found!");
        }

        // set initial sprites of HUD
        playerSister = playerSisterIdle;
        playerBrother = playerBrotherIdle;
        playerSisterLife = playerSister5Life;
        playerBrotherLife = playerBrother5Life;

    }

    // --- draw method ---

    /**
     * This method is from the DrawingObject interface. It takes a Graphics2D
     * object and draws the HUD accordingly.
     * 
     * @param g2d   the Graphics2D object used to draw the HUD
     */

    @Override
    public void draw(Graphics2D g2d) {

        // background
        Rectangle2D.Double background = new Rectangle2D.Double(x, y, width, height);
        g2d.setColor(Color.BLACK);
        g2d.fill(background);

        // headings
        g2d.setFont(pixelFont);
        g2d.setColor(Color.WHITE);
        g2d.drawString("Lives", (x + 30), (y + 200)); // heading for lives

        // player images
        g2d.drawImage(playerSister, (x + 45), (y + 220), null);
        g2d.drawImage(playerBrother, (x + 45), (y + 330), null);

        // lives
        g2d.drawImage(playerSisterLife, (x + 38), (y + 280), null);
        g2d.drawImage(playerBrotherLife, (x + 38), (y + 390), null);

    }

    // --- set methods ---
    
    /**
     * This method displays the appropriate sprites on the HUD for when
     * the game is over. It shows which player has won and lost depending
     * on what is passed inside the parameters.
     * 
     * @param winningPlayer     the ID of the player that won
     */

    public void setResults(int winningPlayer) {
        // sister
        if (winningPlayer == 1) {
            playerSister = playerSisterWin;
            playerBrother = playerBrotherLose;
        }
        // brother
        else if (winningPlayer == 2) {
            playerBrother = playerBrotherWin;
            playerSister = playerSisterLose;
        }
    }

    /**
     * This method modifies the sprites of the lives of the player in the HUD.
     * It determines which sprite to change to depending on what is passed
     * in the parameters
     * 
     * @param id    the ID of the player whose sprites should be changed
     * @param l     the number of lives that should be displayed in the sprite
     */

    public void setPlayerLifeHUD(int id, int l) {
        // sister
        if (id == 1) {
            if (l == 5) {
                playerSisterLife = playerSister5Life;
            }
            else if (l == 4) {
                playerSisterLife = playerSister4Life;
            }
            else if (l == 3) {
                playerSisterLife = playerSister3Life;
            }
            else if (l == 2) {
                playerSisterLife = playerSister2Life;
            }
            else if (l == 1) {
                playerSisterLife = playerSister1Life;
            }
            else {
                playerSisterLife = playerSister0Life;
            }
        }
        // brother
        else if (id == 2) {
            if (l == 5) {
                playerBrotherLife = playerBrother5Life;
            }
            else if (l == 4) {
                playerBrotherLife = playerBrother4Life;
            }
            else if (l == 3) {
                playerBrotherLife = playerBrother3Life;
            }
            else if (l == 2) {
                playerBrotherLife = playerBrother2Life;
            }
            else if (l == 1) {
                playerBrotherLife = playerBrother1Life;
            }
            else {
                playerBrotherLife = playerBrother0Life;
            }
        }
    }

    // --- get methods ---

    /**
     * This method gets the current life sprite of a certain player. It
     * returns the number that corresponds to this life sprite.
     * 
     * @param id    the ID of the player whose sprite information is needed
     */

    public int getPlayerLifeHUD(int id) {
        // sister
        if (id == 1) {
            if (playerSisterLife == playerSister5Life) {
                return 5;
            }
            else if (playerSisterLife == playerSister4Life) {
                return 4;
            }
            else if (playerSisterLife == playerSister3Life) {
                return 3;
            }
            else if (playerSisterLife == playerSister2Life) {
                return 2;
            }
            else if (playerSisterLife == playerSister1Life) {
                return 1;
            }
            else {
                return 0;
            }
        }
        // brother
        else if (id == 2) {
            if (playerBrotherLife == playerBrother5Life) {
                return 5;
            }
            else if (playerBrotherLife == playerBrother4Life) {
                return 4;
            }
            else if (playerBrotherLife == playerBrother3Life) {
                return 3;
            }
            else if (playerBrotherLife == playerBrother2Life) {
                return 2;
            }
            else if (playerBrotherLife == playerBrother1Life) {
                return 1;
            }
            else {
                return 0;
            }
        }
        else {
            return 5; // default five lives
        }

    }

}