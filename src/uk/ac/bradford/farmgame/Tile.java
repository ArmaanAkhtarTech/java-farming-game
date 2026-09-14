
package uk.ac.bradford.farmgame;

/**
 * A class to create Tile objects used in the game. A Tile object represents one single
 * tile in the game. Each Tile object has a type, defining their appearance and their
 * their purpose in the game.
 * 
 * @author prtrundl
 */
public class Tile {
    
    /**
     * A type for this Tile object. The type affects what is drawn to the screen
     * and is used for checking what kind of Tile it is in the game for e.g.
     * changing tools, tilling dirt etc. The type must use one of the values from the
     * TileType enumeration defined below.
     */
    private TileType type;
    
    /**
     * An enumeration type to restrict the type of Tile objects to one from a set
     * of fixed values. Each type has an associated graphic for drawing to the
     * screen which is set when the Tile object constructor is called.
     */
    public enum TileType {
    AXE_BOX, BED, CROP, DIRT, HOE_BOX, HOUSE_FLOOR,
    PICKAXE_BOX, SEED_BOX, SOWED_DIRT, STONE_GROUND,
    TILLED_DIRT, WALL,

    // Added for Tasks 15–19
    WATERED_DIRT,
    WATERING_CAN_BOX,
    SIGNPOST,
    SHOP_FLOOR,
    SHOP_COUNTER
}

    
    /**
     * Constructor for creating Tile objects. You must pass one of the permitted
     * values from the TileType enumeration into this constructor when you call it,
     * for example by passing the value TileType.BACKGROUND between the brackets of the
     * call to the constructor.
     * @param t the type for this Tile object; a value from the TileType enumeration
     */
    public Tile(TileType t) {
        type = t;
    }
        
    /**
     * Get the type for this Tile object. The value will be one of those
     * defined in the TileType enumeration in the Tile class, e.g. TileType.DIRT
     * or TileType.WALL
     * @return the TIleType for this tile; one value from the TIleType enumeration defined above
     */
    public TileType getType() {
        return this.type;
    }
    
}
