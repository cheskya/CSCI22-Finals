// player appearance and functionality
// each player is an inner class

import java.io.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.*;

public class Player {

    private int tileSize;
    private int playerX, playerY;
    private double playerSpeed;
    public boolean upPressed, downPressed, leftPressed, rightPressed; 
    public boolean isCollidingUp, isCollidingDown, isCollidingLeft, isCollidingRight;
    public boolean isCollidingStage1Up;

    private BufferedImage player1, player2;

    public Player() {
        tileSize = 64;
        playerX = 0;
        playerY = 0;
        playerSpeed = 3.5;

        // player 1 sprite
        try {
            player1 = ImageIO.read( new File("Assets/Graphics/player-1.png"));
        }
        catch (IOException ex) {
            System.out.println("Player 1 sprite not found!");
        }
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(player1, playerX, playerY, null);
    }

    public void movePlayer() {
        if (upPressed == true && isCollidingUp == false && isCollidingStage1Up == false) {
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