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
        g.drawString(this.text, coordX, coordY);
    }

    /*
     Strings have a slightly altered definition for this method, since they are rendered
     with the (x,y) position as their bottom left corner, instead of their top left corner, like other objects.
     */

    /**
     * Strings have a slightly altered definition for this method, since they are rendered
     *  with the (x,y) position as their bottom left corner, instead of their top left corner, like other objects.
     * @param x the x coordinate
     * @param y the y coordinate
     * @return true if the click is on this string
     */
    public boolean isInGUIObject(int x, int y) {
        return (x >= this.coordX &&
                x <= this.coordX + this.width &&
                y <= this.coordY &&
                y >= this.coordY - this.height);
    }

    /**
     * Handle the click on this string
     */
    @Override
    public void handleClick() {
        System.out.println("You clicked on a GUIString");
    }

    /**
     * set the document area for a GUIObject
     * @param documentArea  the document area that needs to be set
     */
    @Override
    public void setDocumentArea(DocumentArea documentArea) {
        this.documentArea = documentArea;

        int textWidth = this.documentArea.getWindow().getFontMetrics().stringWidth(text);
        int textHeight = this.documentArea.getWindow().getFontMetrics().getHeight();
        setDimensions(textWidth, textHeight);
    }
}