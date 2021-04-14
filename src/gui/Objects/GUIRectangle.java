package gui.Objects;

import java.awt.*;

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
    public void draw(Graphics g) {
        g.drawRect(coordX, coordY, width, height);
    }
}
