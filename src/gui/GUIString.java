package gui;

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
        int textWidth = (int) g.getFontMetrics().getStringBounds(text, g).getWidth();
        int textHeight = (int) g.getFontMetrics().getStringBounds(text, g).getHeight();
        setDimensions(textWidth, textHeight);

        g.drawString(this.text, coordX, coordY + height);
    }

    /*
     Strings have a slightly altered definition for this method, since they are rendered
     with the (x,y) position as their bottom left corner, instead of their top left corner, like other objects.
     */

    @Override
    public void setPosition(int x, int y) {
        super.setPosition(x, y);
    }

    /**
     * Handle the click on this string
     */
    @Override
    public void handleClick(int x, int y) {
        System.out.println("You clicked on a GUIString: "+ this.text);
    }

    /**
     * set the document area for a GUIObject
     * @param h  the document area that needs to be set
     */
    @Override
    public void setHandler(WindowHandler h) {
        this.handler = h;

        int textWidth = this.handler.getFontMetrics().stringWidth(text);
        int textHeight = this.handler.getFontMetrics().getHeight();
        setDimensions(textWidth, textHeight);
    }
}