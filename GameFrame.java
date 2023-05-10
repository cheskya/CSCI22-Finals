// this will be the frame of the game
// TITLE: DREAM TEAM (by JV :D)

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class GameFrame implements KeyListener, Runnable {

    public int width, height;
    public JFrame frame;

    public GameCanvas canvas;
    public Player p1, p2; // the current player is p1
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
        System.out.println("This player is currently PID" + playerID);
        if (playerID == 1) {
            canvas.p1 = new Player(0, 0, 1);
            canvas.p2 = new Player(100, 100, 2);
        }
        else {
            canvas.p1 = new Player(100, 100, 2);
            canvas.p2 = new Player(0, 0, 1);
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

    // --- key listeners ---

    // @Override
    // public void keyTyped(KeyEvent e) {

    // }

    // @Override
    // public void keyPressed(KeyEvent e) {
    //     int code = e.getKeyCode();

    //     if (code == KeyEvent.VK_W) {
    //         if (playerID == 1) {
    //             player.setUp(true);
    //         } else {
    //             otherPlayer.setUp(true);
    //         }
    //     }

    //     if (code == KeyEvent.VK_S) {
    //         if (playerID == 1) {
    //             player.setDown(true);
    //         } else {
    //             otherPlayer.setDown(true);
    //         }
    //     }

    //     if (code == KeyEvent.VK_A) {
    //         if (playerID == 1) {
    //             player.setLeft(true);
    //         } else {
    //             otherPlayer.setLeft(true);
    //         }
    //     }

    //     if (code == KeyEvent.VK_D) {
    //         if (playerID == 1) {
    //             player.setRight(true);
    //         } else {
    //             otherPlayer.setRight(true);
    //         }
    //     }

    // }

    // @Override
    // public void keyReleased(KeyEvent e) {
    //     int code = e.getKeyCode();

    //     if (code == KeyEvent.VK_W) {
    //         if (playerID == 1) {
    //             player.setUp(false);
    //         } else {
    //             otherPlayer.setUp(false);
    //         }
    //     }

    //     if (code == KeyEvent.VK_S) {
    //         if (playerID == 1) {
    //             player.setDown(false);
    //         } else {
    //             otherPlayer.setDown(false);
    //         }
    //     }

    //     if (code == KeyEvent.VK_A) {
    //         if (playerID == 1) {
    //             player.setLeft(false);
    //         } else {
    //             otherPlayer.setLeft(false);
    //         }
    //     }

    //     if (code == KeyEvent.VK_D) {
    //         if (playerID == 1) {
    //             player.setRight(false);
    //         } else {
    //             otherPlayer.setRight(false);
    //         }
    //     }

    @Override
    public void keyTyped(KeyEvent e) {

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

    }

    // updates the animation of players and enemies
    // TODO: give the function a more descriptive name
    public void update() {
        player.movePlayer();
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
        // gets the coordinates of the other player
        // moves the other player accordingly
        public void run() {
            try {
                while(true) {
                    int p2x = dataIn.readInt();
                    int p2y = dataIn.readInt();
                    if (otherPlayer != null) {
                        otherPlayer.setPlayerX(p2x);
                        otherPlayer.setPlayerY(p2y);
                    }
                }
            }
            catch (IOException ex) {
                System.out.println("IOException from RFS run()");
                System.out.println(ex.toString());
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
        public void run() {
            
            try {
                while (true) {
                    if (player != null) {
                        dataOut.writeInt(player.getPlayerX());
                        dataOut.writeInt(player.getPlayerY());
                        dataOut.flush();
                    }

                    try {
                        Thread.sleep(25); // some delay for writing data (might have lag)
                    }
                    catch (InterruptedException ex) {
                        System.out.println("InterruptedException from WTS run()");
                    }
                }
            }
            catch (IOException ex) {
                System.out.println("IOException from WTS run()");
                System.out.println(ex.toString());
            }
            
        }

    }

}