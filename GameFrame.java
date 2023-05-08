// this will be the frame of the game
// TITLE: DREAM TEAM (by JV :D)

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.*;
import java.net.*;
import javax.imageio.*;

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

        createSprites();
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
            if (playerID == 1) {
                canvas.getPlayer1().setUp(true);
            } else {
                canvas.getPlayer2().setUp(true);
            }
        }

        if (code == KeyEvent.VK_S) {
            if (playerID == 1) {
                canvas.getPlayer1().setDown(true);
            } else {
                canvas.getPlayer2().setDown(true);
            }
        }

        if (code == KeyEvent.VK_A) {
            if (playerID == 1) {
                canvas.getPlayer1().setLeft(true);
            } else {
                canvas.getPlayer2().setLeft(true);
            }
        }

        if (code == KeyEvent.VK_D) {
            if (playerID == 1) {
                canvas.getPlayer1().setRight(true);
            } else {
                canvas.getPlayer2().setRight(true);
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            if (playerID == 1) {
                canvas.getPlayer1().setUp(false);
            } else {
                canvas.getPlayer2().setUp(false);
            }
        }

        if (code == KeyEvent.VK_S) {
            if (playerID == 1) {
                canvas.getPlayer1().setDown(false);
            } else {
                canvas.getPlayer2().setDown(false);
            }
        }

        if (code == KeyEvent.VK_A) {
            if (playerID == 1) {
                canvas.getPlayer1().setLeft(false);
            } else {
                canvas.getPlayer2().setLeft(false);
            }
        }

        if (code == KeyEvent.VK_D) {
            if (playerID == 1) {
                canvas.getPlayer1().setRight(false);
            } else {
                canvas.getPlayer2().setRight(false);
            }
        }

    }

    // updates the animation of players and enemies
    // TODO: give the function a more descriptive name
    public void update() {
        canvas.isPlayerCollidingEdge();
        canvas.getPlayer1().movePlayer();
        canvas.getPlayer2().movePlayer();
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
            System.out.println(ex);
        }
    }

    private void createSprites() {
        if (playerID == 1) {
            canvas.player1 = new Player(0, 0, 1);
            canvas.player2 = new Player(100, 0, 2);
        } else {
            canvas.player1 = new Player(0, 0, 1);
            canvas.player2 = new Player(100, 0, 2);
        }
    }

}