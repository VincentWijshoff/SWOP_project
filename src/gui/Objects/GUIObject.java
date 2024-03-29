package gui.Objects;

import events.FontMetricsHandler;
import events.PageLoader;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.UUID;

/**
 This class is the superclass of all other GUIObjects. Properties all GUIObjects need are defined here,
 such as their position, dimensions and ids.
 */
public abstract class GUIObject {

    // some needed information about each GUI object
    private final UUID id;
    public int width;
    public int height;
    public int coordX;
    public int coordY;

    // the link to the window area
    protected FontMetricsHandler fontMetricsHandler;
    protected PageLoader pageLoader;

    /**
     * constructor of an object, it assigns a unique id
     */
    public GUIObject() {
        this.id = UUID.randomUUID();
    }

    /**
     * constructor
     * @param x The x coordinate of the object
     * @param y The y coordinate of the object
     * @param w The width of the object
     * @param h The height of the object
     */
    public GUIObject(int x, int y, int w, int h) {
        this.id = UUID.randomUUID();
        this.coordX = x;
        this.coordY = y;
        this.width = w;
        this.height = h;
    }

    /**
     * Constructor
     * @param fontMetricsHandler  The event handler
     * @param x             The x coordinate
     * @param y             The y coordinate
     * @param w             The width
     * @param h             The height
     */
    public GUIObject(FontMetricsHandler fontMetricsHandler, int x, int y, int w, int h) {
        setFontMetricsHandler(fontMetricsHandler);

        this.id = UUID.randomUUID();
        this.coordX = x;
        this.coordY = y;
        this.width = w;
        this.height = h;
    }

    /**
     * set the document area for a GUIObject
     * @param h  the document area that needs to be set
     */
    public void setFontMetricsHandler(FontMetricsHandler h) {
        this.fontMetricsHandler = h;
    }

    /**
     * Set the page loader for this gui object
     * @param p     The page loader
     */
    public void setPageLoader(PageLoader p) {
        this.pageLoader = p;
    }

    /**
     * Check if the given coordinates collide with the position of this object
     * @param x the x coordinate
     * @param y the y coordinate
     * @return  true if the given position collides with the position of the object
     */
    public boolean isInGUIObject(int x, int y) {
        return (x >= this.coordX &&
                x <= this.coordX + this.width &&
                y >= this.coordY &&
                y <= this.coordY + this.height);
    }

    /**
     * update the position of this object
     * @param x the new x coordinate
     * @param y the new y coordinate
     */
    public void setPosition(int x, int y) {
        this.coordX = x;
        this.coordY = y;
    }

    /**
     * update the dimension of the object
     */
    public void updateDimensions() {
    }

    /**
     * draw the object, this function should be overridden by each specific object
     * @param g the graphics needed to draw each object
     * @param paneOffsets the amount of pixels to add because of pane scrolling
     */
    public void draw(Graphics g, int... paneOffsets) { }

    /**
     * Get the list of all child objects
     * @return  The list of child objects of this object
     */
    public ArrayList<GUIObject> getChildObjects() {
        return new ArrayList<>();
    }

    /**
     * Handle a key-press for this Object, if a special action is needed, this should be overridden.
     */
    public void handleKeyEvent(int id, int keyCode, char keyChar, int modifier) { }

    /**
     * Handle the click on this Object, if a special action is needed, this should be overridden.
     */
    public void handleMouseEvent(int x, int y, int id, int clickCount) {
        if (this.isInGUIObject(x, y)) {
            System.out.println("You clicked on a GUIObject");
        }
    }

    /**
     * Get all buttons that are related to this object
     * @return  A list of al related buttons
     */
    public ArrayList<GUIButton> getButtons(){
        return new ArrayList<>();
    }

    /**
     * Get all inputs related to this object
     * @return  A list of al related inputs
     */
    public ArrayList<GUIInput> getInputs(){
        return new ArrayList<>();
    }

    public abstract HashSet<GUIObject> copy();

    public FontMetrics getFontMetrics() {
        return this.fontMetricsHandler.getFontMetrics();
    }

}
