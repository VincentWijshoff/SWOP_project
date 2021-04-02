package gui.Objects;

import java.awt.*;

/**
 * A GUIObject that represents a rectangle drawn on the canvas
 */
public class GUIRectangle extends GUIObject{

    /**
     * Create a new GUIRectangle
     * @param x         The x position off the rectangle
     * @param y         The y position off the rectangle
     * @param width     The width off the rectangle
     * @param height    The height off the rectangle
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

    /**
     * Handle a click on the rectangle
     */
    @Override
    public void handleMouseEvent(int x, int y, int id, int clickCount) {
        if (isInGUIObject(x, y))
            System.out.println("You clicked on a GUIRectangle");
    }
}