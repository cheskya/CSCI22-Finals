// handles the server

import java.io.*;
import java.net.*;

public class GameServer {

    private ServerSocket ss;
    private int numPlayers;

    // constructor
    public GameServer() {

        System.out.println("Game Server!");

        // connect to port
        try {
            ss = new ServerSocket(51734); // port number from sir, might change
        }
        catch (IOException ex) {
            System.out.println("IOException from GameServer Constructor");
        }

    }

    // instructions for the server, waiting for connections
    public void acceptConnections() {

        try {
            System.out.println("Waiting for connections...");
            while (numPlayers < 2) {
                Socket s = ss.accept();
                numPlayers++;
                System.out.println("Player #" + numPlayers + " has connected!");
            }
            System.out.println("There are now 2 players. No longer accepting connections.");
        }
        catch (IOException ex) {
            System.out.println("IOException from acceptConnections()");
        }

    }

}