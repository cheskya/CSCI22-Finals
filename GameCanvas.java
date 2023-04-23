// will draw the screens accordingly

import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;
import java.util.*;

public class GameCanvas extends JComponent {

    public int width, height;

    public Player player;
    public MapStage1 stage1;

    public GameCanvas(int w, int h) {

        width = w;
        height = h;
        setPreferredSize(new Dimension(width, height));

        player = new Player();
        stage1 = new MapStage1();

    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        stage1.draw(g2d);
        player.draw(g2d);

    }

    public Player getPlayer() {
        return player;
    }

}