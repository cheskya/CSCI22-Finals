// this will be the frame of the game
// TITLE: DREAM TEAM (by JV :D)

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class GameFrame implements KeyListener, Runnable {

    public int width, height, playerID;
    public JFrame frame;

    public GameCanvas canvas;

    private Thread animationThread;
    private int FPS = 60;

    private Socket socket;

    public GameFrame(int w, int h) {
        width = w;
        height = h;

        frame = new JFrame();
        canvas = new GameCanvas(width, height);

        frame.addKeyListener(this);
        frame.setFocusable(true);
    }

    public void setUpFrame() {

        Container contentPane = frame.getContentPane();

        contentPane.add(canvas, BorderLayout.CENTER);

        frame.setSize(width, height);
        frame.pack();
        frame.setTitle("Dream Team");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    // --- animation thread ---
    // updates the game graphics

    public void startAnimationThread() {
        animationThread = new Thread(this);
        animationThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000000000/FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;
        long timer = 0;
        int drawCount = 0;

        while (animationThread != null) {
            currentTime = System.nanoTime();

            delta += (currentTime - lastTime) / drawInterval;
            timer += (currentTime - lastTime);
            lastTime = currentTime;

            if (delta >= 1) {
                update();
                canvas.repaint();
                delta--;
                drawCount++;
            }

            if (timer >= 1000000000) {
                drawCount = 0;
                timer = 0;
            }
        }
    }

    // --- key listeners ---

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            canvas.getPlayer().setUp(true);
        }

        if (code == KeyEvent.VK_S) {
            canvas.getPlayer().setDown(true);
        }

        if (code == KeyEvent.VK_A) {
            canvas.getPlayer().setLeft(true);
        }

        if (code == KeyEvent.VK_D) {
            canvas.getPlayer().setRight(true);
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            canvas.getPlayer().setUp(false);
        }

        if (code == KeyEvent.VK_S) {
            canvas.getPlayer().setDown(false);
        }

        if (code == KeyEvent.VK_A) {
            canvas.getPlayer().setLeft(false);
        }

        if (code == KeyEvent.VK_D) {
            canvas.getPlayer().setRight(false);
        }

    }

    // updates the animation of players and enemies
    // TODO: give the function a more descriptive name
    public void update() {
        canvas.isPlayerCollidingEdge();
        canvas.getPlayer().movePlayer();
    }

    public void connectToServer() {
        try {
            socket = new Socket("localhost", 58976);
            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            playerID = in.readInt();
            System.out.println("You are Player #" + playerID);
            if (playerID == 1) {
                System.out.println("Waiting for Player # 2 to connect...");
            }

        } catch (IOException ex) {
            System.out.println("IOException from connectToServer()");
        }
    }

}