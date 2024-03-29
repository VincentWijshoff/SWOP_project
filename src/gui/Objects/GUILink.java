package gui.Objects;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.HashSet;

/**
 * A GUIObject that represents a hyperlink that is drawn on the canvas
 */
public class GUILink extends GUIString {

    private final String href;  // href off this link

    /**
     * Create a GUILink
     * @param text  the text that represents the GUILink
     * @param x     the x coordinate of the GUILink
     * @param y     the y coordinate of the GUILink
     * @param href  The href of the link
     */
    public GUILink(String text, int x, int y, String href) {
        super(text, x, y);
        this.href = href;
    }

    /**
     * Constructor
     * @param text  The text of this link
     * @param href  The link behind this link
     */
    public GUILink(String text, String href) {
        super(text);
        this.href = href;
    }

    /**
     * Get the href representing this GUILink
     * @return  the href of this link
     */
    public String getHref() {
        return href;
    }

    /**
     * draw the GUILink
     * @param g the graphics needed to draw the GUILink
     * @param paneOffsets the amount of pixels to add because of pane scrolling
     */
    @Override
    public void draw(Graphics g, int... paneOffsets) {
        int xOffset = paneOffsets.length == 2 ? paneOffsets[0] : 0;
        int yOffset = paneOffsets.length == 2 ? paneOffsets[1] : 0;
        int x = this.coordX + xOffset;
        int y = this.coordY + yOffset;

        Color oldColor = g.getColor();
        g.setColor(Color.BLUE);
        g.drawString(text, x, y + height);
        g.drawLine(x, y+height, x+this.width, y+height);
        g.setColor(oldColor);
    }

    /**
     * Handles the mouse event of this link
     * @param x             The x coordinate of the mouse event
     * @param y             The y coordinate of hte mouse event
     * @param id            The id of the event
     * @param clickCount    The click count of the event
     */
    @Override
    public void handleMouseEvent(int x, int y, int id, int clickCount) {

        if (isInGUIObject(x, y) && id == MouseEvent.MOUSE_PRESSED) {
            //href is an absolute URL
            this.pageLoader.load(this.href);
        }
    }

    @Override
    public HashSet<GUIObject> copy() {
        HashSet<GUIObject> cpy = new HashSet<>();
        GUILink copy = new GUILink(this.text, this.coordX, this.coordY, this.href);
        cpy.add(copy);
        return cpy;
    }
}