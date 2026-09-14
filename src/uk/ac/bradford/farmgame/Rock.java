package uk.ac.bradford.farmgame;
 
public class Rock extends Entity {
 
    public int health = 3;   // Add durability for Task 14
 
    public Rock(int x, int y) {
        setPosition(x, y);
    }
}