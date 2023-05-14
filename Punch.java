// class for punch (the mechanic that allows players to hit each other)

import java.time.*;

public class Punch {
    
    private Clock clock;
    private long shootBuffer = 0;

    public boolean isPunchPressed, punchLock;

    // constructor
    public Punch() {
        clock = clock.systemUTC();
    }

    // sets isPunchPressed to the boolean value bool. Determines if the punch button is pressed or not.
    public void setPunch(boolean bool) {
        isPunchPressed = bool;
    }

    // sets punchLock to the boolean value bool. This stops the player from holding down the punch button.
    public void setPunchLock(boolean bool) {
        punchLock = bool;
    }

    /* checks if player is ready to punch by checking if:
     * a) player pressed the punch button
     * b) punchlock is not active
     * c) punch is pressed after a short buffer (this stops the player from spam pressing the punch button)
    */ 
    public void playerPunchAvailability(Player p1, Player p2) {
        if (isPunchPressed && !punchLock && clock.millis() - shootBuffer > 500) {
            punch(p1, p2);
        }
    }

    // TODO: make a Buff class and call the buff method when p2.getPlayerLife() == 1
    /* if player is ready to punch, call this method
     * this method checks for collision
     * if colliding, damage the other player and call the corresponding methods (aka buff)
     * if not colliding, player will miss and nothing happens
    */
    public void punch(Player p1, Player p2) {
        if (!(p1.getPlayerX() + p1.getPlayerSize() <= p2.getPlayerX() + 20 ||
            p1.getPlayerX() + 20 >= p2.getPlayerX() + p2.getPlayerSize() ||
            p1.getPlayerY() + p1.getPlayerSize() <= p2.getPlayerY() + 4 ||
            p1.getPlayerY() + 4 >= p2.getPlayerY() + p2.getPlayerSize())) {
                p2.deductLife();
                if (p2.getPlayerLife() == 0) {
                    System.out.println(p2.getPlayerLife());
                    System.out.println("Game Over!");
                    setPunchLock(true);
                } else if (p2.getPlayerLife() == 1) {
                    System.out.println(p2.getPlayerLife());
                    p2.setPlayerSpeed(100);
                    setPunchLock(true);
                } else {
                    System.out.println("Miss!");
                    setPunchLock(true);
                }
                // if (p2.getPlayerLife() == 1) {
                //     p2.deductLife();
                //     System.out.println(p2.getPlayerLife());
                //     System.out.println("Game Over!");
                //     p1.setHitLock(true);
                // } else {
                //     p2.deductLife();
                //     System.out.println(p2.getPlayerLife());
                //     p1.setHitLock(true);
                // }
            } else {
                System.out.println("Miss!");
                setPunchLock(true);
            }
        shootBuffer = clock.millis();
    }

}
