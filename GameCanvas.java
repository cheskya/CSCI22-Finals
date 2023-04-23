// will draw the screens accordingly

import java.awt.*;
import javax.swing.*;

public class GameCanvas extends JComponent {

    private final int screenWidth = 640, screenHeight = 640;
    private Player player;
    
    public GameCanvas() {
        setPreferredSize(new Dimension(screenWidth, screenHeight));
        setBackground(Color.white);
        player = new Player();
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        player.draw(g2d);
    }

    public Player getPlayer() {
        return player;
    }
}