// will draw the screens accordingly

import java.awt.*;
import javax.swing.*;

public class GameCanvas extends JComponent {

    public int width, height;

    public Player p1, p2;
    public MapStage1 stage1;

    public GameCanvas(int w, int h) {

        width = w;
        height = h;
        setPreferredSize(new Dimension(width, height));

        stage1 = new MapStage1();
    
    }

    @Override
    protected void paintComponent(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        stage1.draw(g2d);
        p1.draw(g2d);
        p2.draw(g2d);

    }

    public Player getP1() {
        return p1;
    }

    public Player getP2() {
        return p2;
    }

    public MapStage1 getMapStage1() {
        return stage1;
    }

    public void isPlayerCollidingEdge() {
        if (p1.getPlayerX() <= -1 || p2.getPlayerX() <= -1) {
            if (p1.getPlayerX() <= -1) {
                p1.collideLeft(true);
            }
            if (p2.getPlayerX() <= -1) {
                p2.collideLeft(true);
            }
        } else {
            p1.collideLeft(false);
            p2.collideLeft(false);
        }

        if (p1.getPlayerX() + p1.getPlayerSize() >= width 
        || p2.getPlayerX() + p2.getPlayerSize() >= width) {
            if (p1.getPlayerX() + p1.getPlayerSize() >= width) {
                p1.collideRight(true);
            }
            if (p2.getPlayerX() + p2.getPlayerSize() >= width) {
                p2.collideRight(true);
            }
        } else {
            p1.collideRight(false);
            p2.collideRight(false);
        }

        if (p1.getPlayerY() <= -1 || p2.getPlayerY() <= -1) {
            if (p1.getPlayerY() <= -1) {
                p1.collideUp(true);
            }
            if (p2.getPlayerY() <= -1) {
                p2.collideUp(true);
            }
        } else {
            p1.collideUp(false);
            p2.collideUp(false);
        }

        if (p1.getPlayerY() + p1.getPlayerSize() >= height
        || p2.getPlayerY() + p2.getPlayerSize() >= height) {
            if (p1.getPlayerY() + p1.getPlayerSize() >= height) {
                p1.collideDown(true);
            }
            if (p2.getPlayerY() + p2.getPlayerSize() >= height) {
                p2.collideDown(true);
            }
        } else {
            p1.collideDown(false);
            p2.collideDown(false);
        }
    }
}