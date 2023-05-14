// player appearance and functionality
// each player is an inner class

import java.io.*;
import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.*;

import java.util.*; // for ArrayList

public class Player {

    private int size;
    private int x, y, id, life;
    private double speed;
    public boolean isUpPressed, isDownPressed, isLeftPressed, isRightPressed, isHitPressed, hitLock;
    public boolean isCollidingUp, isCollidingDown, isCollidingLeft, isCollidingRight;

    // sprites for the player characters
    private BufferedImage p1Idle, p2Idle;
    public ArrayList<BufferedImage> p1WalkLeft, p1WalkRight, p1WalkUp, p1WalkDown;
    public ArrayList<BufferedImage> p2WalkLeft, p2WalkRight, p2WalkUp, p2WalkDown;

    public Player(int x, int y, int id) {

        this.id = id;
        this.x = x;
        this.y = y;

        size = 64;
        speed = 4;

        life = 5;

        // fetch player sprites
        // the player sprite fetched depends on the id
        // TODO: turn this into a while loop?
        try {
            p1Idle = ImageIO.read( new File("Assets/Graphics/Players/Sister/player-sister1.png"));
            p2Idle = ImageIO.read( new File("Assets/Graphics/Players/Brother/player-brother1.png"));
        }
        catch (IOException ex) {
            System.out.println("Player Sprites not found!");
        }

    }

    // TODO: make the -16 and -32 not hardcoded...
    public void draw(Graphics2D g2d) {
        if (id == 1) {
            g2d.drawImage(p1Idle, x, y, null);
        } else {
            g2d.drawImage(p2Idle, x, y, null);
        }
    }

    public void movePlayer() {
        if (isUpPressed && !isCollidingUp) {
            y -= speed;
        }

        if (isDownPressed && !isCollidingDown) {
            y += speed;
        }

        if (isLeftPressed && !isCollidingLeft) {
            x -= speed;
        }

        if (isRightPressed && !isCollidingRight) {
            x += speed;
        }
    }

    public void deductLife() {
        life -= 1;
    }

    public int getPlayerX() {
        return this.x;
    }

    public int getPlayerY() {
        return this.y;
    }

    public int getPlayerSize() {
        return size;
    }

    public int getPlayerID() {
        return id;
    }

    public int getPlayerLife() {
        return life;
    }

    public void setPlayerX(int x) {
        this.x = x;
    }

    public void setPlayerY(int y) {
        this.y = y;
    }

    public void setPlayerLife(int l) {
        life = l;
    }

    public void setHit(boolean bool) {
        isHitPressed = bool;
    }

    public void setHitLock(boolean bool) {
        hitLock = bool;
    }

    public void setUp(boolean bool) {
        isUpPressed = bool;
    }

    public void setDown(boolean bool) {
        isDownPressed = bool;
    }

    public void setLeft(boolean bool) {
        isLeftPressed = bool;
    }

    public void setRight(boolean bool) {
        isRightPressed = bool;
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
  
}