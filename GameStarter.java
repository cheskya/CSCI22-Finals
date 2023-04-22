// this will start the game
// call the gameframe, gamecanvas, etc.

public class GameStarter {
    
    public static void main(String[] args) {
        GameFrame gf = new GameFrame();
        gf.setUpGUI();
        gf.startGameThread();
        gf.run();
        gf.update();
    }
}