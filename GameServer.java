// handles the server

import java.io.*;
import java.net.*;

public class GameServer {

    private ServerSocket ss;
    private int numPlayers;
    private int maxPlayers;

    // constructor
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
            System.out.println(ex);
        }

    }

    // instructions for the server, waiting for connections
    public void acceptConnections() {

        try {
            System.out.println("Waiting for connections...");
            while (numPlayers < maxPlayers) {
                Socket s = ss.accept();
                DataInputStream in = new DataInputStream(s.getInputStream());
                DataOutputStream out = new DataOutputStream(s.getOutputStream());

                numPlayers++;
                out.writeInt(numPlayers);
                System.out.println("Player #" + numPlayers + " has connected!");
            }
            System.out.println("There are now 2 players. No longer accepting connections.");
        }
        catch (IOException ex) {
            System.out.println("IOException from acceptConnections()");
        }

    }

    public static void main(String[] args) {
        GameServer gs = new GameServer();
        gs.acceptConnections();
    }

}