import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JOptionPane;

/**
 * Class that is in charge of loading the map from a semicolon-separated character set (;) and its generation according to the characters being read.
 * Characters representing individual Typees of blocks are:
 * N - indestructible wall,
 * Z - destructible wall,
 * O - empty block
 *
 * @author Fiiliip (https://github.com/Fiiliip)
 */
public class Map {
    
    private static final float CHANCE_FOR_BREAKABLE_WALL = 0.6f; // The larger the number, the greater the chance. Percentage principle, so 0.6f = 60%.
    
    private Block[][] blockMap;
    private Bomb[][] bombMap;
    private Explosion[][] explosionMap;
    private PowerUp[][] powerUpMap;

    public Map(String mapFile) {
        this.generateMap(mapFile);
    }

    
    /**
    * Returns an array with blocks.
    * @return array with blocks
    */
    public Block[][] getBlockMap() {
        return this.blockMap;
    }

    /**
     * Returns an array with bombs.
     * @return array with bombs
     */
    public Bomb[][] getBombMap() {
        return this.bombMap;
    }

    
    /**
    * Returns an array with explosions.
    * @return array with explosions
    */
    public Explosion[][] getExplosionMap() {
        return this.explosionMap;
    }

    /**
    * Returns an array with power ups.
    * @return array with power ups
    */
    public PowerUp[][] getPowerUpMap() {
        return this.powerUpMap;
    }

    /**
     * Adds bomb to bomb array.
     * @param bomb
     */
    public void addBomb(Bomb bomb) {
        this.bombMap[bomb.getRow()][bomb.getColumn()] = bomb;
    }

    /**
     * Adds explosion to explosion array.
     * @param explosion
     */
    public void addExplosion(Explosion explosion) {
        this.explosionMap[explosion.getRow()][explosion.getColumn()] = explosion;
    }

    /**
     * Generates a block map based on the characters read from the file into the ArrayList.
     * @param file path to the .csv file showing the map
     */
    public void generateMap(String file) {
        ArrayList<ArrayList<Character>> mapFromFile = this.loadMapFromFile(file);

        int mapWidth = mapFromFile.get(0).size();
        int mapHeight = mapFromFile.size();

        this.blockMap = new Block[mapHeight][mapWidth];

        for (int row = 0; row < mapHeight; row++) {
            for (int column = 0; column < mapWidth; column++) {
                char znak = mapFromFile.get(row).get(column);
                switch (znak) {
                    case 'N':
                        this.blockMap[row][column] = new Block(Block.Type.UNBREAKABLE_WALL, column * 30, (row * 30) + 30);
                        break;
                    case 'Z':
                        this.blockMap[row][column] = new Block(Block.Type.BREAKABLE_WALL, column * 30, (row * 30) + 30); 
                        break;
                    case 'O':
                        if (this.generateBreakableWall(row, column)) { 
                            this.blockMap[row][column] = new Block(Block.Type.BREAKABLE_WALL, column * 30, (row * 30) + 30); 
                        } else { 
                            this.blockMap[row][column] = new Block(Block.Type.EMPTY, column * 30, (row * 30) + 30); 
                        }
                        break;
                    default:
                        JOptionPane.showMessageDialog(null, "Unknown value |" + znak + "| when generating a map on " + row + 1 + ". row and " + column + 1 + ". column.");
                        this.blockMap[row][column] = new Block(Block.Type.EMPTY, column * 30, (row * 30) + 30);
                        break;
                }
            }
        }

        this.bombMap = new Bomb[mapHeight][mapWidth];
        this.explosionMap = new Explosion[mapHeight][mapWidth];
        this.powerUpMap = new PowerUp[mapHeight][mapWidth];
    }

    /**
     * Checks if it can generate a destructible wall at the specified position. If so, it will generate a random number, and if it is within the specified range, it will return true.
     * @param row row position in the array
     * @param column column position in the array
     * @return
     */
    private boolean generateBreakableWall(int row, int column) {
        // A condition that checks if we are in the upper left corner and +1 to the bottom and right sides. Or in the lower right corner and +1 to the top and left sides.
        if (((row == 1 && (column == 1 || column == 2) || (row == 2 && column == 1)) || (row == this.blockMap.length - 2 && (column == this.blockMap[row].length - 2 || column == this.blockMap[row].length - 3) || (row == this.blockMap.length - 3 && column == this.blockMap[row].length - 2)))) {
            return false;
        }

        Random rngChance = new Random();
        if (rngChance.nextFloat() <= CHANCE_FOR_BREAKABLE_WALL) {
            return true;
        }

        return false;
    }

    /**
     * Loads a file (MUST be of Type .csv) containing semicolon-separated characters (;) and returns an ArrayList of characters.
     * @param file path to .csv file with characters showing the map
     * @return ArrayList with characters from the file
     */
    public ArrayList<ArrayList<Character>> loadMapFromFile(String file) {
        ArrayList<ArrayList<Character>> mapFromFile = new ArrayList<ArrayList<Character>>();

        BufferedReader loadedMap = null;
        try {
            loadedMap = new BufferedReader(new FileReader(file));

            String row;
            while ((row = loadedMap.readLine()) != null) {
                ArrayList<Character> charactersRow = new ArrayList<Character>();
                for (int character = 0; character < row.length(); character++) {
                    if (row.charAt(character) != ';') {
                        charactersRow.add(row.charAt(character));
                    }
                }
                mapFromFile.add(charactersRow);
            }

            loadedMap.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Problem during loading map file. Error: " + e);
        }

        return mapFromFile;
    }
}
