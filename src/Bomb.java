
/**
* Class that displays a bomb on a map, explodes and destroys objects in it's radius.
*
* @author Fiiliip (https://github.com/Fiiliip)
*/
public class Bomb {
    
    private static final int TIME_TO_EXPLOSION = 10; // Čas, po ktorom má bomba vybuchnúť od jej vytvorenia. Hodnota je v tikoch, kde 1 tik = 0,25 sekundy. Aktuálný čas do výbuchu je 2,5 sekundy.

    private static final String EXPLOSION_BIG_END = "../textures/Explosion/big_end.png";
    private static final String EXPLOSION_BIG_MIDDLE = "../textures/Explosion/big_middle.png";
    private static final String EXPLOSION_BIG_BEGINNING = "../textures/Explosion/big_beginning.png";

    private Image model;

    private Player owner;

    private Bomberman bomberman;
    private Manager manager;

    private int tickCount;
    private int explosionRadius;

    private int row;
    private int column;

    /**
     * Creates a Bomb.
     * @param owner the player who planted the bomb
     * @param bomberman object that manages the whole game
     * @param explosionRadius radius of the bomb in blocks
     * @param row position Y / row the of bomb in the field
     * @param column position X / column of the bomb in the field
     */
    public Bomb(Player owner, Bomberman bomberman, int explosionRadius, int row, int column) {
        this.model = new Image("../textures/bomb.png");
        this.model.setPosition(bomberman.getMap().getBlockMap()[row][column].getTopLeftX(), bomberman.getMap().getBlockMap()[row][column].getTopLeftY());

        this.owner = owner;

        this.bomberman = bomberman;
        this.manager = new Manager();
        this.manager.manageObject(this);

        this.tickCount = 0;
        this.explosionRadius = explosionRadius;

        this.row = row;
        this.column = column;
        
        this.model.makeVisible();
    }

    /** Tick method called by class Manager every 0.25 seconds */
    public void tick() {
        this.tickCount++;
        if (this.tickCount >= TIME_TO_EXPLOSION) {
            this.explode();
        }
    }

    /**
     * Returns the position Y / row in the field.
     * @return row in the field
     */
    public int getRow() {
        return this.row;
    }

    /**
     * Returns the position X / column in the field.
     * @return column in the field
     */
    public int getColumn() {
        return this.column;
    }

    /** A method that checks blocks in all directions in the radius of a bomb explosion and destroys them. Then it displays an explosion. If there are players there, it will take their lives */
    public void explode() {
        // TODO: Refactor this method, so it will be shorter.

        this.destroy(); // The bomb must be destroyed at the beginning of this method, because otherwise the logic for exploding a bomb would not work by exploding another bomb..

        Block[][] blockMap = this.bomberman.getMap().getBlockMap();
        Bomb[][] bombMap = this.bomberman.getMap().getBombMap();

        // Check the blocks up in the radius of the bomb explosion.
        for (int distanceFromBomb = 0, r = this.row; distanceFromBomb < this.explosionRadius && r >= 0; distanceFromBomb++, r--) {
            if (r != this.row) {
                this.bomberman.takePlayersLifeAt(r, this.column);

                if (bombMap[r][this.column] != null) {
                    bombMap[r][this.column].explode();
                }
            }

            if (distanceFromBomb == this.explosionRadius - 1 && blockMap[r][this.column].getType() != Block.Type.UNBREAKABLE_WALL) {
                this.showExplosion(EXPLOSION_BIG_END, r, this.column, -90, false);
            }

            if (blockMap[r][this.column].getType() == Block.Type.UNBREAKABLE_WALL) {
                this.showExplosion(EXPLOSION_BIG_END, r + 1, this.column, -90, false);
                break;
            } else if (blockMap[r][this.column].getType() == Block.Type.BREAKABLE_WALL) {
                blockMap[r][this.column].setType(Block.Type.EMPTY);
                this.bomberman.generatePowerUpAt(r, this.column);
                this.showExplosion(EXPLOSION_BIG_END, r, this.column, -90, false);
                break;
            } else {
                this.showExplosion(EXPLOSION_BIG_MIDDLE, r, this.column, -90, true);
            }
        }

        
        // Check the blocks down in the radius of the bomb explosion.
        for (int distanceFromBomb = 0, r = this.row; distanceFromBomb < this.explosionRadius && r <= blockMap.length; distanceFromBomb++, r++) {
            if (r != this.row) {
                this.bomberman.takePlayersLifeAt(r, this.column);

                if (bombMap[r][this.column] != null) {
                    bombMap[r][this.column].explode();
                }
            }         

            if (distanceFromBomb == this.explosionRadius - 1 && blockMap[r][this.column].getType() != Block.Type.UNBREAKABLE_WALL) {
                this.showExplosion(EXPLOSION_BIG_END, r, this.column, 90, false);
            }
            
            if (blockMap[r][this.column].getType() == Block.Type.UNBREAKABLE_WALL) {
                this.showExplosion(EXPLOSION_BIG_END, r - 1, this.column, 90, false);
                break;
            } else if (blockMap[r][this.column].getType() == Block.Type.BREAKABLE_WALL) {
                blockMap[r][this.column].setType(Block.Type.EMPTY);
                this.bomberman.generatePowerUpAt(r, this.column);
                this.showExplosion(EXPLOSION_BIG_END, r, this.column, 90, false);
                break;
            } else {
                this.showExplosion(EXPLOSION_BIG_MIDDLE, r, this.column, 90, true);
            }
        }

        // Check the blocks left in the radius of the bomb explosion.
        for (int distanceFromBomb = 0, s = this.column; distanceFromBomb < this.explosionRadius && s >= 0; distanceFromBomb++, s--) {
            if (s != this.column) {
                this.bomberman.takePlayersLifeAt(this.row, s);

                if (bombMap[this.row][s] != null) {
                    bombMap[this.row][s].explode();
                }
            }            

            if (distanceFromBomb == this.explosionRadius - 1 && blockMap[this.row][s].getType() != Block.Type.UNBREAKABLE_WALL) {
                this.showExplosion(EXPLOSION_BIG_END, this.row, s, -180, false);
            }
            
            if (blockMap[this.row][s].getType() == Block.Type.UNBREAKABLE_WALL) {
                this.showExplosion(EXPLOSION_BIG_END, this.row, s + 1, -180, false);
                break;
            } else if (blockMap[this.row][s].getType() == Block.Type.BREAKABLE_WALL) {
                blockMap[this.row][s].setType(Block.Type.EMPTY);
                this.bomberman.generatePowerUpAt(this.row, s);
                this.showExplosion(EXPLOSION_BIG_END, this.row, s, -180, false);
                break;
            } else {
                this.showExplosion(EXPLOSION_BIG_MIDDLE, this.row, s, -180, true);
            }
        }

        // Check the blocks right in the radius of the bomb explosion.
        for (int distanceFromBomb = 0, s = this.column; distanceFromBomb < this.explosionRadius && s <= blockMap[this.row].length; distanceFromBomb++, s++) {
            if (s != this.column) {
                this.bomberman.takePlayersLifeAt(this.row, s);

                if (bombMap[this.row][s] != null) {
                    bombMap[this.row][s].explode();
                }
            }     

            if (distanceFromBomb == this.explosionRadius - 1 && blockMap[this.row][s].getType() != Block.Type.UNBREAKABLE_WALL) {
                this.showExplosion(EXPLOSION_BIG_END, this.row, s, 0, false);
            }
            
            if (blockMap[this.row][s].getType() == Block.Type.UNBREAKABLE_WALL) {
                this.showExplosion(EXPLOSION_BIG_END, this.row, s - 1, 0, false);
                break;
            } else if (blockMap[this.row][s].getType() == Block.Type.BREAKABLE_WALL) {
                blockMap[this.row][s].setType(Block.Type.EMPTY);
                this.bomberman.generatePowerUpAt(this.row, s);
                this.showExplosion(EXPLOSION_BIG_END, this.row, s, 0, false);
                break;
            } else {
                this.showExplosion(EXPLOSION_BIG_MIDDLE, this.row, s, 0, true);
            }
        }

        this.bomberman.takePlayersLifeAt(this.row, this.column);
        this.showExplosion(EXPLOSION_BIG_BEGINNING, this.row, this.column, -90, false);
        
        this.owner.addBomb();
    }

    /**
     * Displays a specific explosion texture at a specific angle on a specified row and column.
     * @param pathToExplosionFile The path to the explosion file.
     * @param row Position Y (row) in the field.
     * @param column Position X (column) in the field.
     * @param angle Texture angle.
     * @param checkArrayOccupancy True / False, if check the explosion on the specified row and column.
     */
    public void showExplosion(String pathToExplosionFile, int row, int column, int angle, boolean checkArrayOccupancy) {
        Explosion[][] mapaExplozii = this.bomberman.getMap().getExplosionMap();
        // I check the occupancy of the field to prioritize explosion textures such as (EXPLOSION_BIG_BEGINNING and EXPLOSION_BIG_END), where this value is always false.
        // If I didn't check it, then the textures would show in the wrong places or would be overwritten. So e.g. EXPLOSION_BIG_MIDDLE would be made where EXPLOSION_BIG_END should be.
        if (checkArrayOccupancy) {
            // This condition is also used to cover newly created explosions from other bombs "old".
            // QUESTION: Is this condition necessary? I don't think so, but it works, so I'd rather not catch it. But I think this method could be made more beautiful.
            if (mapaExplozii[row][column] == null || (mapaExplozii[row][column] != null && mapaExplozii[row][column].getTickCount() > 0)) {                
                Image image = new Image(pathToExplosionFile);
                image.changeAngle(angle);
                mapaExplozii[row][column] = new Explosion(this.bomberman, image, row, column);
            }
        } else {
            Image image = new Image(pathToExplosionFile);
            image.changeAngle(angle);
            mapaExplozii[row][column] = new Explosion(this.bomberman, image, row, column);
        }
    }

    /** Removes all references to the object. */
    public void destroy() {
        Bomb[][] bombMap = this.bomberman.getMap().getBombMap();
        bombMap[this.row][this.column] = null;
        this.manager.stopManagingObject(this);
        this.model.makeInvisible();
    }
}
