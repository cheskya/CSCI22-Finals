import java.time.*;

public class Punch {
    
    private Clock clock;
    private long shootBuffer = 0;

    public boolean isPunchPressed, punchLock;

    public Punch() {
        clock = clock.systemUTC();
    }

    public void setPunch(boolean bool) {
        isPunchPressed = bool;
    }

    public void setPunchLock(boolean bool) {
        punchLock = bool;
    }

    public void playerPunchAvailability(Player p1, Player p2) {
        if (isPunchPressed && !punchLock && clock.millis() - shootBuffer > 500) {
            punch(p1, p2);
        }
    }

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
