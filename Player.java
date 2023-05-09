// player appearance and functionality
// each player is an inner class

import java.io.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.*;

public class Player {

    public int playerID;
    private int tileSize;
    private int playerX, playerY;
    private double playerSpeed;
    public boolean upPressed, downPressed, leftPressed, rightPressed; 
    public boolean isCollidingUp, isCollidingDown, isCollidingLeft, isCollidingRight;
    public boolean isCollidingStage1Up;

    private BufferedImage player1, player2;

    public Player(int x, int y, int id) {
        tileSize = 64;
        playerID = id;
        playerX = x;
        playerY = y;
        playerSpeed = 3.5;

        // player 1 sprite
        try {
            player1 = ImageIO.read(new File("Assets/Graphics/player-1.png"));
            player2 = ImageIO.read(new File("Assets/Graphics/player-2.png"));
        }
        catch (IOException ex) {
            System.out.println("Player sprite not found!");
        }
    }

    public void draw(Graphics2D g2d) {
        if (playerID == 1) {
            g2d.drawImage(player1, playerX, playerY, null);
        } else {
            g2d.drawImage(player2, playerX, playerY, null);
        }

        // if (m == 1) {
        //     if (n == 1) {
        //         g2d.drawImage(player1, playerX, playerY, null);
        //     } else {
        //         Rectangle2D.Double player2 = new Rectangle2D.Double(playerX + 100, playerY, tileSize, tileSize);
        //         g2d.setColor(Color.BLUE);
        //         g2d.fill(player2);
        //     }
        // } else {
        //     if (n == 1) {
        //         Rectangle2D.Double player1 = new Rectangle2D.Double(playerX, playerY, tileSize, tileSize);
        //         g2d.setColor(Color.BLUE);
        //         g2d.fill(player1);
        //     } else {
        //         g2d.drawImage(player2, playerX + 100, playerY, null);
        //     }
        // }
        // if (n == 1) {
        //     g2d.drawImage(player1, playerX, playerY, null);

        //     Rectangle2D.Double player2 = new Rectangle2D.Double(playerX + 100, playerY, tileSize, tileSize);
        //     g2d.setColor(Color.BLUE);
        //     g2d.fill(player2);
        // } else if (n == 2) {
        //     Rectangle2D.Double player1 = new Rectangle2D.Double(playerX, playerY, tileSize, tileSize);
        //     g2d.setColor(Color.BLUE);
        //     g2d.fill(player1);

        //     g2d.drawImage(player2, playerX + 100, playerY, null);
        // }
    }

    public void movePlayer() {
        if (upPressed == true && isCollidingUp == false) {
            playerY -= playerSpeed;
        }

        if (downPressed == true && isCollidingDown == false) {
            playerY += playerSpeed;
        }

        if (leftPressed == true && isCollidingLeft == false) {
            playerX -= playerSpeed;
        }

        if (rightPressed == true && isCollidingRight == false) {
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

    public int getPlayerX() {
        return playerX;
    }

    public int getPlayerY() {
        return playerY;
    }

    public int getPlayerSize() {
        return tileSize;
    }

    public void collideUp(boolean bool) {
        isCollidingUp = bool;
    }

    public void collideDown(boolean bool) {
        isCollidingDown = bool;
    }

    public void collideLeft(boolean bool) {
        isCollidingLeft = bool;
    }

    public void collideRight(boolean bool) {
        isCollidingRight = bool;
    }

    public void collideStage1Up(boolean bool) {
        isCollidingStage1Up = bool;
    }
  
}