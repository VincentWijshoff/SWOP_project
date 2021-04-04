package gui.Objects;

import events.EventHandler;

import java.awt.*;

/**
 * A GUIObject that represents a string drawn on the canvas
 */
public class GUIString extends GUIObject {

    String text;

    /**
     * Create a new GUIString object
     * @param text  The text off this object
     * @param x     The x coordinate off the string
     * @param y     The y coordinate off the string
     */
    public GUIString(String text, int x, int y) {
        super();

        this.text = text;
        setPosition(x, y); // Width and height are calculated during drawing (fonts?)
    }

    /**
     * constructor
     * @param text  The text off this text
     */
    public GUIString(String text) {
        super();

        this.text = text;
    }

    /**
     * Get text of this string
     * @return text
     */
    public String getText() {
        return text;
    }

    /**
     * Draw the GUIString
     * @param g the graphics needed to draw each object
     */
    public void draw(Graphics g) {
        g.drawString(this.text, coordX, coordY + height);
    }

    /*
     Strings have a slightly altered definition for this method, since they are rendered
     with the (x,y) position as their bottom left corner, instead of their top left corner, like other objects.
     */

    /**
     * The new position off this text
     * @param x the new x coordinate
     * @param y the new y coordinate
     */
    @Override
    public void setPosition(int x, int y) {
        super.setPosition(x, y);
    }

    /**
     * Handle a click on this text
     * @param x             The x coordinate off the mouse event
     * @param y             The y coordinate off hte mouse event
     * @param id            The id off the event
     * @param clickCount    The click count off the event
     */
    @Override
    public void handleMouseEvent(int x, int y, int id, int clickCount) {
        if (isInGUIObject(x, y))
            System.out.println("You clicked on a GUIString: "+ this.text);
    }

    /**
     * set the document area for a GUIObject
     * @param h  the document area that needs to be set
     */
    @Override
    public void setHandler(EventHandler h) {
        this.eventHandler = h;

        this.updateDimensions();
    }

    /**
     * Handle the updating of the dimensions off this string
     */
    @Override
    public void updateDimensions() {
        this.width = this.eventHandler.getFontMetrics().stringWidth(text);
        this.height = this.eventHandler.getFontMetrics().getHeight();
    }
}