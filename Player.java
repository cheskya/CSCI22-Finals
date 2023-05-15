/**
 * This class handles the appearance and functionality of the
 * players. It holds values unique to each player and the methods
 * to access and modify them.
 *
 * @author Arthur Jed Lluisma (223729)
 * @author Francesca Dominique J. Reyes (225318)
 * @version May 15, 2023
 */

/*
    I have not discussed the Java language code in my program 
    with anyone other than my instructor or the teaching assistants 
    assigned to this course.

    I have not used Java language code obtained from another student, 
    or any other unauthorized source, either modified or unmodified.

    If any Java language code or documentation used in my program 
    was obtained from another source, such as a textbook or website, 
    that has been clearly noted with a proper citation in the comments 
    of my program.
*/

import java.io.*;
import java.awt.*;
import java.awt.image.*;
import javax.imageio.*;

public class Player implements DrawingObject {

    private int size;
    private int x, y, id, life;
    private double speed;

    // for moving the player
    public boolean isUpPressed, isDownPressed, isLeftPressed, isRightPressed, isHitPressed, hitLock;
    public boolean isCollidingUp, isCollidingDown, isCollidingLeft, isCollidingRight;

    // for animating the player
    private int frameCounter, spriteCounter;
    private BufferedImage currentSprite;
    private BufferedImage idle, walkUp1, walkUp2, walkDown1, walkDown2, walkLeft1, walkLeft2, walkRight1, walkRight2, hit, hurt, win, lose;
    private BufferedImage explosion;
    
    /**
     * This constructor initializes important values for the player. It also fetches
     * all player sprites to be used in animating the player.
     * 
     * @param x     initial x coordinate of the player
     * @param y     intiial y coordinate of the player
     * @param id    player sprite to be used (1 for sister, 2 for brother)
     */

    public Player(int x, int y, int id) {

        this.id = id;
        this.x = x;
        this.y = y;

        size = 64;
        speed = 4;

        life = 5;

        frameCounter = 0; // counts the frames that passed since last player sprite update (animation)
        spriteCounter = 1; // counts the current player sprite

        // fetch player sprites
        try {
            // sister
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
                hit = ImageIO.read(new File("Assets/Graphics/Players/Sister/player-sister10.png"));
                win = ImageIO.read(new File("Assets/Graphics/Players/Sister/player-sister11.png"));
                lose = ImageIO.read(new File("Assets/Graphics/Players/Sister/player-sister12.png"));
                hurt = ImageIO.read(new File("Assets/Graphics/Players/Sister/player-sister13.png"));
            }
            // brother
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
                hit = ImageIO.read(new File("Assets/Graphics/Players/Brother/player-brother10.png"));
                win = ImageIO.read(new File("Assets/Graphics/Players/Brother/player-brother11.png"));
                lose = ImageIO.read(new File("Assets/Graphics/Players/Brother/player-brother12.png"));
                hurt = ImageIO.read(new File("Assets/Graphics/Players/Brother/player-brother13.png"));
            }
            explosion = ImageIO.read(new File("Assets/Graphics/explosion.gif"));
        }
        catch (IOException ex) {
            System.out.println("Player Sprites not found!");
        }

        // sets initial player sprite
        currentSprite = idle;

    }

    // --- draw method ---

    /**
     * This method is from the DrawingObject interface. It takes a Graphics2D
     * object and draws the player sprite accordingly.
     * 
     * @param g2d   the Graphics2D object used to draw the sprite
     */

    @Override
    public void draw(Graphics2D g2d) {
        g2d.drawImage(currentSprite, x, y, null);
    }

    // --- move and animate methods ---

    /**
     * This method moves the player. It checks for which key is pressed and whether the player
     * is colliding with something, then modifies the player's speed accordingly.
     */

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

    /**
     * This method animates the player sprites. It checks for what key is pressed
     * and modifies the player's sprites accordingly. For looping animations, it
     * changes the sprite every 10 frames.
     */

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

        if (isHitPressed == true) {
            currentSprite = hit;
        }
        
        // code inspiration from https://youtu.be/wT9uNGzMEM4
        
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

    // --- get methods ---

    /**
     * This method gets the player's current x coordinate. It returns it
     * as an int.
     * 
     * @return the x-coordinate of player
     */

    public int getPlayerX() {
        return x;
    }

    /**
     * This method gets the player's current y coordinate. It returns it
     * as an int.
     * 
     * @return the y-coordinate of player
     */

    public int getPlayerY() {
        return y;
    }

    /**
     * This method gets the player's size. It returns it
     * as an int.
     * 
     * @return size of player in pixels
     */

    public int getPlayerSize() {
        return size;
    }

    /**
     * This method gets the player's ID. It returns it
     * as an int.
     * 
     * @return player ID (determines which sprite is being used)
     */

    public int getPlayerID() {
        return id;
    }

    /**
     * This method gets the player's current number of lives. It returns it
     * as an int.
     * 
     * @return the player's current number of lives
     */

    public int getPlayerLife() {
        return life;
    }

    /**
     * This method gets the player sprite that is currently being used
     * by the player. It returns the corresponding int value.
     * 
     * @return corresponding number of the current player sprite
     */

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
        else if (currentSprite == hit) {
            return 9;
        }
        else if (currentSprite == hurt) {
            return 10;
        }
        else if (currentSprite == win) {
            return 11;
        }
        else if (currentSprite == lose) {
            return 12;
        }
        else if (currentSprite == explosion) {
            return 13;
        }
        else {
            return 0; // idle
        }
    }

    // --- set methods ---

    /**
     * This method changes the x-coordinate of the player. It accepts
     * an int and assigns the x value of the player to that.
     * 
     * @param x     x-coordinate to change to
     */

    public void setPlayerX(int x) {
        this.x = x;
    }

    /**
     * This method changes the y-coordinate of the player. It accepts
     * an int and assigns the y value of the player to that.
     * 
     * @param y     y-coordinate to change to
     */

    public void setPlayerY(int y) {
        this.y = y;
    }

    /**
     * This method changes the number of lives of the player. It accepts
     * an int and assigns the life value of the player to that.
     * 
     * @param l     number of lives to change to
     */

    public void setPlayerLife(int l) {
        life = l;
    }

    /**
     * This method changes the current sprite of the player. It accepts
     * an int and, depending on the value, assigns the corresponding
     * sprite to currentSprite.
     * 
     * @param s     number of the sprite to change to
     */

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
        else if (s == 9) {
            currentSprite = hit;
        }
        else if (s == 10) {
            currentSprite = hurt;
        }
        else if (s == 11) {
            currentSprite = win;
        }
        else if (s == 12) {
            currentSprite = lose;
        }
        else if (s == 13) {
            currentSprite = explosion;
        }
        else if (s == 0) {
            currentSprite = idle;
        }
    }
    
    /**
     * This method modifies the isHitPressed value depending on
     * the passed boolean. This is used to log whether or not the
     * player is pressing the hit key.
     * 
     * @param bool      boolean to change the isHitPressed value to
     */

    public void setHit(boolean bool) {
        isHitPressed = bool;
    }

    /**
     * This method modifies the hitLock value depending on
     * the passed boolean. This is used to log whether or not the
     * player should be locked from pressing the hit key.
     * 
     * @param bool      boolean to change the hitLock value to
     */

    public void setHitLock(boolean bool) {
        hitLock = bool;
    }

    /**
     * This method modifies the isUpPressed value depending on
     * the passed boolean. This is used to log whether or not the
     * player is pressing the up key.
     * 
     * @param bool      boolean to change the isUpPressed value to
     */

    public void setUp(boolean bool) {
        isUpPressed = bool;
    }

    /**
     * This method modifies the isDownPressed value depending on
     * the passed boolean. This is used to log whether or not the
     * player is pressing the down key.
     * 
     * @param bool      boolean to change the isDownPressed value to
     */

    public void setDown(boolean bool) {
        isDownPressed = bool;
    }

    /**
     * This method modifies the isLeftPressed value depending on
     * the passed boolean. This is used to log whether or not the
     * player is pressing the left key.
     * 
     * @param bool      boolean to change the isLeftPressed value to
     */

    public void setLeft(boolean bool) {
        isLeftPressed = bool;
    }

    /**
     * This method modifies the isRightPressed value depending on
     * the passed boolean. This is used to log whether or not the
     * player is pressing the right key.
     * 
     * @param bool      boolean to change the isRightPressed value to
     */

    public void setRight(boolean bool) {
        isRightPressed = bool;
    }

    // --- collide methods ---

    /**
     * This method modifies the isCollidingUp value depending on
     * the passed boolean. This is used to log whether or not the
     * player is colliding with something at the top.
     * 
     * @param bool      boolean to change the isCollidingUp value to
     */

    public void collideUp(boolean bool) {
        isCollidingUp = bool;
    }

    /**
     * This method modifies the isCollidingDown value depending on
     * the passed boolean. This is used to log whether or not the
     * player is colliding with something at the bottom.
     * 
     * @param bool      boolean to change the isCollidingDown value to
     */

    public void collideDown(boolean bool) {
        isCollidingDown = bool;
    }

    /**
     * This method modifies the isCollidingLeft value depending on
     * the passed boolean. This is used to log whether or not the
     * player is colliding with something at the left.
     * 
     * @param bool      boolean to change the isCollidingLeft value to
     */

    public void collideLeft(boolean bool) {
        isCollidingLeft = bool;
    }

    /**
     * This method modifies the isCollidingRight value depending on
     * the passed boolean. This is used to log whether or not the
     * player is colliding with something at the right.
     * 
     * @param bool      boolean to change the isCollidingRight value to
     */

    public void collideRight(boolean bool) {
        isCollidingRight = bool;
    }

    // --- misc methods ---

    /**
     * This method deducts one from the current number of lives of the player.
     * After, it changes the current player sprite to the hurt sprite.
     */

    public void deductLife() {
        life -= 1;
        currentSprite = hurt; // only appears for a very brief period of time
    }
  
}