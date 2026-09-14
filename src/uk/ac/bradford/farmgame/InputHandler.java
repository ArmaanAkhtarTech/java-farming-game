package uk.ac.bradford.farmgame;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * This class handles keyboard events (key presses) captured by a GameGUI object
 * that are passed to an instance of this class. The class is responsible for
 * calling methods in the GameEngine class that will update tiles, player and
 * the pest for the various keystrokes that are handled.
 *
 * @author prtrundl
 */
public class InputHandler implements KeyListener {

    GameEngine engine;      //GameEngine that this class calls methods from

    /**
     * Constructor that forms a connection between a GameInputHandler object and
     * a GameEngine object. The GameEngine object registered here is the one
     * that will have methods called to change Entity positions etc.
     *
     * @param eng The GameEngine object that this GameInputHandler is linked to
     */
    public InputHandler(GameEngine eng) {
        engine = eng;
    }

    /**
     * Method to handle key presses captured by the GameGUI. The method calls
     * the game engine doTurn method to process a game turn for ANY key press,
     * but if one of the four arrow keys are pressed it also calls a
     * method in the engine to update the game by moving the player.
     *
     * @param e A KeyEvent object generated when a keyboard key is pressed
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (engine.sleeping()) return;
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:        //handle up arrow key
                engine.evenBetterMovePlayer(1);
                break;
            case KeyEvent.VK_RIGHT:     //handle right arrow
                engine.evenBetterMovePlayer(2);
                break;
            case KeyEvent.VK_DOWN:      //handle down arrow
                engine.evenBetterMovePlayer(3);
                break;
            case KeyEvent.VK_LEFT:      //handle left arrow key
                engine.evenBetterMovePlayer(4);
                break;
        }
        engine.doTurn();    //any key press will result in this method being called
    }
    
    /**
     * Unused method
     *
     * @param e
     */
    @Override
    public void keyTyped(KeyEvent e) {
    }

    /**
     * Unused method
     *
     * @param e
     */
    @Override
    public void keyReleased(KeyEvent e) {
    }

}
