import java.util.ArrayList;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.lang.reflect.Method;
import java.util.Collections;

/**
 * Automatically sends messages to the objects:<br />
 * moveDown() - when you press DOWN<br />
 * moveUp() - when you press UP<br />
 * moveLeft() - when you press LEFT<br />
 * moveRight() - when you press RIGHT<br />
 * activate() - when you press ENTER or SPACE<br />
 * exit() - when you press ESC key<br />
 * tick() - every 0.25 seconds<br />
 * selectCoordinates(x, y) - at the click of a mouse
 */

public class Manager {
    private ArrayList<Object> managedObjects;
    private ArrayList<Integer> deletedObjects;
    private long oldTick;
    private static final long TICK_LENGTH = 250000000; // (1 000 000 000 / TICK_LENGTH = FPS)
    
    private class KeyManager extends KeyAdapter {
        public void keyPressed(KeyEvent event) {
            if (event.getKeyCode() == KeyEvent.VK_DOWN) {
                Manager.this.sendMessage("moveDown");
            } else if (event.getKeyCode() == KeyEvent.VK_UP) {
                Manager.this.sendMessage("moveUp");
            } else if (event.getKeyCode() == KeyEvent.VK_LEFT) {
                Manager.this.sendMessage("moveLeft");
            } else if (event.getKeyCode() == KeyEvent.VK_RIGHT) {
                Manager.this.sendMessage("moveRight");
            } else if (event.getKeyCode() == KeyEvent.VK_SPACE || event.getKeyCode() == KeyEvent.VK_ENTER) {
                Manager.this.sendMessage("activate");
            } else if (event.getKeyCode() == KeyEvent.VK_ESCAPE) {
                Manager.this.sendMessage("exit");
            }
        }
    }
    
    private class TimerManager implements ActionListener {
        public void actionPerformed(ActionEvent event) {
            long newTick = System.nanoTime();
            if (newTick - Manager.this.oldTick >= Manager.TICK_LENGTH || newTick < Manager.TICK_LENGTH) {
                Manager.this.oldTick = (newTick / Manager.TICK_LENGTH) * Manager.TICK_LENGTH;
                Manager.this.sendMessage("tick");
            }
        }
    }
    
    private class MouseManager extends MouseAdapter {
        public void mouseClicked(MouseEvent event) {
            if (event.getButton() == MouseEvent.BUTTON1) {
                Manager.this.sendMessage("selectCoordinates", event.getX(), event.getY());
            }
        }
    }

    /**
     * Creates a new manager that does not manage any objects yet.
     */
    public Manager() {
        this.managedObjects = new ArrayList<Object>();
        this.deletedObjects = new ArrayList<Integer>();
        Canvas.getCanvas().addKeyListener(new KeyManager());
        Canvas.getCanvas().addTimerListener(new TimerManager());
        Canvas.getCanvas().addMouseListener(new MouseManager());
    }

    /**
     * Manager will manage object.
     */
    public void manageObject(Object object) {
        this.managedObjects.add(object);
    }
    
    /**
     * Manager stops managing object.
     */
    public void stopManagingObject(Object object) {
        int index = this.managedObjects.indexOf(object);
        if (index >= 0) {
            this.managedObjects.set(index, null);
            this.deletedObjects.add(index);
        }
    }

    private void removeDeletedObjects() {
        if (this.deletedObjects.size() > 0) {
            Collections.sort(this.deletedObjects, Collections.reverseOrder());
            for (int i = this.deletedObjects.size() - 1; i >= 0; i--) {
                this.managedObjects.remove(this.deletedObjects.get(i));
            }
            this.deletedObjects.clear();
        }        
    }

    private void sendMessage(String selector) {
        for (Object addressee : this.managedObjects) {
            try {
                if (addressee != null) {
                    Method message = addressee.getClass().getMethod(selector);
                    message.invoke(addressee);
                }
            } catch (Exception e) {
                // do nothing
            }
        }
        this.removeDeletedObjects();
    }

    private void sendMessage(String selector, int firstParam, int secondParam) {
        for (Object addressee : this.managedObjects) {
            try {
                if (addressee != null) {
                    Method message = addressee.getClass().getMethod(selector, Integer.TYPE, Integer.TYPE);
                    message.invoke(addressee, firstParam, secondParam);
                }
            } catch (Exception e) {
                // do nothing
            }
        }
        this.removeDeletedObjects();
    }
}
