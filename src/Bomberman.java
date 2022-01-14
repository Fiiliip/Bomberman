import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;
/**
* A class that manages the basic elements of the game as it is created.
*
* @author Fiiliip (https://github.com/Fiiliip)
*/
public class Bomberman {

    private static final float CHANCE_FOR_GENERATING_POWER_UP = 0.5f; // The larger the number, the greater the chance. Percentage principle, so 0.5f = 50%.

    private Manager manager;

    private Map map;
    private ArrayList<Player> players;
    private ArrayList<PlayerController> playersController;

    /** Creates a Bomberman. */
    public Bomberman() {
        this.manager = new Manager();
        this.manager.manageObject(this);

        this.map = new Map("../maps/map.csv");
        this.players = new ArrayList<Player>();
        this.playersController = new ArrayList<PlayerController>();
        this.createPlayer("red", 1, 1, 60, 0, KeyEvent.VK_W, KeyEvent.VK_S, KeyEvent.VK_A, KeyEvent.VK_D, KeyEvent.VK_G);
        this.createPlayer("black", this.map.getBlockMap().length - 2, this.map.getBlockMap()[this.map.getBlockMap().length - 2].length - 2, 450, 0, KeyEvent.VK_UP, KeyEvent.VK_DOWN, KeyEvent.VK_LEFT, KeyEvent.VK_RIGHT, KeyEvent.VK_SPACE);
    }

    /**
     * Returns the map.
     * @return map object
     */
    public Map getMap() {
        return this.map;
    }

    /**
     * Returns players.
     * @return ArrayList with players.
     */
    public ArrayList<Player> getPlayers() {
        return this.players;
    }

    /**
     * Returns players controls.
     * @return ArrayList with player controls
     */
    public ArrayList<PlayerController> getPlayersController() {
        return this.playersController;
    }

    /** Exits game. */
    public void exit() {
        System.exit(0);
    }

    /**
     * Creates a new player.
     * @param color player color
     * @param row position of the row on which the player is to be created
     * @param column position of the column on which the player is to be created
     * @param topLeftXHUD position of the upper left X on which the HUD is to be created
     * @param topLeftYHUD the position of the upper left Y on which the HUD is to be created
     * @param up key code to move up
     * @param down key code to move down
     * @param left key code to move left
     * @param right key code to move right
     * @param activate the key code to activate the action
     */
    public void createPlayer(String color, int row, int column, int topLeftXHUD, int topLeftYHUD, int up, int down, int left, int right, int activate) {
        Player player = new Player(this, color, row, column, topLeftXHUD, topLeftYHUD);

        this.players.add(player);
        this.playersController.add(new PlayerController(player, up, down, left, right, activate));
    }

    /** Checks the number of players in the game. If there is already the last player, it will be displayed as the winner. */
    public void checkPlayerCount() {
        if (this.players.size() == 1) {
            this.showWinner(this.players.get(0));
        }
    }

    /**
     * Takes the lives of all players at given coordinates.
     * @param row coordinates of the row in the field
     * @param column coordinates of the column in the field
     */
    public void takePlayersLifeAt(int row, int column) {
        for (Player player : this.getPlayers()) {
            if (player.getRow() == row && player.getColumn() == column) {
                player.takeLife();
            }
        }
    }

    /**
     * Generates power up at given coordinates.
     * @param row coordinates of the row in the field
     * @param column coordinates of the column in the field
     */
    public void generatePowerUpAt(int row, int column) {
        Random rngChance = new Random();
        if (rngChance.nextFloat() <= CHANCE_FOR_GENERATING_POWER_UP) {
            PowerUp[][] powerUpMap = this.getMap().getPowerUpMap();
            powerUpMap[row][column] = new PowerUp(this, row, column);
        }
    }

    /**
     * Displays the specified player as the winner.
     * @param Player player object
     */
    public void showWinner(Player player) {
        Image winner = new Image("../textures/Characters/" + player.getColor() + "_" + "down.png");
        winner.setPosition(330, 0);
        winner.makeVisible();

        Image koruna = new Image("../textures/crown.png");
        koruna.setPosition(330, 0);
        koruna.makeVisible();
    }
}
