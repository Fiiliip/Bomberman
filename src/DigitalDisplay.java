/**
 * Class that displays any number on a numeric display consisting of segmented characters ranging from 0 to 99 inclusive.
 * 
 * This class was created at school exercises and adapted outside the exercises for use on current semester work!
 * 
 * @author MichalMrena (https://github.com/MichalMrena)
 * @author Fiiliip (https://github.com/Fiiliip)
 */
public class DigitalDisplay {
    
    private int limit;
    private int value;

    private SegmentSign tens;
    private SegmentSign units;

    /**
    * Displays values ​​from integer
    * interval <0, limit).
    */
    public DigitalDisplay(int limit, int topLeftX, int topLeftY) {
        this.limit = 100;
        if (limit <= 100 && limit > 0) {
            this.limit = limit;
        }
        this.value = 0;
        
        this.tens = new SegmentSign(topLeftX, topLeftY);
        this.units = new SegmentSign(topLeftX + this.tens.getWidth() + 3, topLeftY);
        
        this.makeVisible();
    }

    /**
     * Sets the new value.
     * @param newValue value to display
     */
    public void setValue(int newValue) {
        if (newValue < this.limit && newValue >= 0) {
            this.value = newValue; 
        }
        
        this.makeVisible();
    }
    
    /**
     * Returns a two-digit number prefixed with 0 if it is current
     * value single digit.
     */
    public String getFormattedValue() {
        if (this.value < 10) {
            return "0" + this.value;
        } else {
            return "" + this.value;
        }
    }
    
    /**
     * Displays a numeric display with the set value.
     */
    public void makeVisible() {
        if (this.tens != null && this.units != null) {
            this.tens.displayDigit(this.value / 10);
            this.units.displayDigit(this.value % 10);
        }
    }
}
