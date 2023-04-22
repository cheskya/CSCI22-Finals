// this will contain the UI of the game

// TITLE: DREAM TEAM (by JV :D)

import javax.swing.*;
import java.awt.event.*;

public class GameFrame implements KeyListener, Runnable {

    private JFrame f;
    private int FPS = 60;
    private Thread gameThread;
    private GameCanvas gc;

    public GameFrame() {
        f = new JFrame();
        gc = new GameCanvas();
        f.addKeyListener(this);
        f.setFocusable(true);
    }

    public void setUpGUI() {
        f.setResizable(false);
        f.setTitle("Test");
        f.add(gc);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.pack();
        f.setVisible(true);
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (gameThread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                gc.repaint();
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000) {
                drawCount = 0;
                timer = 0;
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            gc.getPlayer().setUp(true);
        }

        if (code == KeyEvent.VK_S) {
            gc.getPlayer().setDown(true);
        }

        if (code == KeyEvent.VK_A) {
            gc.getPlayer().setLeft(true);
        }

        if (code == KeyEvent.VK_D) {
            gc.getPlayer().setRight(true);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            gc.getPlayer().setUp(false);
        }

        if (code == KeyEvent.VK_S) {
            gc.getPlayer().setDown(false);
        }

        if (code == KeyEvent.VK_A) {
            gc.getPlayer().setLeft(false);
        }

        if (code == KeyEvent.VK_D) {
            gc.getPlayer().setRight(false);
        }
    }

    public void update() {
        gc.getPlayer().movePlayer();
    }
}