
/**
 * Class that displays information about a player.
 * 
 * @author Fiiliip (https://github.com/Fiiliip)
 */
public class PlayerHUD {
    
    private Player player;

    private Image imgPlayer;
    private Image imgHeart;
    private Image imgBomb;

    private DigitalDisplay pocetZivotov;
    private DigitalDisplay pocetBomb;

    /**
     * Creates HUD that displays information about player.
     * @param player object
     * @param topLeftX coordinates of the top left X
     * @param topLeftY coordinates of the top left Y
     */
    public PlayerHUD(Player player, int topLeftX, int topLeftY) {
        this.player = player;

        this.imgPlayer = new Image("../textures/Characters/" + this.player.getColor() + "_down.png");
        this.imgPlayer.setPosition(topLeftX, topLeftY);
        this.imgPlayer.makeVisible();

        this.imgHeart = new Image("../textures/heart.png");
        this.imgHeart.setPosition(topLeftX + 45, topLeftY + 1);
        this.pocetZivotov = new DigitalDisplay(100, topLeftX + 45 + 30, topLeftY + 5);
        this.pocetZivotov.setValue(this.player.getLivesCount());
        this.imgHeart.makeVisible();

        this.imgBomb = new Image("../textures/bomb.png");
        this.imgBomb.setPosition(topLeftX + 45 + 30 + 45, topLeftY + 2);
        this.pocetBomb = new DigitalDisplay(100, topLeftX + 45 + 30 + 45 + 30, topLeftY + 5);
        this.pocetBomb.setValue(this.player.getBombCount());
        this.imgBomb.makeVisible();
    }

    /** Update player values / information about player. */
    public void updateValues() {
        this.pocetZivotov.setValue(this.player.getLivesCount());
        this.pocetBomb.setValue(this.player.getBombCount());
    }
}
