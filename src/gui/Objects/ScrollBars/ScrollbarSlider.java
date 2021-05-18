package gui.Objects.ScrollBars;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * A GUIObject that represents a rectangle drawn on the canvas
 */
public class ScrollbarSlider {

    //the corresponding scrollbar
    ScrollBar scrollBar;
    //the slider belongs to a horizontal scrollbar or not?
    final boolean isHorizontal;
    //dimensions of the slider
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
     * @param g the graphics needed to draw the slider
     */
    public void draw(Graphics g, int... paneOffsets) {
        int xOffset = paneOffsets.length == 2 ? paneOffsets[0] : 0;
        int yOffset = paneOffsets.length == 2 ? paneOffsets[1] : 0;
        Color oldColor = g.getColor();
        g.setColor(this.sliderColor);
        g.fillRect(coordX + xOffset, coordY + yOffset, width, height);
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
     * @param id            The id of the mouse event
     * @param clickCount    The click count of the user
     */
    public void handleMouseEvent(int id, int x, int y, int clickCount){
        System.out.println("Clicked on scrollbar slider!");
        if (!this.canSlide) return;

        if (id == MouseEvent.MOUSE_PRESSED) {
            startClick(x, y);
        } else if (id == MouseEvent.MOUSE_DRAGGED && this.isSliding) {
            drag(x, y);
        } else if (id == MouseEvent.MOUSE_RELEASED) {
            releaseClick();
        }
    }

    /**
     * User clicked on the slider, change the slider color and save where the click happened in the slider
     * @param x x-coordinate of the click
     * @param y y-coordinate of the click
     */
    private void startClick(int x, int y){
        this.isSliding = true;
        this.sliderColor = Color.LIGHT_GRAY;
        this.cursorStart = this.isHorizontal ? x-coordX : y-coordY;
    }

    /**
     * User is dragging the slider, change coordX (horizontal slider) or coordY (vertical slider)
     * @param x the x-coordinate of the slided click
     * @param y the y-coordinate of the slided click
     */
    private void drag(int x, int y){
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
        } else {
            int newSliderCoordY = y-this.cursorStart;
            // Keep the slider within scrollbar boundaries
            if ((newSliderCoordY > this.scrollBar.getSliderStart())/* && (newSliderCoordY+this.height < this.scrollBar.getSliderEnd())*/) {
                this.coordY = newSliderCoordY; // Slide with mouse
            } else if (newSliderCoordY <= this.scrollBar.getSliderStart()) {
                this.coordY = this.scrollBar.getSliderStart(); // Slide most up
            } else {
                this.coordY = this.scrollBar.getSliderEnd()-this.height; // Slide most down
            }
        }
    }

    /**
     * User stops dragging
     */
    private void releaseClick(){
        this.isSliding = false;
        this.sliderColor = Color.WHITE;
    }

}
