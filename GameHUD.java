// HUD for the game. used by all screens

import java.io.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.*;

public class GameHUD {

    int x, y;
    int width, height;

    Font pixelFont;

    private BufferedImage player1, player2;
    private BufferedImage player1Hearts, player2Hearts;
    
    public GameHUD(int x, int y, int w, int h) {
        
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;

        // fetch pixel font
        // taken from https://stackoverflow.com/questions/5652344/how-can-i-use-a-custom-font-in-java
        // further modification from https://stackoverflow.com/questions/24800886/how-to-import-a-custom-java-awt-font-from-a-font-family-with-multiple-ttf-files
        try {
            pixelFont = Font.createFont(Font.TRUETYPE_FONT, new File("Assets/Fonts/Pixel.ttf")).deriveFont(30f);
            GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            //register the font
            ge.registerFont(pixelFont);
        }
        catch (IOException ex) {
            System.out.println("Pixel Font not found!");
        }
        catch (FontFormatException ex) {
            System.out.println("FontFormatException from GameHUD!");
        }

        // fetch player 1 icon
        try {
            player1 = ImageIO.read( new File("Assets/Graphics/HUD/Sister/ui-sister.png"));
        }
        catch (IOException ex) {
            System.out.println("Player 1 Icon not found!");
        }

        // fetch player 2 icon
        try {
            player2 = ImageIO.read( new File("Assets/Graphics/HUD/Brother/ui-brother.png"));
        }
        catch (IOException ex) {
            System.out.println("Player 2 Icon not found!");
        }

        // fetch player 1 hearts
        // TODO: make the image changeable depending on number of lives
        try {
            player1Hearts = ImageIO.read( new File("Assets/Graphics/HUD/Sister/ui-sisterhearts1.png"));
        }
        catch (IOException ex) {
            System.out.println("Player 1 Icon not found!");
        }

        // fetch player 2 hearts
        // TODO: make the image changeable depending on number of lives
        try {
            player2Hearts = ImageIO.read( new File("Assets/Graphics/HUD/Brother/ui-brotherhearts1.png"));
        }
        catch (IOException ex) {
            System.out.println("Player 2 Heart Icons not found!");
        }

    }

    // draw all HUD elements
    // NOTE: hardcoded values for now
    public void draw(Graphics2D g2d) {

        // background
        Rectangle2D.Double background = new Rectangle2D.Double(x, y, width, height);
        g2d.setColor(Color.BLACK);
        g2d.fill(background);

        // headings
        g2d.setFont(pixelFont);
        g2d.setColor(Color.WHITE);
        g2d.drawString("Lives", (x + 30), (y + 50)); // heading for lives
        g2d.drawString("Buffs", (x + 30), (y + 350)); // heading for buffs

        // player images
        g2d.drawImage(player1, (x + 45), (y + 70), null);
        g2d.drawImage(player2, (x + 45), (y + 180), null);

        // lives
        // TODO: allow these to be changed by other classes
        g2d.drawImage(player1Hearts, (x + 35), (y + 130), null);
        g2d.drawImage(player2Hearts, (x + 35), (y + 240), null);

        // buff images
        // TODO: allow these to be changed by other classes

        // NOTE: finalize buffs first before creating/adding!
        // we need to check if it's possible to code them in!

    }

}