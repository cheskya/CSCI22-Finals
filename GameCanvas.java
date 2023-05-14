// the class that merges the screen with the players

import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.net.*;

public class GameCanvas extends JComponent {

    public int width, height;

    public Player p1, p2; // p1 is you, p2 is the other player

    public GameScreen screen;

    // instantiate gamecanvas and related elements
    public GameCanvas(int w, int h) {

        width = w;
        height = h;
        setPreferredSize(new Dimension(width, height));

        screen = new GameScreen();
    
    }

    // method that draws the canvas
    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        screen.draw(g2d);
        p1.draw(g2d);
        p2.draw(g2d);

    }

    // method to access player 1
    public Player getPlayer1() {
        return p1;
    }

    // method to access player 2
    public Player getPlayer2() {
        return p2;
    }

    public GameScreen getCurrentScreen() {
        return screen;
    }

}