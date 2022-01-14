/**
 * A rectangle that can be moved and drawn on the canvas.
 * 
 * @author  Michael Kolling and David J. Barnes
 * @version 1.0  (15 July 2000)
 */
public class Rectangle {

    private int sideA;
    private int sideB;
    private int topLeftX;
    private int topLeftY;
    private String color;
    private boolean isVisible;

    public Rectangle() {
        this.sideA = 30;
        this.sideB = 60;
        this.topLeftX = 60;
        this.topLeftY = 50;
        this.color = "red";
        this.isVisible = false;
    }

    /**
	 * Make this rectangle visible. If it was already visible, do nothing.
	 */
	public void makeVisible() {
		this.isVisible = true;
		this.draw();
	}
	
	/**
	 * Make this rectangle invisible. If it was already invisible, do nothing.
	 */
	public void makeInvisible() {
		this.erase();
		this.isVisible = false;
	}

    /**
     * Move the rectangle horizontally by 'distance' pixels.
     */
    public void moveHorizontal(int distance) {
		this.erase();
		this.topLeftX += distance;
		this.draw();
    }

    /**
     * Move the rectangle vertically by 'distance' pixels.
     */
    public void moveVertical(int distance) {
		this.erase();
		this.topLeftY += distance;
		this.draw();
    }

    /**
     * Change the sides to the new sides (in pixels). Side must be >= 0.
     */
    public void changeSize(int newSideA, int newSideB) {
		this.erase();
		this.sideA = newSideA;
        this.sideB = newSideB;
		this.draw();
    }

    /**
     * Change the color. Valid colors are "red", "yellow", "blue", "green",
	 * "magenta" and "black".
     */
    public void changeColor(String newColor) {
		this.color = newColor;
		this.draw();
    }

	/*
	 * Draw the rectangle with current specifications on screen.
	 */
	private void draw() {
		if(this.isVisible) {
			Canvas canvas = Canvas.getCanvas();
			canvas.draw(this, this.color, new java.awt.Rectangle(this.topLeftX, this.topLeftY, this.sideA, this.sideB));
		}
	}

	/*
	 * Erase the rectangle on screen.
	 */
	private void erase() {
		if(this.isVisible) {
			Canvas canvas = Canvas.getCanvas();
			canvas.erase(this);
		}
	}
}
