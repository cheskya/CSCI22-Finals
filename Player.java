// player appearance and functionality
// each player is an inner class

import java.awt.*;

public class Player {
    private final int tileSize;
    private int playerX, playerY, playerSpeed;
    public boolean upPressed, downPressed, leftPressed, rightPressed;

    public Player() {
        tileSize = 64;
        playerX = 100;
        playerY = 100;
        playerSpeed = 5;
    }

    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.black);
        g2d.fillRect(playerX, playerY, tileSize, tileSize);
        g2d.dispose();
    }

    public void movePlayer() {
        if (upPressed == true) {
            playerY -= playerSpeed;
        }

        if (downPressed == true) {
            playerY += playerSpeed;
        }

        if (leftPressed == true) {
            playerX -= playerSpeed;
        }

        if (rightPressed == true) {
            playerX += playerSpeed;
        }
    }

    public void setUp(boolean bool) {
        upPressed = bool;
    }

    public void setDown(boolean bool) {
        downPressed = bool;
    }

    public void setLeft(boolean bool) {
        leftPressed = bool;
    }

    public void setRight(boolean bool) {
        rightPressed = bool;
    }
}