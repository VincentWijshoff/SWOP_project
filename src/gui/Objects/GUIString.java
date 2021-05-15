package gui.Objects;

import events.FontMetricsHandler;

import java.awt.*;
import java.util.HashSet;

/**
 * A GUIObject that represents a string drawn on the canvas
 */
public class GUIString extends GUIObject {

    String text;    // the text of this string

    /**
     * Create a new GUIString object
     * @param text  The text of this object
     * @param x     The x coordinate of the string
     * @param y     The y coordinate of the string
     */
    public GUIString(String text, int x, int y) {
        super();

        this.text = text;
        setPosition(x, y); // Width and height are calculated during drawing (fonts?)
    }

    /**
     * constructor
     * @param text  The text of this text
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
    @Override
    public void draw(Graphics g, int... paneOffsets) {
        int xOffset = paneOffsets.length == 2 ? paneOffsets[0] : 0;
        int yOffset = paneOffsets.length == 2 ? paneOffsets[1] : 0;
        g.drawString(this.text, coordX + xOffset, coordY + yOffset + height);
    }

    @Override
    public HashSet<GUIObject> copy() {
        HashSet<GUIObject> cpy = new HashSet<>();
        GUIString copy = new GUIString(this.text, this.coordX, this.coordY);
        cpy.add(copy);
        return cpy;
    }

    /*
     Strings have a slightly altered definition for this method, since they are rendered
     with the (x,y) position as their bottom left corner, instead of their top left corner, like other objects.
     */

    /**
     * The new position of this text
     * @param x the new x coordinate
     * @param y the new y coordinate
     */
    @Override
    public void setPosition(int x, int y) {
        super.setPosition(x, y);
    }

    /**
     * set the document area for a GUIObject
     * @param h  the document area that needs to be set
     */
    @Override
    public void setFontMetricsHandler(FontMetricsHandler h) {
        this.fontMetricsHandler = h;

        this.updateDimensions();
    }

    /**
     * Handle the updating of the dimensions of this string
     */
    @Override
    public void updateDimensions() {
        this.width = this.fontMetricsHandler.getFontMetrics().stringWidth(text);
        this.height = this.fontMetricsHandler.getFontMetrics().getHeight();
    }
}