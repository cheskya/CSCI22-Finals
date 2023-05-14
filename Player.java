// player appearance and functionality

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import javax.swing.*;
import javax.imageio.*;

public class Player {

    private int size;
    private int x, y, id;
    private double speed;
    public boolean isUpPressed, isDownPressed, isLeftPressed, isRightPressed;
    public boolean isCollidingUp, isCollidingDown, isCollidingLeft, isCollidingRight;

    // sprites for the player characters
    private int frameCounter, spriteCounter;
    private BufferedImage currentSprite;
    private BufferedImage idle, walkUp1, walkUp2, walkDown1, walkDown2, walkLeft1, walkLeft2, walkRight1, walkRight2;

    public Player(int x, int y, int id) {

        this.id = id;
        this.x = x;
        this.y = y;

        size = 64;
        speed = 3.5;

        frameCounter = 0; // counts the frames that passed since last player sprite update (animation)
        spriteCounter = 1; // counts the current player sprite

        // fetch player sprites
        // the player sprite fetched depends on the id
        try {
            if (id == 1) {
                idle = ImageIO.read(new File("Assets/Graphics/Players/Sister/player-sister1.png"));
                walkDown1 = ImageIO.read(new File("Assets/Graphics/Players/Sister/player-sister2.png"));
                walkDown2 = ImageIO.read(new File("Assets/Graphics/Players/Sister/player-sister3.png"));
                walkRight1 = ImageIO.read(new File("Assets/Graphics/Players/Sister/player-sister4.png"));
                walkRight2 = ImageIO.read(new File("Assets/Graphics/Players/Sister/player-sister5.png"));
                walkLeft1 = ImageIO.read(new File("Assets/Graphics/Players/Sister/player-sister6.png"));
                walkLeft2 = ImageIO.read(new File("Assets/Graphics/Players/Sister/player-sister7.png"));
                walkUp1 = ImageIO.read(new File("Assets/Graphics/Players/Sister/player-sister8.png"));
                walkUp2 = ImageIO.read(new File("Assets/Graphics/Players/Sister/player-sister9.png"));
            }
            else {
                idle = ImageIO.read(new File("Assets/Graphics/Players/Brother/player-brother1.png"));
                walkDown1 = ImageIO.read(new File("Assets/Graphics/Players/Brother/player-brother2.png"));
                walkDown2 = ImageIO.read(new File("Assets/Graphics/Players/Brother/player-brother3.png"));
                walkRight1 = ImageIO.read(new File("Assets/Graphics/Players/Brother/player-brother4.png"));
                walkRight2 = ImageIO.read(new File("Assets/Graphics/Players/Brother/player-brother5.png"));
                walkLeft1 = ImageIO.read(new File("Assets/Graphics/Players/Brother/player-brother6.png"));
                walkLeft2 = ImageIO.read(new File("Assets/Graphics/Players/Brother/player-brother7.png"));
                walkUp1 = ImageIO.read(new File("Assets/Graphics/Players/Brother/player-brother8.png"));
                walkUp2 = ImageIO.read(new File("Assets/Graphics/Players/Brother/player-brother9.png"));
            }
        }
        catch (IOException ex) {
            System.out.println("Player Sprites not found!");
        }

        currentSprite = idle;

    }

    public void draw(Graphics2D g2d) {
        g2d.drawImage(currentSprite, x, y, null);
    }

    // move the player depending on what key is pressed and whether they are colliding with something
    public void movePlayer() {
        if (isUpPressed == true && isCollidingUp == false) {
            y -= speed;
        }

        if (isDownPressed == true && isCollidingDown == false) {
            y += speed;
        }

        if (isLeftPressed == true && isCollidingLeft == false) {
            x -= speed;
        }

        if (isRightPressed == true && isCollidingRight == false) {
            x += speed;
        }

        animatePlayer();
    }

    // animate the player depending on what key is pressed
    public void animatePlayer() {
        // walking up
        if (isUpPressed == true) {
            if (spriteCounter == 1) {
                currentSprite = walkUp1;
            }
            else if (spriteCounter == 2) {
                currentSprite = walkUp2;
            }
        }
        // walking down
        else if (isDownPressed == true) {
            if (spriteCounter == 1) {
                currentSprite = walkDown1;
            }
            else if (spriteCounter == 2) {
                currentSprite = walkDown2;
            }
        }
        // walking left
        else if (isLeftPressed == true) {
            if (spriteCounter == 1) {
                currentSprite = walkLeft1;
            }
            else if (spriteCounter == 2) {
                currentSprite = walkLeft2;
            }
        }
        // walking right
        else if (isRightPressed == true) {
            if (spriteCounter == 1) {
                currentSprite = walkRight1;
            }
            else if (spriteCounter == 2) {
                currentSprite = walkRight2;
            }
        }
        else {
            currentSprite = idle;
        }

        
        // inspiration from https://youtu.be/wT9uNGzMEM4
        frameCounter++;
        if (frameCounter > 10) { // update the sprite every 10 frames
            if (spriteCounter == 1) {
                spriteCounter = 2;
            }
            else if (spriteCounter == 2) {
                spriteCounter = 1;
            }
            frameCounter = 0;
        }
    }

    // this is for the server to determine the current x coord
    public int getPlayerX() {
        return this.x;
    }

    // this is for the server to determine the current y coord
    public int getPlayerY() {
        return this.y;
    }

    public int getPlayerSize() {
        return size;
    }

    public int getPlayerID() {
        return id;
    }

    // this is for the server to determine the current sprite
    // using int because string is complicated for the server
    public int getCurrentSprite() {
        if (currentSprite == walkUp1) {
            return 1;
        }
        else if (currentSprite == walkUp2) {
            return 2;
        }
        else if (currentSprite == walkDown1) {
            return 3;
        }
        else if (currentSprite == walkDown2) {
            return 4;
        }
        else if (currentSprite == walkLeft1) {
            return 5;
        }
        else if (currentSprite == walkLeft2) {
            return 6;
        }
        else if (currentSprite == walkRight1) {
            return 7;
        }
        else if (currentSprite == walkRight2) {
            return 8;
        }
        else {
            return 0; // idle
        }
    }

    public void setPlayerX(int x) {
        this.x = x;
    }

    public void setPlayerY(int y) {
        this.y = y;
    }

    public void setPlayerSprite(int s) {
        if (s == 1) {
            currentSprite = walkUp1;
        }
        else if (s == 2) {
            currentSprite = walkUp2;
        }
        else if (s == 3) {
            currentSprite = walkDown1;
        }
        else if (s == 4) {
            currentSprite = walkDown2;
        }
        else if (s == 5) {
            currentSprite = walkLeft1;
        }
        else if (s == 6) {
            currentSprite = walkLeft2;
        }
        else if (s == 7) {
            currentSprite = walkRight1;
        }
        else if (s == 8) {
            currentSprite = walkRight2;
        }
        else {
            currentSprite = idle;
        }
    }

    // player is pressing up key
    public void setUp(boolean bool) {
        isUpPressed = bool;
    }

    // player is pressing down key
    public void setDown(boolean bool) {
        isDownPressed = bool;
    }

    // player is pressing left key
    public void setLeft(boolean bool) {
        isLeftPressed = bool;
    }

    // player is pressing right key
    public void setRight(boolean bool) {
        isRightPressed = bool;
    }

    // player is colliding with top
    public void collideUp(boolean bool) {
        isCollidingUp = bool;
    }

    // player is colliding with bottom
    public void collideDown(boolean bool) {
        isCollidingDown = bool;
    }

    // player is colliding with left
    public void collideLeft(boolean bool) {
        isCollidingLeft = bool;
    }

    // player is colliding with right
    public void collideRight(boolean bool) {
        isCollidingRight = bool;
    }
  
}