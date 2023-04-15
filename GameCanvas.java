// will draw the screens accordingly

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.util.*;

public class GameCanvas extends JComponent {

    public int width, height;
    public ArrayList<GameScreen> screens;

    // initialize all GameScreens here later

    public GameCanvas(int w, int h) {

        width = w;
        height = h;
        setPreferredSize(new Dimension(1024, 768)); // the dimensions we'll use for now

        // create all GameScreens here later
        // add all GameScreens to arraylist here later

    }

    @Override
    protected void paintComponent(Graphics g) {

        // i'll set this up later

    }

}