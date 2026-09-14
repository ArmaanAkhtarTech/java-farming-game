package uk.ac.bradford.farmgame;

/**
 * The Player class is a subclass of Entity and adds specific state and
 * behaviour for the player in the game, including what item (if any) the player
 * is holding based on their actions in the game.
 *
 * @author prtrundl
 */
public class Player extends Entity {
    
    /**
     * A numerical code for what the player is currently holding. By default the game
     * will draw a tool or seed bag when this attribute has one of the following
     * values:
     * 1 - a hoe to till dirt
     * 2 - a seed bag to plant seeds
     * 3 - an axe to cut trees
     * 4 - a pickaxe to break stones
     * A value of 0 for this attribute will draw the player with no tool in hand
     */
    private int holding = 0;

    /**
     * This constructor is used to create a Player object to use in the game
     *
     * @param x the starting X position of this Player in the game
     * @param y the starting Y position of this Player in the game
     */
    public Player(int x, int y) {
        setPosition(x, y);
    }

    /**
     * A getter method to check if the player is holding a tool or item. Used to check
     * what is held by the player when taking actions in the game that require the player
     * to hold a specific item e.g. an axe or seed bag.
     * @return a numerical value representing the currently held item: 0 is no item, 1 is
     * the how to till dirt, 2 is the seed bag to sow seeds, 3 is the axe to cut trees and 4 is
     * the pickaxe to break rocks.
     */
    public int getHeldItem() {
        return holding;
    }
    
    /**
     * A setter to change the held item for the player. Used for actions that
     * require the player to be holding a certain items to perform them e.g. 
     * planting seeds that requires a seed bag.
     * @param a the numerical value representing the item the player will now hold: 0 is no item, 1 is
     * the how to till dirt, 2 is the seed bag to sow seeds, 3 is the axe to cut trees and 4 is
     * the pickaxe to break rocks.
     */
    public void setHeldItem(int a) {
        holding = a;
    }
}
