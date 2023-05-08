// this will start the game
// call the gameframe, gamecanvas, etc.

public class GameStarter {

    public static void main(String[] args) {

        GameFrame game = new GameFrame(800, 640);

        game.setUpFrame();
        game.startAnimationThread();
        game.update();

    }

}