/**
 * This class creates the main frame of the game. It is the main class sending
 * and receiving data from the server. It handles various important game mechanics.
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
    public Player winningPlayer, losingPlayer;

    public GameHUD hud;
    public GameMusic music;

    private Thread animationThread;
    private int FPS = 60;

    public int playerID;
    private Socket socket;
    private ReadFromServer rfsRunnable;
    private WriteToServer wtsRunnable;

    public boolean isGameFinished;
    public boolean hasPlayerWon; // checks if player is the one who won, and not the other

    /**
     * This constructor creates all the needed objects for the GameFrame. It
     * also initializes many important variables, and uses methods to set up
     * the GameFrame for use.
     * 
     * @param w     the width of the frame
     * @param h     the height of the frame
     */

    public GameFrame(int w, int h) {
        width = w;
        height = h;

        frame = new JFrame();
        canvas = new GameCanvas(width, height);
        music = new GameMusic();

        hud = canvas.getCurrentScreen().getHUD();

        frame.addKeyListener(this);
        frame.setFocusable(true);

        clock = clock.systemUTC();

        isGameFinished = false;
        hasPlayerWon = false;
    }

    // --- methods for setting up elements ---

    /**
     * This method creates the actual frame that can be seen and used by
     * the user. It starts the background music, and adds both the canvas
     * and the players.
     */

    public void setUpFrame() {

        Container contentPane = frame.getContentPane();

        contentPane.add(canvas, BorderLayout.CENTER);

        createSprites();
        music.playBGM();

        frame.setSize(width, height);
        frame.pack();
        frame.setTitle("Dream Clash - Player #" + playerID);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }

    /**
     * This method creates the player sprites in the canvas. Depending on the
     * current player ID, it creates the corresponding player sprite and places
     * it at the right coordinates.
     */

     public void createSprites() {
        // p1 is you, p2 is friend
        // 1 is sister, 2 is brother (3rd parameter of Player)
        if (playerID == 1) {
            // player becomes the sister
            canvas.p1 = new Player(32, 32, 1);
            canvas.p2 = new Player(528, 544, 2);
        }
        else {
            // player becomes the brother
            canvas.p1 = new Player(528, 544, 2);
            canvas.p2 = new Player(32, 23, 1);
        }
        player = canvas.p1;
        otherPlayer = canvas.p2;

    }

    // --- animation thread ---

    /**
     * This method starts the thread that handles updating the animation
     * of the GameFrame.
     */

    public void startAnimationThread() {
        animationThread = new Thread(this);
        animationThread.start();
    }

    /**
     * This method is from the Runnable interface. When the animationThread is
     * started, it updates the player's movement, animation, hits, and collisions.
     * Then, it repaints the canvas according to the FPS.
     */

    // code inspiration from https://youtu.be/VpH33Uw-_0E

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

    /**
     * This method is in charge of updating various important mechanics in the game.
     * Specifically, it is in charge of moving and animating the player. It is also in charge
     * of handling the collisions and punches of the player.
     */

    public void update() {
        if (isGameFinished != true) {
            handlePlayerEdgeCollision();
            playerPunchAvailability();
            player.movePlayer();
            player.animatePlayer();
        }
    }

    // --- methods for important mechanics ---

    // handles the collisions of the player with edge
    // not too heavy on program, so made it client-side

    /**
     * This method helps the player handle collisions with the edges of
     * the map. If a player collides with an edge, it uses the corresponding
     * collide method of the player.
     */

    public void handlePlayerEdgeCollision() {
        // left edge
        if (player.getPlayerX() <= 0) {
            player.collideLeft(true);
        }
        else {
            player.collideLeft(false);
        }
        // right edge
        if (player.getPlayerX() + player.getPlayerSize() >= 640) { // hardcoded width (because of hud)
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

    /**
     * This method checks if the player is able to punch. It prevents the player
     * from simply holding down the button to punch. Because of this method, the player
     * can only punch once per press with a delay.
     */

    // boolean flag to stop player from holding down the button:
    // https://stackoverflow.com/questions/48120739/how-to-prevent-repeated-actions-from-a-key-being-held-down
    
    public void playerPunchAvailability() {
        if (player.isHitPressed && !player.hitLock && clock.millis() - shootBuffer > 1000) {
            punch();
        }
    }

    /**
     * This method checks if players are hitting each other when they press the punch key. If they do,
     * a life is deducted from the player who was hit. If the other player has no more lives
     * left, the game is ended.
     */

    public void punch() {
        if (!(player.getPlayerX() + player.getPlayerSize() <= otherPlayer.getPlayerX() + 20 ||
            player.getPlayerX() + 20 >= otherPlayer.getPlayerX() + otherPlayer.getPlayerSize() ||
            player.getPlayerY() + player.getPlayerSize() <= otherPlayer.getPlayerY() + 4 ||
            player.getPlayerY() + 4 >= otherPlayer.getPlayerY() + otherPlayer.getPlayerSize())) {
                music.playHit1Jingle();
                if (otherPlayer.getPlayerLife() == 1) {
                    music.playLoseJingle();
                    music.playWinJingle();
                    otherPlayer.deductLife();
                    //System.out.println(otherPlayer.getPlayerLife());
                    hasPlayerWon = true;
                    finishGame();
                    System.out.println("You win!");
                    player.setHitLock(true);
                } else {
                    otherPlayer.deductLife();
                    // System.out.println(otherPlayer.getPlayerLife());
                    player.setHitLock(true);
                }
                hud.setPlayerLifeHUD(otherPlayer.getPlayerID(), otherPlayer.getPlayerLife());
            } else {
                System.out.println("Miss!");
                player.setHitLock(true);
            }
        shootBuffer = clock.millis();
    }

    /**
     * This method signals that the game as ended, which stops the game.
     * Depending on which player has won, it sets the sprites accordingly.
     */

     public void finishGame() {

        // when game is finished...
        // inhibit all keyboard controls
        // switch to winning/losing sprites

        isGameFinished = true;

        // you won
        if (hasPlayerWon) {
            player.setPlayerSprite(11);
            otherPlayer.setPlayerSprite(12);
            hud.setResults(player.getPlayerID());
        }
        // the other player won
        else {
            otherPlayer.setPlayerSprite(11);
            player.setPlayerSprite(12);
            hud.setResults(otherPlayer.getPlayerID());
        }

    }

    // --- key listeners ---

    /**
     * This method is from the KeyListener interface. It checks for which
     * keys are typed. Because this is not used for the game, it is left blank.
     */

    @Override
    public void keyTyped(KeyEvent e) {
        // not needed for game
    }

    /**
     * This method is from the KeyListener interface. It checks for which keys are
     * pressed. The game used the WASD keys to move the player, while the P key
     * is reserved for punching. When the user presses a key, it uses the corresponding
     * code to activiate an event. This stops accepting input if the game is finished.
     */

    @Override
    public void keyPressed(KeyEvent e) {

        int code = e.getKeyCode();

        if (isGameFinished != true) {

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

    }

    /**
     * This method is from the KeyListener interface. It checks for which keys are
     * pressed. The game used the WASD keys to move the player, while the P key
     * is reserved for punching. When the user releases a key, it stops the event previously activated.
     */

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

    // --- server code ---

    /**
     * This method allows the GameFrame to connect to GameServer. After both players have connected,
     * it starts the neccessary read and write threads needed to communicate between the client
     * and the server.
     */

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

    /**
     * This class reads data from the server. Then, it assigns them to
     * their corresponding variables and updates objects accordingly.
     */

    private class ReadFromServer implements Runnable {
        
        private DataInputStream dataIn;

        /**
         * This constructor creates the thread using the passed values.
         * Then, it alerts the user that the thread has been created.
         * 
         * @param in    the input stream from the server that the thread will work with
         */

        public ReadFromServer(DataInputStream in) {
            dataIn = in;
            System.out.println("RFS Runnable created!");
        }

        /**
         * This method is from the Runnable interface. When the thread starts, it
         * continuously reads data from the server. After, it updates some of the
         * otherPlayer's data and some data for the HUD. It also checks for whether
         * the game has ended for the other player and ends the game accordingly.
         */

        public void run() {
            try {
                while(true) {
                    int p2x = dataIn.readInt();
                    int p2y = dataIn.readInt();
                    int p2s = dataIn.readInt();
                    int p2l = dataIn.readInt();
                    int p2ls = dataIn.readInt();
                    int gg = dataIn.readInt();
                    if (otherPlayer != null) {
                        otherPlayer.setPlayerX(p2x);
                        otherPlayer.setPlayerY(p2y);
                        otherPlayer.setPlayerSprite(p2s);
                        //otherPlayer.setPlayerLife(p2l); // this one conflicts with deductLife()
                        hud.setPlayerLifeHUD(player.getPlayerID(), p2ls);
                        if (gg == 1) {
                            finishGame();
                        }
                    }
                }
            }
            catch (IOException ex) {
                System.out.println("IOException from RFS run()");
            }
        }

        /**
         * This method waits for a start message from the server before starting
         * the game. When both players have connected, it starts the read and write
         * threads.
         */
        
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

    
    /**
     * This class gathers data from various objects. Then, it
     * writes the corresponding data to the server.
     */

    private class WriteToServer implements Runnable {
        
        private DataOutputStream dataOut;

        // creates the runnable

        /**
         * This constructor creates the thread using the passed values.
         * Then, it alerts the user that the thread has been created.
         * 
         * @param out    the output stream from the server that the thread will work with
         */

        public WriteToServer(DataOutputStream out) {
            dataOut = out;
            System.out.println("WTS Runnable created!");
        }

        // the main code
        // sends coordinates of player to server
        // sends current sprite of player to server

        /**
         * This method is from the Runnable interface. When the thread starts, it
         * gathers data from some objects in the GameFrame. Then, it continuously
         * writes that data to the client.
         */

        public void run() {
            try {
                while (true) {

                    int gameOver;
                    if (isGameFinished) {
                        gameOver = 1;
                    }
                    else {
                        gameOver = 0;
                    }

                    if (player != null) {
                        dataOut.writeInt(player.getPlayerX());
                        dataOut.writeInt(player.getPlayerY());
                        dataOut.writeInt(player.getCurrentSprite());
                        dataOut.writeInt(player.getPlayerLife());
                        dataOut.writeInt(hud.getPlayerLifeHUD(otherPlayer.getPlayerID()));
                        dataOut.writeInt(gameOver);
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