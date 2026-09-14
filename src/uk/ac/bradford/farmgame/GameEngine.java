package uk.ac.bradford.farmgame;

import java.util.Random;
import uk.ac.bradford.farmgame.Tile.TileType;

/**
 * The GameEngine class is responsible for managing information about the game,
 * creating the level, the player and pest, as well as updating information when
 * a key is pressed (processed by the InputHandler) while the game is running.
 *
 * @author prtrundl
 */
public class GameEngine {

    /**
     * The width of the level, measured in tiles. Changing this may cause the
     * display to draw incorrectly, and as a minimum the size of the GUI would
     * need to be adjusted.
     */
    public static final int LEVEL_WIDTH = 36;

    /**
     * The height of the level, measured in tiles. Changing this may cause the
     * display to draw incorrectly, and as a minimum the size of the GUI would
     * need to be adjusted.
     */
    public static final int LEVEL_HEIGHT = 20;

    /**
     * A random number generator that can be used to include randomised choices
     * in the creation of levels, in choosing places to place the player and other
     * objects, and to randomise movement etc. Passing an integer (e.g. 123) to
     * the constructor called here will give fixed results - the same numbers
     * will be generated every time WHICH CAN BE VERY USEFUL FOR TESTING AND
     * BUGFIXING!
     */
    private Random rng = new Random();

    /**
     * The current turn number. Increased by one every turn. Used to control
     * effects that only occur on certain turn numbers.
     */
    private int turnNumber = 1;

    /**
     * The amount of money the player has in the current game. Money is gained by
     * harvesting crops.
     */
    private int money = 0;

    /**
     * The GUI associated with this GameEngine object. This link allows the
     * engine to pass level and entity information to the GUI to be drawn.
     */
    private GameGUI gui;

    /**
     * The 2 dimensional array of tiles that represent the current level. The
     * size of this array should use the LEVEL_WIDTH (X dimension!) and LEVEL_HEIGHT
     * (Y dimension!) attributes when it is created. This is the array that is used
     * to draw images to the screen by the GUI class, and by you to check what a specific
     * Tile contains by checking the content of specific elements of the array and
     * using the getType() method to determine the type of the Tile.
     *
     * For example:
     *
     * level[10][15].getType() == TiletType.DIRT
     *
     * would return the value true if the tile at position 10,15 was a DIRT tile.
     */
    private Tile[][] level;

    /**
     * A Player object that is the current player. This object stores the state
     * information for the player, including the current position
     * (which is a pair of co-ordinates that corresponds to a tile in the
     * current level - see the Entity class for more information on the
     * co-ordinate system used as well as the coursework specification
     * document).
     */
    private Player player;

    /**
     * A Pest object used to create the pest when crops grow. The object
     * has position information stored via its attributes, and methods to check
     * and update the position of the pest.
     */
    private Pest pest;

    /**
     * An array of Entity objects that is used to store Tree and Rock objects for
     * one of the coursework tasks. New Tree and Rock objects should be created and
     * stored in this array for e.g. checking player movement as these objects
     * should block player movement into the tile that they are in.
     */
    private Entity[] debris;

    // Player inventory state
    private boolean hasHoe = false;
    private boolean hasSeeds = false;
    private boolean hasAxe = false;
    private boolean hasPickaxe = false;
    private boolean hasWateringCan = false;

    // NPC (used in later tasks)
    private Pest npc = null;
    private int daysSinceStart = 0;

    // Weather system (later task)
    private String currentWeather = "sunny";

    // === TASK 14 – TOOL DURABILITY ===
    private int hoeDurability = 3;
    private int axeDurability = 3;
    private int pickaxeDurability = 3;

    /**
     * Constructor that creates a GameEngine object and connects it with a
     * GameGUI object.
     *
     * @param gui The GameGUI object that this engine will pass information to
     * in order to draw levels and entities to the screen.
     */
    public GameEngine(GameGUI gui) {
        this.gui = gui;
    }

    /**
     * Creates a Player object in the game. This method should instantiate the
     * Player class by creating an object using the appropriate constructor. The
     * created object should then be assigned to the "player" attribute of this
     * class.
     *
     * This method should use fixed X and Y values for the player's position.
     */
    private void createPlayer() {
        player = new Player(32, 15);
    }

    /**
     * Handles the movement of the player when attempting to move in the game.
     * This method is automatically called by the GameInputHandler class when
     * the user has pressed one of the keys on the keyboard. Your code should
     * change the X and Y position of the player to place them in the correct tile
     * based on the direction of movement, i.e. changing their X or Y position by
     * +1 or -1. You will need to use the relevant method from the Player/Entity
     * classes to find the current position of the player, and set new values
     * for the X and Y position by using another method and passing it updated values.
     * @param direction the direction of movement based on the arrow key that was pressed:
     * 1 is up, 2 is right, 3 is down and 4 is left.
     */
    public void movePlayer(int direction) {
        if (player == null) return;

        int x = player.getX();
        int y = player.getY();

        if (direction == 1) y--;
        if (direction == 2) x++;
        if (direction == 3) y++;
        if (direction == 4) x--;

        player.setPosition(x, y);
    }

    /**
     * Generates a new level. This method should instantiate the level array, and
     * then fill it with Tile objects that are created inside this method.
     * This method is used for an early task, but later you will replace calls
     * to this method with calls to generateBetterFarm() instead.
     *
     * It is recommended that for this method you create the level array and then fill
     * it using for a pair of for loops, and create new Tile objects inside the inner loop.
     * Set their type using the constructor and the TileType enumeration, then
     * assign the Tile object to the current element in the array using the loop counters.
     * For this method you should use just Tile objects with the type STONE_GROUND.
     * */
    private void generateFarm() {
        level = new Tile[LEVEL_WIDTH][LEVEL_HEIGHT];

        for (int x = 0; x < LEVEL_WIDTH; x++) {
            for (int y = 0; y < LEVEL_HEIGHT; y++) {
                level[x][y] = new Tile(TileType.STONE_GROUND);
            }
        }
    }

    /**
     * This method is used in a later task to improve the movement logic for the player.
     * You should remove the line of code in this method and replace it with your own code.
     * You may like to start by copying and pasting your movement code from the movePlayer()
     * method and then editing it. This method should improve the movement code to prevent
     * the player from moving off the edge of the level (i.e. stopping movement that would
     * change the X or Y positions to be less than 0 or greater than the level width/height
     * values).
     * @param direction the direction of movement based on the arrow key that was pressed:
     * 1 is up, 2 is right, 3 is down and 4 is left.
     */
    public void betterMovePlayer(int direction) {
        if (player == null) return;

        int x = player.getX();
        int y = player.getY();

        if (direction == 1 && y > 0) y--;
        if (direction == 2 && x < LEVEL_WIDTH - 1) x++;
        if (direction == 3 && y < LEVEL_HEIGHT - 1) y++;
        if (direction == 4 && x > 0) x--;

        player.setPosition(x, y);
    }

    /**
     * This method is used in a later task to generate a better level with more features.
     * Leave the generateFarm() method code as it is and write your new code in this method.
     * Remove the call to generateFarm() that is in this method and then
     * begin writing your new code. You may wish to begin by copying and pasting the code
     * from generateFarm() into this method.
     *
     * This method should generate a farm with the same stone ground as before, but
     * should add a rectangular patch of dirt tiles somewhere in the level that will
     * be used with later tasks. For the highest marks in this task your dirt patch
     * should be dynamically placed i.e. its position and size is determined randomly
     * each time this method is called so it is different every time the game is started.
     */
    private void generateBetterFarm() {
        generateFarm();

        int startX = 5 + rng.nextInt(15);
        int startY = 5 + rng.nextInt(5);

        for (int x = startX; x < startX + 6; x++) {
            for (int y = startY; y < startY + 4; y++) {
                if (x < LEVEL_WIDTH && y < LEVEL_HEIGHT) {
                    level[x][y] = new Tile(TileType.DIRT);
                }
            }
        }

        level[5][5] = new Tile(TileType.HOE_BOX);
        level[30][5] = new Tile(TileType.SEED_BOX);
    }

    /**
     * This method is used in an even later task to generate an even better level with even more features!
     * Leave the generateBetterFarm() method code as it is and write your new code in this method.
     * Remove the call to generateBetterFarm() that is in this method and then
     * begin writing your new code.
     * You may wish to begin by copying and pasting the code from generateBetterFarm() into
     * this method.
     *
     * This method should generate the same farm as before with stone ground and a dirt patch,
     * but should also add a "farmhouse" using the farmhouse floor tiles and possibly wall tiles.
     * One bed should be placed somewhere in the house to be used in a later task. For top marks
     * in this task the farmhouse should be dynamically generated i.e. its size and/or position
     * is different each game, and ideally it should avoid overwriting the dirt patch previously created.
     */
    private void generateEvenBetterFarm() {
        generateBetterFarm();

        // House walls
        for (int x = 28; x < 36; x++) {
            level[x][13] = new Tile(TileType.WALL);
            level[x][18] = new Tile(TileType.WALL);
        }
        for (int y = 13; y < 19; y++) {
            level[28][y] = new Tile(TileType.WALL);
            level[35][y] = new Tile(TileType.WALL);
        }

        // House floor
        for (int x = 29; x < 35; x++) {
            for (int y = 14; y < 18; y++) {
                level[x][y] = new Tile(TileType.HOUSE_FLOOR);
            }
        }

        // Bed placement
        level[32][16] = new Tile(TileType.BED);
        level[32][18] = new Tile(TileType.HOUSE_FLOOR);

        // Trees and debris
        addDebris();

        // Tool boxes
        level[5][8] = new Tile(TileType.AXE_BOX);
        level[5][10] = new Tile(TileType.PICKAXE_BOX);

        // watering can task 17
        level[6][5] = new Tile(TileType.WATERING_CAN_BOX);

        // signpost for task 18
        level[10][2] = new Tile(TileType.SIGNPOST);

        // shop for task 19
        for (int x = 3; x <= 6; x++) {
            for (int y = 14; y <= 17; y++) {
                level[x][y] = new Tile(TileType.SHOP_FLOOR);
            }
        }
        level[3][15] = new Tile(TileType.SHOP_COUNTER);
    }

    /**
     * This method should add debris to the game in the form of Tree and Rock objects.
     * It should instantiate the debris array with a sensible length and then fill
     * the array with instances of the Tree and Rock classes that you create in this
     * method. For top marks in this task the objects should be randomly placed so
     * that their position is different each game. Ideally they should not be placed
     * in a way that prevents the player from playign the game (e.g. should not be placed
     * in a way that blocks the door to the farmhouse!)
     * Rocks and Trees should block player movement; add code to your newest
     * player movement method (depends on which tasks you have already completed!)
     */
    private void addDebris() {
        debris = new Entity[8];
        debris[0] = new Tree(10, 5);
        debris[1] = new Tree(15, 7);
        debris[2] = new Tree(8, 12);
        debris[3] = new Tree(20, 10);
        debris[4] = new Rock(12, 8);
        debris[5] = new Rock(18, 6);
        debris[6] = new Rock(7, 15);
        debris[7] = new Rock(22, 12);
    }

    /**
     * This method is used to make the crops grow overnight if any tiles exist
     * that have been tilled with the hoe and then seeded with the seed bag.
     * You should check every tile in the level 2D array and any that are of the
     * SOWED_DIRT type should be changed to be of the CROP type. You can do this
     * by creating a new Tile object with the correct type and adding it to the
     * level array in the position of the current SOWED_DIRT tile being checked.
     * A later task will require you to modify this method so that a Pest is created
     * whenever one or more crops are created.
     */
    public void growCrops() {
        boolean created = false;

        daysSinceStart++;

        // NPC comes during day 2
        if (daysSinceStart == 2 && npc == null) {
            int npcX = 10 + rng.nextInt(20);
            int npcY = 5 + rng.nextInt(10);
            npc = new Pest(npcX, npcY);
            System.out.println("A visitor has arrived at the farm!");
            System.out.println("Villager: Hello farmer! Here is 20 money!");
            money += 20;
            npc = null;
        }

        // Weather
        double weatherRoll = rng.nextDouble();
        if (weatherRoll < 0.33) {
            currentWeather = "rainy";
            System.out.println("=== WEATHER: RAINY ===");
        } else if (weatherRoll < 0.66) {
            currentWeather = "cloudy";
            System.out.println("=== WEATHER: CLOUDY ===");
        } else {
            currentWeather = "sunny";
            System.out.println("=== WEATHER: SUNNY ===");
        }

        boolean rainy = false;
        boolean cloudy = false;

        double roll = rng.nextDouble();
        if (roll < 0.33) rainy = true;
        else if (roll < 0.66) cloudy = true;

        // Crop growing
        for (int x = 0; x < LEVEL_WIDTH; x++) {
            for (int y = 0; y < LEVEL_HEIGHT; y++) {

                TileType t = level[x][y].getType();

                if (t == TileType.SOWED_DIRT) {
                    boolean grow = false;

                    if (rainy) grow = true;
                    else if (cloudy && rng.nextDouble() < 0.50) grow = true;
                    else if (!cloudy && rng.nextDouble() < 0.70) grow = true;

                    if (grow) {
                        level[x][y] = new Tile(TileType.CROP);
                        created = true;
                    } else {
                        level[x][y] = new Tile(TileType.TILLED_DIRT);
                    }
                }

                if (t == TileType.TILLED_DIRT && rng.nextDouble() < 0.10) {
                    level[x][y] = new Tile(TileType.DIRT);
                }
            }
        }

        if (created) createPest();
    }

    /**
     * Adds a pest to the current level. This method should create a new Pest object
     * and assign it to the pest attribute of this class. For top marks you should
     * make the position of the Pest dynamic i.e. it is randomly selected in some way.
     */
    private void createPest() {
        int px, py;
        do {
            px = rng.nextInt(LEVEL_WIDTH);
            py = rng.nextInt(LEVEL_HEIGHT);
        } while (level[px][py].getType() == TileType.WALL);

        pest = new Pest(px, py);
    }

    /**
     * Moves the pest object within the level. This method should change the pest objects
     * X and Y positions so that it moves towards the nearest crop in the level. You will
     * need to write code to find the nearest crop to the pest. You should then change the
     * pests position so that it moves closer to the nearest crop. The pest should not
     * be blocked by any tile types nor by Trees or Rocks (though you can add code to
     * create more complex movement rules if you wish!)
     */
    private void movePest() {
        if (pest == null) return;

        int bestX = -1, bestY = -1;
        double bestDist = Double.MAX_VALUE;

        for (int x = 0; x < LEVEL_WIDTH; x++) {
            for (int y = 0; y < LEVEL_HEIGHT; y++) {
                if (level[x][y].getType() == TileType.CROP) {
                    double d = Math.hypot(x - pest.getX(), y - pest.getY());
                    if (d < bestDist) {
                        bestDist = d;
                        bestX = x;
                        bestY = y;
                    }
                }
            }
        }

        if (bestX == -1) return;

        int newX = pest.getX();
        int newY = pest.getY();

        if (bestX > newX) newX++;
        else if (bestX < newX) newX--;

        if (bestY > newY) newY++;
        else if (bestY < newY) newY--;

        if (level[newX][newY].getType() == TileType.CROP) {
            level[newX][newY] = new Tile(TileType.DIRT);
        }

        pest.setPosition(newX, newY);
    }

    /**
     * This method is used in an even later task to improve the movement logic for the player.
     * You should remove the line of code in this method and replace it with your own code.
     * You may like to start by copying and pasting your movement code from the betterMovePlayer()
     * method and then editing it. This method should add logic that handles the player moving into
     * a BED tile by calling the triggerNight() method. You should also rewrite the method to be
     * as efficient and easy to understand as possible, e.g by removing/refining duplicated code that is
     * common to all movement directions (or writing a new method or methods), creating new methods
     * that help streamline this code and any other improvements to readability and conciseness.
     * @param direction the direction of movement based on the arrow key that was pressed:
     * 1 is up, 2 is right, 3 is down and 4 is left.
     */
    public void evenBetterMovePlayer(int direction) {

        if (player == null || sleeping()) return;

        int x = player.getX();
        int y = player.getY();
        int newX = x;
        int newY = y;

        if (direction == 1 && y > 0) newY--;
        if (direction == 2 && x < LEVEL_WIDTH - 1) newX++;
        if (direction == 3 && y < LEVEL_HEIGHT - 1) newY++;
        if (direction == 4 && x > 0) newX--;

        if (level[newX][newY].getType() == TileType.WALL) return;

        //Debris Breaking
        if (debris != null) {
            for (int i = 0; i < debris.length; i++) {
                Entity e = debris[i];
                if (e != null && e.getX() == newX && e.getY() == newY) {

                    // Tree breaking and axe durability
                    if (e instanceof Tree) {
                        if (hasAxe && axeDurability > 0) {
                            debris[i] = null;
                            axeDurability--;
                            System.out.println("Axe used. Durability left: " + axeDurability);
                            if (axeDurability == 0) {
                                hasAxe = false;
                                player.setHeldItem(0);
                                System.out.println("The axe broke!");
                            }
                            player.setPosition(newX, newY);
                        }
                        return;
                    }

                    // Rock breaking also pickaxe durability
                    if (e instanceof Rock) {
                        if (hasPickaxe && pickaxeDurability > 0) {
                            debris[i] = null;
                            pickaxeDurability--;
                            System.out.println("Pickaxe used. Durability left: " + pickaxeDurability);
                            if (pickaxeDurability == 0) {
                                hasPickaxe = false;
                                player.setHeldItem(0);
                                System.out.println("The pickaxe broke!");
                            }
                            player.setPosition(newX, newY);
                        }
                        return;
                    }
                }
            }
        }

        // npc
        if (npc != null && npc.getX() == newX && npc.getY() == newY) {
            System.out.println("=== VILLAGER SPEAKING ===");
            System.out.println("Villager: Hello farmer! I have a gift for you.");
            System.out.println("You accepted the gift!");
            money += 20;
            System.out.println("Got 20 money! Total: £" + money);
            npc = null;
            player.setPosition(newX, newY);
            return;
        }

        // bed and sleep
        if (level[newX][newY].getType() == TileType.BED) {
            triggerNight();
            player.setPosition(newX, newY);
            return;
        }

        // tools
        if (level[newX][newY].getType() == TileType.HOE_BOX) {
            hasHoe = true;
            hoeDurability = 3;
            hasSeeds = hasAxe = hasPickaxe = false;
            player.setHeldItem(1);
            return;
        }

        if (level[newX][newY].getType() == TileType.SEED_BOX) {
            hasSeeds = true;
            hasHoe = hasAxe = hasPickaxe = false;
            player.setHeldItem(2);
            return;
        }

        if (level[newX][newY].getType() == TileType.AXE_BOX) {
            hasAxe = true;
            axeDurability = 3;
            hasHoe = hasSeeds = hasPickaxe = false;
            player.setHeldItem(3);
            return;
        }

        if (level[newX][newY].getType() == TileType.PICKAXE_BOX) {
            hasPickaxe = true;
            pickaxeDurability = 3;
            hasHoe = hasSeeds = hasAxe = false;
            player.setHeldItem(4);
            return;
        }

        //  Watering Can part
        if (level[newX][newY].getType() == TileType.WATERING_CAN_BOX) {
            hasWateringCan = true;
            System.out.println("Picked up Watering Can!");
            return;
        }

        // dirt
        if (hasHoe && level[newX][newY].getType() == TileType.DIRT) {
            if (hoeDurability > 0) {
                hoeDurability--;
                System.out.println("Hoe used. Durability left: " + hoeDurability);
                level[newX][newY] = new Tile(TileType.TILLED_DIRT);

                if (hoeDurability == 0) {
                    hasHoe = false;
                    player.setHeldItem(0);
                    System.out.println("The hoe broke!");
                }

                player.setPosition(newX, newY);
            }
            return;
        }

        //planting the seeds
        if (hasSeeds && level[newX][newY].getType() == TileType.TILLED_DIRT) {
            level[newX][newY] = new Tile(TileType.SOWED_DIRT);
            player.setPosition(newX, newY);
            return;
        }

        // the watering can
        if (hasWateringCan && level[newX][newY].getType() == TileType.SOWED_DIRT) {
            level[newX][newY] = new Tile(TileType.WATERED_DIRT);
            System.out.println("Watered crop!");
            player.setPosition(newX, newY);
            return;
        }

        //the sign post for task 18
        if (level[newX][newY].getType() == TileType.SIGNPOST) {
            System.out.println("=== SIGNPOST ===");
            System.out.println("Welcome to Sunmist Plateau!");
            System.out.println("Farm. Grow. Survive.");
            return;
        }

        // shop for task 19
        if (level[newX][newY].getType() == TileType.SHOP_COUNTER) {
            System.out.println("=== SHOP MENU ===");
            System.out.println("1. Hoe (£30)");
            System.out.println("2. Axe (£50)");
            System.out.println("3. Pickaxe (£60)");
            System.out.println("4. Seeds (£10)");

            if (money >= 10) {
                money -= 10;
                hasSeeds = true;
                System.out.println("Bought Seeds! Money left: £" + money);
            } else {
                System.out.println("Not enough money!");
            }
            return;
        }

        //harvesting the cropp
        if (level[newX][newY].getType() == TileType.CROP) {
            level[newX][newY] = new Tile(TileType.DIRT);
            money += 10;
            System.out.println("Money: £" + money);
            player.setPosition(newX, newY);
            return;
        }

        //killing the pest
        if (pest != null && pest.getX() == newX && pest.getY() == newY) {
            pest = null;
            player.setPosition(newX, newY);
            return;
        }

        // movement set position
        player.setPosition(newX, newY);
    }

    /**
     * A method to trigger the night animation for the game when the player enters
     * a BED tile. You can call this method to start the night animation and the growth
     * of crops.
     */
    private void triggerNight() {
        gui.doNight(this);
    }

    /**
     * A method that is used to prevent player movement during the night animation.
     * @return true if the night animation is still playing, false otherwise.
     */
    public boolean sleeping() {
        return gui.animatingNight();
    }

    /**
     * Performs a single turn of the game when the user presses a key on the
     * keyboard. The method increments the turnNumber, moves the pest object
     * every four turns, and requests the GUI to redraw the game level by passing
     * it the level, debris, player and pest objects/arrays.
     * */
    public void doTurn() {
        turnNumber++;

        if (turnNumber % 4 == 0 && pest != null)
            movePest();

        if (turnNumber % 3 == 0 && npc != null)
            moveNPC();

        gui.updateDisplay(level, debris, player, pest);
    }

    private void moveNPC() {
        if (npc == null) return;

        int newX = npc.getX();
        int newY = npc.getY();
        int dir = rng.nextInt(4);

        if (dir == 0 && newY > 0) newY--;
        else if (dir == 1 && newX < LEVEL_WIDTH - 1) newX++;
        else if (dir == 2 && newY < LEVEL_HEIGHT - 1) newY++;
        else if (dir == 3 && newX > 0) newX--;

        if (level[newX][newY].getType() == TileType.STONE_GROUND)
            npc.setPosition(newX, newY);
    }

    /**
     * Starts a game. This method generates a room, adds the dropper and player,
     * then requests the GUI to update the level on screen using the information
     * passed to it.
     */
    public void startGame() {
        generateEvenBetterFarm();
        createPlayer();
        gui.updateDisplay(level, debris, player, pest);
    }
}