
/**
 * A class that displays a player on a map.
 * 
 * @author Fiiliip (https://github.com/Fiiliip)
 */
public class Player {
    
    private static final int IMMUNITY_TIME = 10;
    
    private Image model;
    private String color;
    
    private Bomberman bomberman;
    private Manager manager;

    private int livesCount;
    private int tickCount;
    private boolean isImmune;

    private int bombCount;
    private int bombRadius;

    private int row;
    private int column;

    private PlayerHUD hud;

    /** Enumerator, that stores textures name for specific direction of view. */
    public enum ViewDirection {
        UP("up"),
        DOWN("down"),
        LEFT("left"),
        RIGHT("right");

        private String direction;

        /**
         * Create view direction.
         * @param direction view direction texture name
         */
        ViewDirection(String direction) {
            this.direction = direction;
        }

        /**
         * Method that returns the direction of the view.
         * @return string containing the direction of the view
         */
        public String getDirection() {
            return this.direction;
        }
    }

    /**
     * Creates a player.
     * @param bomberman object that manages the whole game
     * @param color player color
     * @param row position Y / row of the player in array
     * @param column position X / column of the player in array
     * @param topLeftXHUD position upper left X HUD
     * @param topLeftYHUD position upper left Y HUD
     */
    public Player(Bomberman bomberman, String color, int row, int column, int topLeftXHUD, int topLeftYHUD) {
        this.model = new Image("../textures/Characters/" + color + "_" + ViewDirection.DOWN + ".png");
        this.color = color;

        this.bomberman = bomberman;
        this.manager = new Manager();
        this.manager.manageObject(this);

        this.livesCount = 3;
        this.tickCount = 0;
        this.isImmune = false;

        this.bombCount = 1;
        this.bombRadius = 2;

        this.goTo(row, column);
        this.model.makeVisible();

        this.hud = new PlayerHUD(this, topLeftXHUD, topLeftYHUD);
    }

    /** Tick method called by class Manager every 0.25 seconds */
    public void tick() {
        this.tickCount++;
        if (this.isImmune && this.tickCount <= IMMUNITY_TIME) {
            this.showImmunityEffect();
        } else {
            this.isImmune = false;
        }
    }

    /**
     * Returns text containing the player's current color.
     * @return text containing the player's color
     */
    public String getColor() {
        return this.color;
    }

    /**
    * Returns the integer, the current number of lives.
    * @return integer current number of lives
    */
    public int getLivesCount() {
        return this.livesCount;
    }

    /**
     * Returns the integer of the current number of bombs.
     * @return integer of the current number of bombs
     */
    public int getBombCount() {
        return this.bombCount;
    }

    /**
     * Returns the Y position / the index of the row in the array where the player is located.
     * @return index row in the field
     */
    public int getRow() {
        return this.row;
    }

    /**
     * Returns position X / the index of the column in the field where the player is located.
     * @return column index in the field
     */
    public int getColumn() {
        return this.column;
    }

    /** Adds a bomb to the player. */
    public void addBomb() {
        this.bombCount++;
        this.hud.updateValues();
    }

    /** Increases the bomb radius by 1 block. */
    public void increaseBombRadius() {
        this.bombRadius++;
    }

    /** Takes players life if the player is not immune. If a player has a last life, he disappears from the game. */
    public void takeLife() {
        if (this.isImmune) {
            return;
        }

        this.livesCount--;
        this.hud.updateValues();
        if (this.livesCount <= 0) {
            this.destroy();
            return;
        }

        this.tickCount = 0;
        this.isImmune = true;
    }

    /** Displays the effect of immunity based on the number of ticks. This method must be called several times in quick succession to display the effect. */
    public void showImmunityEffect() {
        if (this.tickCount >= IMMUNITY_TIME) {
            this.model.makeVisible();
            return;
        }

        if (this.tickCount % 2 == 0) {
            this.model.makeVisible();
        } else {
            this.model.makeInvisible();
        }
    }

    /**
     * Displays the player at the specified coordinates. This method also checks to see if the player has entered an explosion or is on the same block as an upgrade.
     * @param row index of the row on which to display the player
     * @param column index of the column on which to display the player
     */
    public void goTo(int row, int column) {
        Block[][] blockMap = this.bomberman.getMap().getBlockMap();
        Bomb[][] bombMap = this.bomberman.getMap().getBombMap();
        Explosion[][] explosionMap = this.bomberman.getMap().getExplosionMap();
        if (blockMap[row][column].getType() != Block.Type.UNBREAKABLE_WALL && blockMap[row][column].getType() != Block.Type.BREAKABLE_WALL && bombMap[row][column] == null) {
            if (explosionMap[row][column] != null) {
                this.takeLife();
            }
            
            this.model.setPosition(blockMap[row][column].getTopLeftX(), blockMap[row][column].getTopLeftY());

            this.row = row;
            this.column = column;

            // If there is an improvement on the given coordinates, the player will take it.
            PowerUp[][] powerUpMap = this.bomberman.getMap().getPowerUpMap();
            if (powerUpMap[this.row][this.column] != null) {
                powerUpMap[this.row][this.column].getType().activate(this);
                powerUpMap[this.row][this.column].destroy();
            }
        }
    }

    /**
     * Adjusts the view direction according to movement.
     * @param directionView is an enumerator value containing the name of the view texture
     */
    public void setViewDirection(ViewDirection viewDirection) {
        this.model.changeImage("../textures/Characters/" + this.color + "_" + viewDirection.getDirection() + ".png");
    }

    /** Places a bomb on the current coordinates of the player, if any. */
    public void placeBomb() {
        if (this.bombCount > 0) {
            this.bomberman.getMap().addBomb(new Bomb(this, this.bomberman, this.bombRadius, this.row, this.column));
            this.bombCount--;

            this.model.makeVisible();

            this.hud.updateValues();
        }
    }

    /** Move player 1 block up. */
    public void goUp() {
        this.goTo(this.row - 1, this.column);
        this.setViewDirection(ViewDirection.UP);
    }

    /** Move player 1 block down. */
    public void goDown() {
        this.goTo(this.row + 1, this.column);
        this.setViewDirection(ViewDirection.DOWN);
    }

    /** Move player 1 block left. */
    public void goLeft() {
        this.goTo(this.row, this.column - 1);
        this.setViewDirection(ViewDirection.LEFT);
    }

    /** Move player 1 block right. */
    public void goRight() {
        this.goTo(this.row, this.column + 1);
        this.setViewDirection(ViewDirection.RIGHT);
    }

    /** Destroys an object by removing all references to that object. */
    public void destroy() {
        this.manager.stopManagingObject(this);
        this.model.makeInvisible();

        for (int hrac = 0; hrac < this.bomberman.getPlayers().size(); hrac++) {
            if (this.bomberman.getPlayers().get(hrac).equals(this)) {
                this.bomberman.getPlayers().remove(this);
            }
        }

        for (int ovladanie = 0; ovladanie < this.bomberman.getPlayersController().size(); ovladanie++) {
            if (this.bomberman.getPlayersController().get(ovladanie).getPlayer().equals(this)) {
                this.bomberman.getPlayersController().get(ovladanie).removeController();
            }
        }

        this.bomberman.checkPlayerCount();
    }
}
