import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Image;
import java.awt.Shape;
import java.awt.Graphics;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.HashMap;
import java.util.ArrayList;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * Canvas is a class to allow for simple graphical drawing on a canvas.
 * This is a modification of the general purpose Canvas, specially made for
 * the BlueJ "shapes" example. 
 *
 * @author: Bruce Quig
 * @author: Michael Kolling (mik)
 *
 * @version: 1.6 (shapes)
 */
public class Canvas
{
    // Note: The implementation of this class (specifically the handling of
    // shape identity and colors) is slightly more complex than necessary. This
    // is done on purpose to keep the interface and instance fields of the
    // shape objects in this project clean and simple for educational purposes.

	private static Canvas canvasSingleton;

	/**
	 * Factory method to get the canvas singleton object.
	 */
	public static Canvas getCanvas() {
		if(Canvas.canvasSingleton == null) {
			Canvas.canvasSingleton = new Canvas("Bomberman", 690, 630, Color.white);
		}
		Canvas.canvasSingleton.setVisible(true);
		return Canvas.canvasSingleton;
	}

	//  ----- instance part -----

    private JFrame frame;
    private CanvasPane canvas;
    private Graphics2D graphic;
    private Color backgroundColour;
    private Image canvasImage;
    private Timer timer;
    private List<Object> objects;
    private HashMap<Object, IDraw> shapes;
    
    /**
     * Create a Canvas.
     * @param title  title to appear in Canvas Frame
     * @param width  the desired width for the canvas
     * @param height  the desired height for the canvas
     * @param bgClour  the desired background colour of the canvas
     */
    private Canvas(String title, int width, int height, Color bgColour) {
        this.frame = new JFrame();
        this.canvas = new CanvasPane();
        this.frame.setContentPane(canvas);
        this.frame.setTitle(title);
        this.canvas.setPreferredSize(new Dimension(width, height));
        this.timer = new javax.swing.Timer(25, null);
        this.timer.start();
        this.backgroundColour = bgColour;
        this.frame.pack();
        this.objects = new ArrayList<Object>();
        this.shapes = new HashMap<Object, IDraw>();
    }

    /**
     * Set the canvas visibility and brings canvas to the front of screen
     * when made visible. This method can also be used to bring an already
     * visible canvas to the front of other windows.
     * @param visible  boolean value representing the desired visibility of
     * the canvas (true or false) 
     */
    public void setVisible(boolean visible) {
        if(graphic == null) {
            // first time: instantiate the offscreen image and fill it with
            // the background colour
            Dimension size = canvas.getSize();
            this.canvasImage = canvas.createImage(size.width, size.height);
            this.graphic = (Graphics2D)canvasImage.getGraphics();
            this.graphic.setColor(backgroundColour);
            this.graphic.fillRect(0, 0, size.width, size.height);
            this.graphic.setColor(Color.black);
        }
        this.frame.setVisible(visible);
    }

    /**
     * Draw a given shape onto the canvas.
     * @param  referenceObject  an object to define identity for this shape
     * @param  color            the color of the shape
     * @param  shape            the shape object to be drawn on the canvas
     */
     // Note: this is a slightly backwards way of maintaining the shape
     // objects. It is carefully designed to keep the visible shape interfaces
     // in this project clean and simple for educational purposes.
    public void draw(Object referenceObject, String color, Shape shape) {
    	this.objects.remove(referenceObject);   // just in case it was already there
    	this.objects.add(referenceObject);      // add at the end
    	this.shapes.put(referenceObject, new ShapeDescription(shape, color));
    	this.redraw();
    }

    /**
     * Draw a given image onto the canvas.
     * @param  referenceObject  an object to define identity for this image
     * @param  image            the image object to be drawn on the canvas
     * @param  transform        the transformation applied to the image
     */
     // Note: this is a slightly backwards way of maintaining the shape
     // objects. It is carefully designed to keep the visible shape interfaces
     // in this project clean and simple for educational purposes.
    public void draw(Object objekt, BufferedImage image, AffineTransform transform) {
        this.objects.remove(objekt);   // just in case it was already there
        this.objects.add(objekt);      // add at the end
        this.shapes.put(objekt, new ImageDescription(image, transform));
        this.redraw();
    }
 
    /**
     * Erase a given shape's from the screen.
     * @param  referenceObject  the shape object to be erased 
     */
    public void erase(Object referenceObject) {
    	this.objects.remove(referenceObject);   // just in case it was already there
    	this.shapes.remove(referenceObject);
    	this.redraw();
    }

    /**
     * Set the foreground colour of the Canvas.
     * @param  newColour   the new colour for the foreground of the Canvas 
     */
    public void setForegroundColor(String colorString) {
		if(colorString.equals("red")) {
            this.graphic.setColor(Color.red);
        } else if(colorString.equals("black")) {
            this.graphic.setColor(Color.black);
        } else if(colorString.equals("blue")) {
            this.graphic.setColor(Color.blue);
        } else if(colorString.equals("yellow")) {
            this.graphic.setColor(Color.yellow);
        } else if(colorString.equals("green")) {
            this.graphic.setColor(Color.green);
        } else if(colorString.equals("magenta")) {
            this.graphic.setColor(Color.magenta);
        } else if(colorString.equals("white")) {
            this.graphic.setColor(Color.white);
        } else {
            this.graphic.setColor(Color.black);
        }
    }

    /**
     * Wait for a specified number of milliseconds before finishing.
     * This provides an easy way to specify a small delay which can be
     * used when producing animations.
     * @param  milliseconds  the number 
     */
    public void wait(int milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (Exception e) {
            // ignoring exception at the moment
        }
    }

	/**
	 * Redraw ell shapes currently on the Canvas.
	 */
    private void redraw() {
        this.erase();
        for (Object shape : this.objects) {
            this.shapes.get(shape).draw(this.graphic);
        }
        this.canvas.repaint();
    }
       
    /**
     * Erase the whole canvas. (Does not repaint.)
     */
    private void erase() {
        Color original = this.graphic.getColor();
        graphic.setColor(this.backgroundColour);
        Dimension size = this.canvas.getSize();
        this.graphic.fill(new Rectangle(0, 0, size.width, size.height));
        this.graphic.setColor(original);
    }

    public void addKeyListener(KeyListener listener) {
        this.frame.addKeyListener(listener);
    }

    public void removeKyeListener(KeyListener listener) {
        this.frame.removeKeyListener(listener);
    }
    
    public void addMouseListener(MouseListener listener) {
        this.canvas.addMouseListener(listener);
    }
    
    public void addTimerListener(ActionListener listener) {
        this.timer.addActionListener(listener);
    }

    /************************************************************************
     * Inner class CanvasPane - the actual canvas component contained in the
     * Canvas frame. This is essentially a JPanel with added capability to
     * refresh the image drawn on it.
     */
    private class CanvasPane extends JPanel {
        public void paint(Graphics g) {
            g.drawImage(Canvas.this.canvasImage, 0, 0, null);
        }
    }

    /***********************************************************************
     * Inner interface IDraw - defines functions that need to be supported by
     * shapes descriptors
     */
    private interface IDraw {
        void draw(Graphics2D graphic);
    }
    
    /************************************************************************
     * Inner class CanvasPane - the actual canvas component contained in the
     * Canvas frame. This is essentially a JPanel with added capability to
     * refresh the image drawn on it.
     */
    private class ShapeDescription implements Canvas.IDraw {
    	private Shape shape;
    	private String colorString;

		public ShapeDescription(Shape shape, String color) {
    		this.shape = shape;
    		this.colorString = color;
    	}

		public void draw(Graphics2D graphic) {
			Canvas.this.setForegroundColor(this.colorString);
			graphic.fill(this.shape);
		}
    }

    private class ImageDescription implements Canvas.IDraw {
        private BufferedImage image;
        private AffineTransform transformation;
        
        ImageDescription(BufferedImage image, AffineTransform transformation) {
            this.image = image;
            this.transformation = transformation;
        }
        
        public void draw(Graphics2D graphic) {
            graphic.drawImage(this.image, this.transformation, null); 
        }
    }
}
