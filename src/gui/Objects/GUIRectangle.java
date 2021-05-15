package gui.Objects;

import java.awt.*;
import java.util.HashSet;

/**
 * A GUIObject that represents a rectangle drawn on the canvas
 */
public class GUIRectangle extends GUIObject{

    /**
     * Create a new GUIRectangle
     * @param x         The x position of the rectangle
     * @param y         The y position of the rectangle
     * @param width     The width of the rectangle
     * @param height    The height of the rectangle
     */
    public GUIRectangle(int x, int y, int width, int height) {
        super();

        // Initialise dimensions of rectangle
        this.width = width;
        this.height = height;
        setPosition(x, y);
    }

    /**
     * Draw the rectangle
     * @param g the graphics needed to draw each object
     */
    public void draw(Graphics g, int... paneOffsets) {
        int xOffset = paneOffsets.length == 2 ? paneOffsets[0] : 0;
        int yOffset = paneOffsets.length == 2 ? paneOffsets[1] : 0;
        g.drawRect(coordX + xOffset, coordY + yOffset, width, height); }

    @Override
    public HashSet<GUIObject> copy() {
        HashSet<GUIObject> cpy = new HashSet<>();
        GUIRectangle copy = new GUIRectangle(this.coordX, this.coordY, this.width, this.height);
        cpy.add(copy);
        return cpy;
    }
}
