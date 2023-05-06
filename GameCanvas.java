// will draw the screens accordingly

import java.awt.*;
import javax.swing.*;

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

    public MapStage1 getMapStage1() {
        return stage1;
    }

    public void isPlayerCollidingEdge() {
        if (player.getPlayerX() <= 0) {
            player.collideLeft(true);
        } else {
            player.collideLeft(false);
        }

        if (player.getPlayerX() + player.getPlayerSize() >= width) {
            player.collideRight(true);
        } else {
            player.collideRight(false);
        }

        if (player.getPlayerY() <= 0) {
            player.collideUp(true);
        } else {
            player.collideUp(false);
        }

        if (player.getPlayerY() + player.getPlayerSize() >= height) {
            player.collideDown(true);
        } else {
            player.collideDown(false);
        }
    }
}