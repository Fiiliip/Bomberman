
/**
 * The class that displays on the map, at the specified block / position, an explosion for a specified time.
 * 
 * @author Fiiliip (https://github.com/Fiiliip)
 */
public class Explosion {

    private static final int EXPLOSION_TIME = 8; // Duration of the explosion display. After this time, the explosion disappears. The current duration is 2 seconds (1 tick = 0.25 sec.).

    private Image model;

    private Bomberman bomberman;
    private Manager manager;

    private int tickCount;

    private int row;
    private int column;

    /**
     * Creates an explosion.
     * @param bomberman object that manages the whole game
     * @param image texture explosion
     * @param row position Y / row of the bomb in the array
     * @param column position X / column of the bomb in the array
     */
    public Explosion(Bomberman bomberman, Image image, int row, int column) {
        this.model = image;
        this.model.setPosition(bomberman.getMap().getBlockMap()[row][column].getTopLeftX(), bomberman.getMap().getBlockMap()[row][column].getTopLeftY());

        this.bomberman = bomberman;
        this.manager = new Manager();
        this.manager.manageObject(this);

        this.row = row;
        this.column = column;

        this.model.makeVisible();
    }

    /** Tick method called by class Manager every 0.25 seconds */
    public void tick() {
        this.tickCount++;
        if (this.tickCount >= EXPLOSION_TIME) {
            this.destroy();
        }
    }

    /**
     * Returns the position Y, so the row in array.
     * @return row in the field
     */
    public int getRow() {
        return this.row;
    }

    /**
     * Returns the X position, so the column in array.
     * @return column in the field
     */
    public int getColumn() {
        return this.column;
    }

    /**
     * Returns the current number of ticks, so the time in ticks that has elapsed since the explosion was created.
     * @return number of ticks that have elapsed since the explosion
     */
    public int getTickCount() {
        return this.tickCount;
    }

    /** Removes all references to the object. */
    public void destroy() {
        Explosion[][] explosionMap = this.bomberman.getMap().getExplosionMap();
        explosionMap[this.row][this.column] = null;
        this.manager.stopManagingObject(this);
        this.model.makeInvisible();
    }
}
