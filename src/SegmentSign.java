/**
 * A class that displays any number using a segment sign on the canvas ranging from 0 to 9, inclusive.
 * 
 * This class was created at school exercises and adapted outside the exercises for use on current semester work!
 * 
 * @author MichalMrena (https://github.com/MichalMrena)
 * @author Fiiliip (https://github.com/Fiiliip)
 */
public class SegmentSign {
    
    /*
     *   __A__
     *  -     -
     *  F     B
     *  -     -
     *   --G--
     *  -     -
     *  E     C
     *  -     -
     *   __D__
     */

    private Rectangle a;
    private Rectangle b;
    private Rectangle c;
    private Rectangle d;
    private Rectangle e;
    private Rectangle f;
    private Rectangle g;

    private int rectangleWidth = 2;
    private int rectangleHeight = 8;

    /**
     * Creates segment sign at given coordinates.
     * @param x coordinate X (top left corner X)
     * @param y coordinate Y (top left corner Y)
     */
    public SegmentSign(int x, int y) {
        this.a = new Rectangle();
        this.b = new Rectangle();
        this.c = new Rectangle();
        this.d = new Rectangle();
        this.e = new Rectangle();
        this.f = new Rectangle();
        this.g = new Rectangle();

        this.a.moveHorizontal(-60 + x);
        this.a.moveVertical(-50 + y);
        this.b.moveHorizontal(-60 + x);
        this.b.moveVertical(-50 + y);
        this.c.moveHorizontal(-60 + x);
        this.c.moveVertical(-50 + y);
        this.d.moveHorizontal(-60 + x);
        this.d.moveVertical(-50 + y);
        this.e.moveHorizontal(-60 + x);
        this.e.moveVertical(-50 + y);
        this.f.moveHorizontal(-60 + x);
        this.f.moveVertical(-50 + y);
        this.g.moveHorizontal(-60 + x);
        this.g.moveVertical(-50 + y);

        this.a.changeSize(this.rectangleHeight, this.rectangleWidth);
        this.g.changeSize(this.rectangleHeight, this.rectangleWidth);
        this.d.changeSize(this.rectangleHeight, this.rectangleWidth);
        
        this.b.changeSize(this.rectangleWidth, this.rectangleHeight);
        this.c.changeSize(this.rectangleWidth, this.rectangleHeight);
        this.e.changeSize(this.rectangleWidth, this.rectangleHeight);
        this.f.changeSize(this.rectangleWidth, this.rectangleHeight);

        this.a.moveHorizontal(this.rectangleWidth);
        this.f.moveVertical(this.rectangleWidth);
        this.b.moveVertical(this.rectangleWidth);
        this.b.moveHorizontal(this.rectangleWidth + this.rectangleHeight);
        this.g.moveHorizontal(this.rectangleWidth);
        this.g.moveVertical(this.rectangleWidth + this.rectangleHeight);
        this.c.moveVertical(2 * this.rectangleWidth + this.rectangleHeight);
        this.c.moveHorizontal(this.rectangleWidth + this.rectangleHeight);
        this.e.moveVertical(2 * this.rectangleWidth + this.rectangleHeight);
        this.d.moveHorizontal(this.rectangleWidth);
        this.d.moveVertical(2 * this.rectangleWidth + 2 * this.rectangleHeight);
    }

    /**
     * Displays given digit from 0 to 9, inclusive.
     * @param digit - integer value from 0 to 9, inclusive
     */
    public void displayDigit(int digit) {
        this.a.makeInvisible();
        this.b.makeInvisible();
        this.c.makeInvisible();
        this.d.makeInvisible();
        this.e.makeInvisible();
        this.f.makeInvisible();
        this.g.makeInvisible();
        
        switch (digit) {
            case 0:
                this.a.makeVisible();
                this.b.makeVisible();
                this.c.makeVisible();
                this.d.makeVisible();
                this.e.makeVisible();
                this.f.makeVisible();
                break;
            case 1:
                this.b.makeVisible();
                this.c.makeVisible();
                break;
            case 2:
                this.a.makeVisible();
                this.b.makeVisible();
                this.d.makeVisible();
                this.e.makeVisible();
                this.g.makeVisible();
                break;
            case 3:
                this.a.makeVisible();
                this.b.makeVisible();
                this.c.makeVisible();
                this.d.makeVisible();
                this.g.makeVisible();
                break;
            case 4:
                this.b.makeVisible();
                this.c.makeVisible();
                this.f.makeVisible();
                this.g.makeVisible();
                break;
            case 5:
                this.a.makeVisible();
                this.c.makeVisible();
                this.d.makeVisible();
                this.f.makeVisible();
                this.g.makeVisible();
                break;
            case 6:
                this.a.makeVisible();
                this.c.makeVisible();
                this.d.makeVisible();
                this.e.makeVisible();
                this.f.makeVisible();
                this.g.makeVisible();
                break;
            case 7:
                this.a.makeVisible();
                this.b.makeVisible();
                this.c.makeVisible();
                break;
            case 8:
                this.a.makeVisible();
                this.b.makeVisible();
                this.c.makeVisible();
                this.d.makeVisible();
                this.e.makeVisible();
                this.f.makeVisible();
                this.g.makeVisible();
                break;
            case 9:
                this.a.makeVisible();
                this.b.makeVisible();
                this.c.makeVisible();
                this.d.makeVisible();
                this.f.makeVisible();
                this.g.makeVisible();
                break;
            default:
                break;
        }
    }

    /**
     * Returns total rectangleWidth of segment sign.
     * @return rectangleWidth of segment sign
     */
    public int getWidth() {
        return (2 * this.rectangleWidth + this.rectangleHeight);
    }
    
    /**
     * Sets color of segment sign. Valid colors are "red", "yellow", "blue", "green", "magenta" and "black".
     * @param color for segment sign
     */
    public void setColor(String color) {
        this.a.changeColor(color);
        this.b.changeColor(color);
        this.c.changeColor(color);
        this.d.changeColor(color);
        this.e.changeColor(color);
        this.f.changeColor(color);
        this.g.changeColor(color);
    }
}
