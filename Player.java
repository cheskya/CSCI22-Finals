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

    private BufferedImage player1, player2;

    public Player() {
        tileSize = 64;
        playerX = 100;
        playerY = 100;
        playerSpeed = 3.5;

        // player 1 sprite
        try {
            player1 = ImageIO.read( new File("Assets/Graphics/Players/Sister/player-sister1.png"));
        }
        catch (IOException ex) {
            System.out.println("Player 1 Sprite not found!");
        }
    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(player1, playerX, playerY, null);
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

    public int getPlayerX() {
        return playerX;
    }

    public int getPlayerY() {
        return playerY;
    }
  
}