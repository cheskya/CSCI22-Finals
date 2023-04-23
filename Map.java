import java.awt.*;
import java.awt.geom.*;
import javax.swing.*;

public interface Map {

    public void draw(Graphics2D g2d);
    public boolean isPlayerColliding(Player player);
    
}