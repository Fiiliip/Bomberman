import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Class that manages player control.
 * 
 * @author Fiiliip (https://github.com/Fiiliip)
 */
public class PlayerController implements KeyListener {
    
    private Player player;

    private int up;
    private int down;
    private int left;
    private int right;
    private int activate;

    /**
     * Creates player control.
     * @param player player object
     * @param up key code to move up
     * @param down key code to move down
     * @param left key code to move left
     * @param right key code to move right
     * @param activate key code to activate the action
     */
    public PlayerController(Player player, int up, int down, int left, int right, int activate) {
        this.player = player;

        this.up = up;
        this.down = down;
        this.left = left;
        this.right = right;
        this.activate = activate;

        Canvas.getCanvas().addKeyListener(this);
    }

    /** Remove controller */
    public void removeController() {
        Canvas.getCanvas().removeKyeListener(this);
    }

    /**
     * Returns the player using the current control setting.
     * @return player object
     */
    public Player getPlayer() {
        return this.player;
    }

    /** The method needed to use the KeyListener. Registers every key pressed and then released. */
    public void keyTyped(KeyEvent event) {
        // I'm an empty method :'(
    }

    /** The method needed to use the KeyListener. Registers every pressed key. */
    public void keyPressed(KeyEvent event) {
        if (event.getKeyCode() == this.up) {
            this.player.goUp();
        } else if (event.getKeyCode() == this.down) {
            this.player.goDown();
        } else if (event.getKeyCode() == this.left) {
            this.player.goLeft();
        } else if (event.getKeyCode() == this.right) {
            this.player.goRight();
        } else if (event.getKeyCode() == this.activate) {
            this.player.placeBomb();
        }
    }

    /** The method needed to use the KeyListener. Registers every key released. */
    public void keyReleased(KeyEvent event) {
        // I'm an empty method :'(
    }
}
