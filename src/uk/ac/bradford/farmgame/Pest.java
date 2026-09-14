package uk.ac.bradford.farmgame;

/**The Pest class is a subclass of Entity and implements the
 * pest concept in the game. The pest moves towards crops once they have grown
 * and "eats" them unless stopped by the player.
 *
 * @author prtrundl
 */
public class Pest extends Entity {
    
    /**
     * This constructor is used to create a Pest object to use in the game.
     * @param x the starting X position of this Pest in the level
     * @param y the starting Y position of this Pest in the level
     */
    public Pest(int x, int y) {
        setPosition(x, y);
    }
}
