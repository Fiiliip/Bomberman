import java.util.Random;
/**
 * Class that displays a random power up on a map.
 * 
 * @author Fiiliip (https://github.com/Fiiliip)
 */
public class PowerUp {
    
    private Image model;
    private Type type;

    private Bomberman bomberman;

    private int row;
    private int column;

    /**
     * An enumerator that stores information about texture name and methods for activating them.
     */
    public enum Type {
        INCREASE_EXPLOSION_RADIUS("increase_explosion_radius") {
            public void activate(Player player) {
                player.increaseBombRadius();
            }
        },
        ADD_BOMB("add_bomb") {
            public void activate(Player player) {
                player.addBomb();
            }
        };

        private String name;

        /**
         * Creates power up type.
         * @param powerUpName power up name
         */
        Type(String powerUpName) {
            this.name = powerUpName;
        }

        /**
         * Get type name.
         * @return power up name
         */
        public String getName() {
            return this.name;
        }

        /** A method that activates power up. */
        public abstract void activate(Player player);
    }

    /**
     * Creates a power up.
     * @param bomberman object that manages the whole game
     * @param row position Y / row of power up in array
     * @param column position X / column of power up in array
     */
    public PowerUp(Bomberman bomberman, int row, int column) {
        this.bomberman = bomberman;

        this.row = row;
        this.column = column;

        this.chooseRandom();
    }

    /**
     * Returns the power up type.
     * @return power up type
     */
    public Type getType() {
        return this.type;
    }

    /** Selects a random power up from all possible power ups and displays it at the current position. */
    public void chooseRandom() {
        Random rngPowerUp = new Random();
        this.type = Type.values()[rngPowerUp.nextInt(Type.values().length)];
        this.model = new Image("../textures/PowerUps/" + this.type.getName() + ".png");
        this.model.setPosition(this.bomberman.getMap().getBlockMap()[this.row][this.column].getTopLeftX(), this.bomberman.getMap().getBlockMap()[this.row][this.column].getTopLeftY());
        this.model.makeVisible();
    }

    /** Removes all references to the object. */
    public void destroy() {
        PowerUp[][] powerUpMap = this.bomberman.getMap().getPowerUpMap();
        powerUpMap[this.row][this.column] = null;
        this.model.makeInvisible();
    }
}
