// handles the server
// run this first before GameStarter!

/**
 * This class functions as the server of the game. It reads
 * values from the clients and sends the corresponding values
 * back to them. It also checks whether both players are available
 * before starting the game.
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

import java.io.*;
import java.net.*;

public class GameServer {

    private ServerSocket ss;
    private int numPlayers;
    private int maxPlayers;

    private Socket p1Socket;
    private Socket p2Socket;
    private ReadFromClient p1ReadRunnable;
    private ReadFromClient p2ReadRunnable;
    private WriteToClient p1WriteRunnable;
    private WriteToClient p2WriteRunnable;

    // note:
    // p1x and p2x are for x coords
    // p1y and p2y are for y coords
    // p1s and p2s are for player sprites
    // p1l and p2l are for player lives (values)
    // p1ls and p2ls are for life sprites
    // gg is for determing whether game is over

    private int p1x, p1y, p1s, p2x, p2y, p2s, p1l, p2l, p1ls, p2ls, gg;

    // creates the main serversocket, instantiates variables

    /**
     * This constructor initializes some important values. Then, it sets up the
     * server of the game.
     */

    public GameServer() {

        System.out.println("Game Server!");

        numPlayers = 0;
        maxPlayers = 2;

        // connect to port
        try {
            ss = new ServerSocket(58976); // port number from sir, might change
        }
        catch (IOException ex) {
            System.out.println("IOException from GameServer Constructor");
        }

    }

    /**
     * This main method creates a server for the game. Then, it allows for
     * connections with other users who want to play the game.
     */

    public static void main(String[] args) {
        GameServer gs = new GameServer();
        gs.acceptConnections();
    }

    // --- accept connections method ---

    /**
     * This method accepts connections until the number of max players have
     * been reached. When a player connects, it creates the necessary sockets
     * and threads. Once two players have connected, it starts the threads
     * for reading and writing between the client and the server.
     */

    public void acceptConnections() {

        try {
            
            System.out.println("Waiting for connections...");
            
            // wait for connections
            while (numPlayers < maxPlayers) {

                Socket s = ss.accept();
                DataInputStream in = new DataInputStream(s.getInputStream());
                DataOutputStream out = new DataOutputStream(s.getOutputStream());

                numPlayers++;
                out.writeInt(numPlayers); // this will become the player id in gameframe
                System.out.println("Player #" + numPlayers + " has connected!");

                ReadFromClient rfc = new ReadFromClient(numPlayers, in);
                WriteToClient wtc = new WriteToClient(numPlayers, out);

                if (numPlayers == 1) {
                    p1Socket = s;
                    p1ReadRunnable = rfc;
                    p1WriteRunnable = wtc;
                }
                else {
                    p2Socket = s;
                    p2ReadRunnable = rfc;
                    p2WriteRunnable = wtc;
                    p1WriteRunnable.sendStartMsg();
                    p2WriteRunnable.sendStartMsg();
                    // after both players have connected, start threads
                    Thread readThread1 = new Thread(p1ReadRunnable);
                    Thread readThread2 = new Thread(p2ReadRunnable);
                    readThread1.start();
                    readThread2.start();
                    Thread writeThread1 = new Thread(p1WriteRunnable);
                    Thread writeThread2 = new Thread(p2WriteRunnable);
                    writeThread1.start();
                    writeThread2.start();
                }
            }

            System.out.println("There are now 2 players. No longer accepting connections.");
        
        }
        catch (IOException ex) {
            System.out.println("IOException from acceptConnections()");
        }

    }

    // --- threads for reading and writing ---

    /**
     * This class reads data from the client. Then, it assigns them to
     * their corresponding variables.
     */

    private class ReadFromClient implements Runnable {

        private int playerID;
        private DataInputStream dataIn;

        /**
         * This constructor creates the thread using the passed values.
         * Then, it alerts the user that the thread has been created.
         * 
         * @param pid   the player ID assigned to the thread
         * @param in    the input stream from the client that the thread will work with
         */

        public ReadFromClient(int pid, DataInputStream in) {
            playerID = pid;
            dataIn = in;

            System.out.println("RFC " + playerID + " Runnable created!");
        }

        /**
         * This method is from the Runnable interface. When the thread starts, it
         * continuously reads data from the client.
         */

        @Override
        public void run() {
            try {
                while (true) {
                    if (playerID == 1) {
                        p1x = dataIn.readInt(); // x-coordinate
                        p1y = dataIn.readInt(); // y-coordinate
                        p1s = dataIn.readInt(); // current player sprite
                        p1l = dataIn.readInt(); // current player lives
                        p1ls = dataIn.readInt(); // current lives sprite
                        gg = dataIn.readInt(); // game over state
                    }
                    else {
                        p2x = dataIn.readInt();
                        p2y = dataIn.readInt();
                        p2s = dataIn.readInt();
                        p2l = dataIn.readInt();
                        p2ls = dataIn.readInt();
                        gg = dataIn.readInt();
                    }
                }
            }
            catch (IOException ex) {
                System.out.println("IOException from RFC run()");
            }

        }

    }

    /**
     * This class manages the data gathered from the client. Then, it
     * writes the corresponding data back.
     */

    private class WriteToClient implements Runnable {

        private int playerID;
        private DataOutputStream dataOut;

        /**
         * This constructor creates the thread using the passed values.
         * Then, it alerts the user that the thread has been created.
         * 
         * @param pid   the player ID assigned to the thread
         * @param out   the output stream from the client that the thread will work with
         */

        public WriteToClient(int pid, DataOutputStream out) {
            playerID = pid;
            dataOut = out;

            System.out.println("RFC " + playerID + " Runnable created!");
        }

        /**
         * This method is from the Runnable interface. When the thread starts, it
         * continuously writes data to the client.
         */

         @Override
        public void run() {
            try {
                while(true) {
                    if (playerID == 1) {
                        dataOut.writeInt(p2x);
                        dataOut.writeInt(p2y);
                        dataOut.writeInt(p2s);
                        dataOut.writeInt(p2l);
                        dataOut.writeInt(p2ls);
                        dataOut.writeInt(gg);
                        dataOut.flush();
                    }
                    else {
                        dataOut.writeInt(p1x);
                        dataOut.writeInt(p1y);
                        dataOut.writeInt(p1s);
                        dataOut.writeInt(p1l);
                        dataOut.writeInt(p1ls);
                        dataOut.writeInt(gg);
                        dataOut.flush();
                    }

                    try {
                        Thread.sleep(25); // slight delay for writing data
                    }
                    catch (InterruptedException ex) {
                        System.out.println("InterruptedException from WTC run()");
                    }
                }
            }
            catch (IOException ex) {
                System.out.println("IOException from WTC run()");
            }
        }

        /**
         * This method sends a start message to the client to signal that
         * the game is ready to be played. This is only sent when two players
         * have connected to the server.
         */

        public void sendStartMsg() {
            try {
                dataOut.writeUTF("We now have two players. Go!");
            }
            catch (IOException ex) {
                System.out.println("IOException from sendStartMsg()");
            }
        }

    }

}