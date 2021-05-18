package gui.Objects.ScrollBars;

import java.awt.*;

public abstract class ScrollBar {

    Scrollable scrollable;

    public ScrollBar(Scrollable scrollable) {
        this.scrollable = scrollable;
    }

    /**
     * The 'scrollbar' is the gray area in which the 'scrollbar slider' can move.
     */
    private int scrollbarWidth, scrollbarHeight, scrollBarCoordX,scrollBarCoordY;

    /**
     * The 'scrollbar slider' is the part that actually slides.
     */
    private ScrollbarSlider slider;


    /**
     * This buffer is the minimal space that's always present between the edge
     * of the slider and the edge of the scrollbar.
     */
    private static final int buffer = 5;


    /**
     * Used to calculate the maximum amount of pixels the content can move.
     * @return the amount.
     */
    public abstract int calcMaxOffset();

    /**
     * The method used to slide, each scrollbar needs one.
     * @param movement the amount to slide
     */
    public abstract void slide(int movement);

    /**
     * Drawing the scrollbar. This method should call the draw method from its
     * slider as well.
     */
    public abstract void draw(Graphics g, int... paneOffsets);

    /**
     * Used to get the boundaries which the slider cannot cross.
     * @return the left-most 'extreme' coordinate of the slider.
     */
    public abstract int getSliderStart();

    /**
     * Used to get the boundaries which the slider cannot cross.
     * @return the right-most 'extreme' coordinate of the slider.
     */
    public abstract int getSliderEnd();

    public abstract void handleMouseEvent(int id, int x, int y, int clickCount);

    public abstract void updateDimensions();

    public abstract void setBoundaries();
    /**
         * @return the slider of this scrollbar
         */
    public ScrollbarSlider getSlider() { return this.slider; }

    /**
     * @param slider the slider to set to this scrollbar
     */
    public void setSlider(ScrollbarSlider slider) { this.slider = slider; }

    /**
     * @return width of this scrollbar
     */
    public int getScrollbarWidth() { return this.scrollbarWidth; }

    /**
     * @return height of this scrollbar
     */
    public int getScrollbarHeight() { return this.scrollbarHeight; }

    /**
     * @return x-coordinate of this scrollbar
     */
    public int getScrollbarCoordX() { return this.scrollBarCoordX; }

    /**
     * @return y-coordinate of this scrollbar
     */
    public int getScrollbarCoordY() { return this.scrollBarCoordY; }

    /**
     * @return the buffer/margin of this scrollbar
     */
    public static int getBuffer() { return buffer; }

    /**
     * @param width to set to this scrollbar
     */
    public void setScrollbarWidth(int width) { this.scrollbarWidth = width; }

    /**
     * @param height to set to this scrollbar
     */
    public void setScrollbarHeight(int height) { this.scrollbarHeight = height; }

    /**
     * @param x x-coordinate to set to this scrollbar
     */
    public void setScrollbarCoordX(int x) { this.scrollBarCoordX = x; }

    /**
     * @param y y-coordinate to set to this scrollbar
     */
    public void setScrollBarCoordY(int y) { this.scrollBarCoordY = y; }

    /**
     * @return return x-coordinate of this scrollbar's scrollable
     */
    public int getX() {return scrollable.getX();}

    /**
     * @return return y-coordinate of this scrollbar's scrollable
     */
    public int getY() {return scrollable.getY();}

    /**
     * @return return the height of this scrollbar's scrollable
     */
    public int getHeight() {return scrollable.getHeight();}

    /**
     * Check if the given coordinates collide with the position of this object
     * @param X the x coordinate
     * @param Y the y coordinate
     * @return  true if the given position collides with the position of the object
     */
    public boolean isOnScrollBar(int X, int Y) {
        return (X >= this.scrollBarCoordX &&
                X <= this.scrollBarCoordX + this.scrollbarWidth &&
                Y >= this.scrollBarCoordY &&
                Y <= this.scrollBarCoordY + this.scrollbarHeight);
    }

}
