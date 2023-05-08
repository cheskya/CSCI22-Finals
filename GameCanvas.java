// will draw the screens accordingly

import java.awt.*;
import javax.swing.*;

public class GameCanvas extends JComponent {

    public int width, height;

    public Player player1, player2;
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
        player1.draw(g2d);
        player2.draw(g2d);

    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public MapStage1 getMapStage1() {
        return stage1;
    }

    public void isPlayerCollidingEdge() {
        if (player1.getPlayerX() <= 0 || player2.getPlayerX() <= 0) {
            if (player1.getPlayerX() <= 0) {
                player1.collideLeft(true);
            }
            if (player2.getPlayerX() <= 0) {
                player2.collideLeft(true);
            }
        } else {
            player1.collideLeft(false);
            player2.collideLeft(false);
        }

        if (player1.getPlayerX() + player1.getPlayerSize()
        >= width || player2.getPlayerX() + player2.getPlayerSize() >= width) {
            if (player1.getPlayerX() + player1.getPlayerSize() >= width) {
                player1.collideRight(true);
            }
            if (player2.getPlayerX() + player2.getPlayerSize() >= width) {
                player2.collideRight(true);
            }
        } else {
            player1.collideRight(false);
            player2.collideRight(false);
        }

        if (player1.getPlayerY() <= 0 || player2.getPlayerY() <= 0) {
            if (player1.getPlayerY() <= 0) {
                player1.collideUp(true);
            }
            if (player2.getPlayerY() <= 0) {
                player2.collideUp(true);
            }
        } else {
            player1.collideUp(false);
            player2.collideUp(false);
        }

        if (player1.getPlayerY() + player1.getPlayerSize() >= height
        || player2.getPlayerY() + player2.getPlayerSize() >= height) {
            if (player1.getPlayerY() + player1.getPlayerSize() >= height) {
                player1.collideDown(true);
            }
            if (player2.getPlayerY() + player2.getPlayerSize() >= height) {
                player2.collideDown(true);
            }
        } else {
            player1.collideDown(false);
            player2.collideDown(false);
        }
    }
}