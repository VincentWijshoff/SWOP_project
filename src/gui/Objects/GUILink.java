package gui.Objects;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * A GUIObject that represents a hyperlink that is drawn on the canvas
 */
public class GUILink extends GUIString {

    private String href;

    /**
     * Create a GUILink
     * @param text  the text that represents the GUILink
     * @param x     the x coordinate off the GUILink
     * @param y     the y coordinate off the GUILink
     * @param href  The href off the link
     */
    public GUILink(String text, int x, int y, String href) {
        super(text, x, y);
        this.href = href;
    }

    /**
     * Constructor
     * @param text  The text off this link
     * @param href  The link behind this link
     */
    public GUILink(String text, String href) {
        super(text);
        this.href = href;
    }

    /**
     * Get the href representing this GUILink
     * @return  the href off this link
     */
    public String getHref() {
        return href;
    }

    /**
     * draw the GUILink
     * @param g the graphics needed to draw the GUILink
     */
    public void draw(Graphics g) {

        Color oldColor = g.getColor();
        g.setColor(Color.BLUE);
        g.drawString(text, coordX, coordY + height);
        g.drawLine(coordX, coordY+height, coordX+this.width, coordY+height);
        g.setColor(oldColor);
    }

    /**
     * Handles the mouse event off this link
     * @param x             The x coordinate off the mouse event
     * @param y             The y coordinate off hte mouse event
     * @param id            The id off the event
     * @param clickCount    The click count off the event
     */
    public void handleMouseEvent(int x, int y, int id, int clickCount) {

        if (isInGUIObject(x, y) && id == MouseEvent.MOUSE_PRESSED) {
            //href is an absolute URL
            this.eventHandler.load(this.href);
        }
    }
}