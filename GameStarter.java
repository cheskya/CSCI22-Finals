// this will start the game
// call the gameframe, gamecanvas, etc.

public class GameStarter {

    public static void main(String[] args) {

        GameServer gs = new GameServer();
        GameFrame game = new GameFrame(640, 640);

        gs.acceptConnections();
        game.connectToServer();
        game.setUpFrame();
        game.startAnimationThread();
        game.update();

    }

}