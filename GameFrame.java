// this will be the frame of the game
// TITLE: DREAM TEAM (by JV :D)

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.time.*;

public class GameFrame implements KeyListener, Runnable {

    private Clock clock;
    private long shootBuffer = 0;
    private long bufferTime = 0;

    public int width, height;
    public JFrame frame;

    public GameCanvas canvas;
    public Player player, otherPlayer;

    private Thread animationThread;
    private int FPS = 60;

    public int playerID;
    private Socket socket;
    private ReadFromServer rfsRunnable;
    private WriteToServer wtsRunnable;

    // instantiates the gameframe and gamecanvas
    public GameFrame(int w, int h) {
        width = w;
        height = h;

        frame = new JFrame();
        canvas = new GameCanvas(width, height);

        frame.addKeyListener(this);
        frame.setFocusable(true);

        clock = clock.systemUTC();
    }

    // fixes the gameframe contents
    public void setUpFrame() {

        Container contentPane = frame.getContentPane();

        contentPane.add(canvas, BorderLayout.CENTER);

        createSprites();

        frame.setSize(width, height);
        frame.pack();
        frame.setTitle("Dream Team - Player #" + playerID);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    // method to draw the players
    public void createSprites() {
        // p1 is you, p2 is friend
        // 1 is sister, 2 is brother (3rd parameter of Player)
        if (playerID == 1) {
            canvas.p1 = new Player(256, 288, 1);
            canvas.p2 = new Player(330, 288, 2);
        }
        else {
            canvas.p2 = new Player(256, 288, 1);
            canvas.p1 = new Player(330, 288, 2);
        }
        player = canvas.p1;
        otherPlayer = canvas.p2;

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

    // updates the animation of players and enemies
    // TODO: give the function a more descriptive name
    public void update() {
        handlePlayerEdgeCollision();
        playerPunchAvailability();
        player.movePlayer();
    }

    // handles the collisions of the player with edge
    // not too heavy on program, so made it client-side
    public void handlePlayerEdgeCollision() {
        // left edge
        if (player.getPlayerX() <= 0) {
            player.collideLeft(true);
        }
        else {
            player.collideLeft(false);
        }
        // right edge
        if (player.getPlayerX() + player.getPlayerSize() >= 640) { // hardcoded width for  (because of hud)
            player.collideRight(true);
        }
        else {
            player.collideRight(false);
        }
        // top edge
        if (player.getPlayerY() <= 0) {
            player.collideUp(true);
        }
        else {
            player.collideUp(false);
        }
        // bottom edge
        if (player.getPlayerY() + player.getPlayerSize() >= height) {
            player.collideDown(true);
        }
        else {
            player.collideDown(false);
        }
    }

    // boolean flag to stop player from holding down the button:
    // https://stackoverflow.com/questions/48120739/how-to-prevent-repeated-actions-from-a-key-being-held-down
    // this method calculates if the players hit each other when they press the punch button
    public void playerPunchAvailability() {
        if (player.isHitPressed && !player.hitLock && clock.millis() - shootBuffer > 1000) {
            punch();

            // if (!(player.getPlayerX() + player.getPlayerSize() <= otherPlayer.getPlayerX() + 20 ||
            // player.getPlayerX() + 20 >= otherPlayer.getPlayerX() + otherPlayer.getPlayerSize() ||
            // player.getPlayerY() + player.getPlayerSize() <= otherPlayer.getPlayerY() + 4 ||
            // player.getPlayerY() + 4 >= otherPlayer.getPlayerY() + otherPlayer.getPlayerSize())) {
            //     if (otherPlayer.getPlayerLife() == 1) {
            //         otherPlayer.deductLife();
            //         System.out.println(otherPlayer.getPlayerLife());
            //         System.out.println("Game Over!");
            //         player.setHitLock(true);
            //     } else {
            //         otherPlayer.deductLife();
            //         System.out.println(otherPlayer.getPlayerLife());
            //         player.setHitLock(true);
            //     }
            // } else {
            //     System.out.println("Miss!");
            //     player.setHitLock(true);
            // }
        }
    }

    public void punch() {
        if (!(player.getPlayerX() + player.getPlayerSize() <= otherPlayer.getPlayerX() + 20 ||
            player.getPlayerX() + 20 >= otherPlayer.getPlayerX() + otherPlayer.getPlayerSize() ||
            player.getPlayerY() + player.getPlayerSize() <= otherPlayer.getPlayerY() + 4 ||
            player.getPlayerY() + 4 >= otherPlayer.getPlayerY() + otherPlayer.getPlayerSize())) {
                if (otherPlayer.getPlayerLife() == 1) {
                    otherPlayer.deductLife();
                    System.out.println(otherPlayer.getPlayerLife());
                    System.out.println("Game Over!");
                    player.setHitLock(true);
                } else {
                    otherPlayer.deductLife();
                    System.out.println(otherPlayer.getPlayerLife());
                    player.setHitLock(true);
                }
            } else {
                System.out.println("Miss!");
                player.setHitLock(true);
            }
        shootBuffer = clock.millis();
    }

    // -- key listeners --

    @Override
    public void keyTyped(KeyEvent e) {
        // not needed for game
    }

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            player.setUp(true);
        }

        if (code == KeyEvent.VK_S) {
            player.setDown(true);
        }

        if (code == KeyEvent.VK_A) {
            player.setLeft(true);
        }

        if (code == KeyEvent.VK_D) {
            player.setRight(true);
        }

        if (code == KeyEvent.VK_P) {
            player.setHit(true);
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {

        int code = e.getKeyCode();

        if (code == KeyEvent.VK_W) {
            player.setUp(false);
        }

        if (code == KeyEvent.VK_S) {
            player.setDown(false);
        }

        if (code == KeyEvent.VK_A) {
            player.setLeft(false);
        }

        if (code == KeyEvent.VK_D) {
            player.setRight(false);
        }

        if (code == KeyEvent.VK_P) {
            player.setHit(false);
            player.setHitLock(false);
        }

    }

    // -- server things ---

    // connect to gameserver
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

            rfsRunnable = new ReadFromServer(in);
            wtsRunnable = new WriteToServer(out);
            rfsRunnable.waitForStartMsg();
        }

        catch (IOException ex) {
            System.out.println("IOException from connectToServer()");
        }

    }

    // read from the server
    private class ReadFromServer implements Runnable {
        
        private DataInputStream dataIn;

        // creates the runnable
        public ReadFromServer(DataInputStream in) {
            dataIn = in;
            System.out.println("RFS Runnable created!");
        }

        // the main code for the runnable
        // gets the coordinates of the other player (and moves them accordingly)
        // gets the current sprite of the other player (and animates them accordingly)
        public void run() {
            try {
                while(true) {
                    int p2x = dataIn.readInt();
                    int p2y = dataIn.readInt();
                    int p2s = dataIn.readInt();
                    int p2l = dataIn.readInt();
                    if (otherPlayer != null) {
                        otherPlayer.setPlayerX(p2x);
                        otherPlayer.setPlayerY(p2y);
                        otherPlayer.setPlayerSprite(p2s);
                        // otherPlayer.setPlayerLife(p2l);
                    }
                }
            }
            catch (IOException ex) {
                System.out.println("IOException from RFS run()");
            }
        }

        // wait for start msg from server before starting
        // after starting, run all threads
        public void waitForStartMsg() {
            try {
                String startMsg = dataIn.readUTF();
                System.out.println("Message from server: " + startMsg);
                Thread readThread = new Thread(rfsRunnable);
                Thread writeThread = new Thread(wtsRunnable);
                readThread.start();
                writeThread.start();
            }
            catch (IOException ex) {
                System.out.println("IOException from waitForStartMsg()");
            }
        }

    }

    // write to the server
    private class WriteToServer implements Runnable {
        
        private DataOutputStream dataOut;

        // creates the runnable
        public WriteToServer(DataOutputStream out) {
            dataOut = out;
            System.out.println("WTS Runnable created!");
        }

        // the main code
        // sends coordinates of player to server
        // sends current sprite of player to server
        public void run() {
            
            try {
                while (true) {
                    if (player != null) {
                        dataOut.writeInt(player.getPlayerX());
                        dataOut.writeInt(player.getPlayerY());
                        dataOut.writeInt(player.getCurrentSprite());
                        dataOut.writeInt(player.getPlayerLife());
                        dataOut.flush();
                    }

                    try {
                        Thread.sleep(25); // some delay for writing data
                    }
                    catch (InterruptedException ex) {
                        System.out.println("InterruptedException from WTS run()");
                    }
                }
            }
            catch (IOException ex) {
                System.out.println("IOException from WTS run()");
            }
            
        }

    }

}