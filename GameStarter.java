/**
 * This class starts the game. It does so by calling important methods that
 * set up the frame and its elements for the user.
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

public class GameStarter {

    /**
     * This main method instantiates a GameFrame object. It then
     * calls its important methods to set up the frame for the
     * player.
     */

    public static void main(String[] args) {

        GameFrame game = new GameFrame(800, 640);

        game.connectToServer();
        game.setUpFrame();
        game.startAnimationThread();

    }

}