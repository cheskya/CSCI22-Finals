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
    private int playerX, playerY, playerID;
    private double playerSpeed;
    private boolean upPressed, downPressed, leftPressed, rightPressed, hitPressed;

    // sprites for the player characters
    private BufferedImage player1Sprite, player2Sprite;

    public Player(int x, int y, int id) {

        tileSize = 64;

        playerID = id;
        playerX = x;
        playerY = y;

        playerSpeed = 3.5;

        // fetch player sprites
        // TODO: turn this into a while loop
        try {
            player1Sprite = ImageIO.read( new File("Assets/Graphics/Players/Sister/player-sister1.png"));
            player2Sprite = ImageIO.read( new File("Assets/Graphics/Players/Brother/player-brother1.png"));
        }
        catch (IOException ex) {
            System.out.println("Player Sprites not found!");
        }

    }

    public void draw(Graphics2D g2d) {
        if (playerID == 1) {
            g2d.drawImage(player1Sprite, playerX, playerY, null);
        } else {
            g2d.drawImage(player2Sprite, playerX, playerY, null);
        }
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

    public void setPlayerX(int x) {
        playerX = x;
    }

    public void setPlayerY(int y) {
        playerY = y;
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

    public void setHit(boolean bool) {
        hitPressed = bool;
    }

    public int getPlayerX() {
        return playerX;
    }

    public int getPlayerY() {
        return playerY;
    }

    public int getPlayerID() {
        return playerID;
    }
  
}