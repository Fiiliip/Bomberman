import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;

/**
 * Class that represents a bitmap image that can be rendered on the canvas.
 * 
 * @author Miroslav Kvassay
 * @author Michal Varga
 * 
 * @author Fiiliip (https://github.com/Fiiliip) - translated to English with small code changes
 * 
 * @version 1.2
 */
public class Image {
    
    private boolean isVisible;

    private int topLeftX;
    private int topLeftY;
    private int angle;

    private BufferedImage image;

    /**
     * Creates Image at X, Y and angle with value 0.
     * 
     * @param fileWithImage path to the image file to render
     */
    public Image(String fileWithImage) {
        this.image = this.loadPictureFromFile(fileWithImage);

        this.isVisible = false;
        this.topLeftX = 0;
        this.topLeftY = 0;
        this.angle = 0;
    }

    /**
     * Returns the position of the top left X of image.
     * @return top left X of the image
     */
    public int getTopLeftX() {
        return this.topLeftX;
    }

    /**
     * Returns the position of the top left Y of image.
     * @return top left Y of the image
     */
    public int getTopLeftY() {
        return this.topLeftY;
    }

    /**
	 * Make this image visible. If it was already visible, do nothing.
	 */
    public void makeVisible() {
        this.isVisible = true;
        this.draw();
    }

    /**
	 * Make this image invisible. If it was already invisible, do nothing.
	 */
    public void makeInvisible() {
        this.erase();
        this.isVisible = false;
    }

    /**
     * Move the image horizontally by 'distance' pixels.
     */
    public void moveHorizontal(int distance) {
        this.erase();
        this.topLeftX += distance;
        this.draw();
    }

    /**
     * Move the image horizontally by 'distance' pixels.
     */
    public void moveVertical(int distance) {
        this.erase();
        this.topLeftY += distance;
        this.draw();
    }

    /**
     * Sets the new top left X and top left Y position of the image
     * 
     * @param newX the X coordinate at which we want to display the image
     * @param newY the Y coordinate at which we want to display the image
     */
    public void setPosition(int newX, int newY) {
        int x = this.topLeftX - newX;
        int y = this.topLeftY - newY;
        
        this.moveHorizontal(-(x)); // Move horizontally.
        this.moveVertical(-(y)); // Move vertically.
    }

    /**
     * (Image) Changes the image.
     * Image file must exist.
     * 
     * @param fileWithImage path to file
     */
    public void changeImage(String fileWithImage) {
        boolean drawn = this.isVisible;
        this.erase();        
        this.image = this.loadPictureFromFile(fileWithImage);
        if (drawn) {
            this.draw();
        }
    }
    
    /**
     * (Image) Change the rotation angle of the image according to the parameter. North = 0.
     */
    public void changeAngle(int angle) {
        boolean nakresleny = this.isVisible;
        this.erase();
        this.angle = angle;
        if (nakresleny) {
            this.draw();
        }
    }  

    /**
     * Loads an image from a file.
     * 
     * @param file path to file
     */
    private BufferedImage loadPictureFromFile(String file) {
        BufferedImage loadedImage = null;

        try {
            loadedImage = ImageIO.read(new File(file));
        } catch (IOException e) {
            javax.swing.JOptionPane.showMessageDialog(null, "File " + file + " was not found!");
        }

        return loadedImage;
    }

    /*
     * (Image) Returns the width of the image.
     */
    private int width() {
        return this.image.getWidth();
    }

    /*
     * (Image) Returns the height of the image.
     */
    private int height() {
        return this.image.getHeight();
    }

    /*
     * Draw the image with current specifications on screen.
     */
    private void draw() {
        if (this.isVisible) {
            Canvas canvas = Canvas.getCanvas();

            AffineTransform at = new AffineTransform();
            at.translate(this.topLeftX + this.width() / 2, this.topLeftY + this.height() / 2);
            at.rotate(this.angle / 180.0 * Math.PI);
            at.translate(-this.width() / 2, -this.height() / 2);
        
            canvas.draw(this, this.image, at);
        }
    }

    /*
     * Erase the image on screen.
     */
    private void erase() {
        if (this.isVisible) {
            Canvas canvas = Canvas.getCanvas();
            canvas.erase(this);
        }
    }
}
