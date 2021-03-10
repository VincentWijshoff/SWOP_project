package gui;

import java.awt.*;

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
        updateGUIPos(x, y); // Width and height are calculated during drawing (fonts?)
    }

    /**
     * Draw the GUIString
     * @param g the graphics needed to draw each object
     */
    public void draw(Graphics g) {
        // Bounds needed for click event
        int textWidth = (int) g.getFontMetrics().getStringBounds(text, g).getWidth();
        int textHeight = (int) g.getFontMetrics().getStringBounds(text, g).getHeight();
        updateGUIDimensions(textWidth, textHeight);

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GUIString guiString = (GUIString) o;

        return this.text.equals(guiString.text) &&
        this.coordX == guiString.coordX &&
        this.coordY == guiString.coordY;
    }

    @Override
    public String toString() {
        return "GUIString{" +
                "coordX=" + coordX +
                ", coordY=" + coordY +
                ", text='" + text + '\'' +
                '}';
    }


    @Override
    public int hashCode() {
        int result = this.coordX;
        result = 31 * result + this.coordY;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        return result;
    }
}