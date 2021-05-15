package gui.Objects.ScrollBars;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * A GUIObject that represents a rectangle drawn on the canvas
 */
public class ScrollbarSlider {

    ScrollBar scrollBar;
    boolean isHorizontal;
    public int coordX, coordY, width, height;

    // Relative position of click in the slider
    int cursorStart;

    // Slider properties
    boolean canSlide = false;
    boolean isSliding = false;
    Color sliderColor = Color.WHITE;

    /**
     * Create a new ScrollbarSlider
     * @param scrollBar The scrollbar
     * @param x         The x position of the slider
     * @param y         The y position of the slider
     * @param width     The width of the slider
     * @param height    The height of the slider
     */
    public ScrollbarSlider(HorizontalScrollBar scrollBar, int x, int y, int width, int height) {
        this.scrollBar = scrollBar;
        this.isHorizontal = true;

        this.coordX = x;
        this.coordY = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Create a new ScrollbarSlider
     * @param scrollBar The scrollbar
     * @param x         The x position of the slider
     * @param y         The y position of the slider
     * @param width     The width of the slider
     * @param height    The height of the slider
     */
    public ScrollbarSlider(VerticalScrollBar scrollBar, int x, int y, int width, int height) {
        this.scrollBar = scrollBar;
        this.isHorizontal = false;

        this.coordX = x;
        this.coordY = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Draw the slider
     * @param g the graphics needed to draw the object
     */
    public void draw(Graphics g) {
        Color oldColor = g.getColor();
        g.setColor(this.sliderColor);
        g.fillRect(coordX, coordY, width, height);
        g.setColor(oldColor);
    }

    /**
     * checks if the given position is on the slider
     * @param X the given x coordinate
     * @param Y the given y coordinate
     * @return  a boolean representing if the position is on this slider
     */
    public boolean isOnSlider(int X, int Y) {
        return (X >= this.coordX &&
                X <= this.coordX + this.width &&
                Y >= this.coordY &&
                Y <= this.coordY + this.height);
    }

    /**
     * Handles the interaction between the user's mouse and the slider
     * @param id            The id off the mouse event
     * @param clickCount    The click count off the user
     */
    public void handleMouseEvent(int id, int x, int y, int clickCount){
        System.out.println("Clicked on scrollbar slider!");
        if (!this.canSlide) return;

        // User clicked on the slider, change the slider color and save where the click happened in the slider
        if (id == MouseEvent.MOUSE_PRESSED) {
            this.isSliding = true;
            this.sliderColor = Color.LIGHT_GRAY;
            this.cursorStart = this.isHorizontal ? x-coordX : y-coordY;
        }

        // User is dragging the slider, change coordX
        else if (id == MouseEvent.MOUSE_DRAGGED && this.isSliding) {
            if (isHorizontal) {
                int newSliderCoordX = x-this.cursorStart;

                // Keep the slider within scrollbar boundaries
                if ((newSliderCoordX > this.scrollBar.getSliderStart()) && (newSliderCoordX+this.width < this.scrollBar.getSliderEnd())) {
                    this.coordX = newSliderCoordX; // Slide with mouse
                } else if (newSliderCoordX <= this.scrollBar.getSliderStart()) {
                    this.coordX = this.scrollBar.getSliderStart(); // Slide most left
                } else {
                    this.coordX = this.scrollBar.getSliderEnd()-this.width; // Slide most right
                }
            }
            else {
                int newSliderCoordY = y-this.cursorStart;

                // Keep the slider within scrollbar boundaries
                if ((newSliderCoordY > this.scrollBar.getSliderStart()) && (newSliderCoordY+this.height < this.scrollBar.getSliderEnd())) {
                    this.coordY = newSliderCoordY; // Slide with mouse
                } else if (newSliderCoordY <= this.scrollBar.getSliderStart()) {
                    this.coordY = this.scrollBar.getSliderStart(); // Slide most up
                } else {
                    this.coordY = this.scrollBar.getSliderEnd()-this.height; // Slide most down
                }
            }

        }

        // User stops dragging
        else if (id == MouseEvent.MOUSE_RELEASED) {
            this.isSliding = false;
            this.sliderColor = Color.WHITE;
        }
    }

}
