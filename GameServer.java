// handles the server
// run this first before GameStarter!

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

    private int p1x, p1y, p1s, p2x, p2y, p2s, p1l, p2l;

    // creates the main serversocket, instantiates variables
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

    // main method that starts server
    public static void main(String[] args) {
        GameServer gs = new GameServer();
        gs.acceptConnections();
    }

    // accept for connections of players before starting game
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

    // --- inner classes for threads! ---

    // read from the client
    private class ReadFromClient implements Runnable {

        private int playerID;
        private DataInputStream dataIn;

        // creates the runnable
        public ReadFromClient(int pid, DataInputStream in) {
            playerID = pid;
            dataIn = in;

            System.out.println("RFC " + playerID + " Runnable created!");
        }

        // the main code
        // gets the player coordinates from the client
        // gets the player sprite from the client
        public void run() {
            try {
                while (true) {
                    if (playerID == 1) {
                        p1x = dataIn.readInt();
                        p1y = dataIn.readInt();
                        p1s = dataIn.readInt();
                        p1l = dataIn.readInt();
                    }
                    else {
                        p2x = dataIn.readInt();
                        p2y = dataIn.readInt();
                        p2s = dataIn.readInt();
                        p2l = dataIn.readInt();
                    }
                }
            }
            catch (IOException ex) {
                System.out.println("IOException from RFC run()");
            }

        }

    }

    // write to the client
    private class WriteToClient implements Runnable {

        private int playerID;
        private DataOutputStream dataOut;

        // creates the runnable
        public WriteToClient(int pid, DataOutputStream out) {
            playerID = pid;
            dataOut = out;

            System.out.println("RFC " + playerID + " Runnable created!");
        }

        // the main code
        // returns the coordinates of the other player to the client
        // returns the sprite of the other player to the client
        // returns results of collision detection for the player to handle
        // (e.g. if collision is true, send that value. player will have a method
        // to handle that)
        public void run() {
            try {
                while(true) {
                    if (playerID == 1) {
                        dataOut.writeInt(p2x);
                        dataOut.writeInt(p2y);
                        dataOut.writeInt(p2s);
                        dataOut.writeInt(p2l);
                        dataOut.flush();
                    }
                    else {
                        dataOut.writeInt(p1x);
                        dataOut.writeInt(p1y);
                        dataOut.writeInt(p1s);
                        dataOut.writeInt(p2l);
                        dataOut.flush();
                    }

                    try {
                        Thread.sleep(25);
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

        // send a start message for the server
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