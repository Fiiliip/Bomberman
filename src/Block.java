
/**
* A class that displays three types of blocks on the map as empty or (non) destructible.
*
* @author Fiiliip (https://github.com/Fiiliip)
*/
public class Block {
    
    private Type type;
    private Image model;

    /**
    * Enumerator that stores information about the names of individual block textures.
    */
    public enum Type {
        EMPTY("grass"),
        BREAKABLE_WALL("box"),
        UNBREAKABLE_WALL("brick_wall");

        private String blockName;

        /**
         * Create block type.
         * @param name texture name
         */
        Type(String name) {
            this.blockName = name;
        }
  
        /**
        * A method that returns the name of a block texture.
        * @return Block texture name.
        */
        public String getBlockName() {
            return this.blockName;
        }
    }

    /**
     * Creates a Block.
     * @param type block type
     * @param topLeftX top left X
     * @param topLeftY top left Y
     */
    public Block(Type type, int topLeftX, int topLeftY) {
        this.type = type;

        this.model = new Image("../textures/Blocks/" + type.getBlockName() + ".png");
        this.model.setPosition(topLeftX, topLeftY);
        this.model.makeVisible();
    }

    /**
     * Returns Enumerator Type.
     * @return Block type.
     */
    public Type getType() {
        return this.type;
    }

    /**
    * Sets the new Block Type.
    * @param newType Block type.
    */
    public void setType(Type newType) {
        this.type = newType;

        this.model.changeImage("../textures/Blocks/" + this.type.getBlockName() + ".png");
        this.model.makeVisible();
    }
    
    /**
     * Returns the Picture object.
     * @return model (image / texture) block.
     */
    public Image getModel() {
        return this.model;
    }
    
    /**
     * Returns the position coordinate of the upper left X object.
     * @return top left X
     */
    public int getTopLeftX() {
        return this.model.getTopLeftX();
    }
    
    /**
     * Returns the position coordinate of the upper left Y object.
     * @return top left Y
     */
    public int getTopLeftY() {
        return this.model.getTopLeftY();
    }
}
