package gui.Objects.ScrollBars;

public abstract class ScrollBar {

    // The 'scrollbar' is the gray area in which the 'scrollbar slider' can move.
    private int scrollbarWidth, scrollbarHeight, scrollBarCoordX,scrollBarCoordY;

    // The 'scrollbar slider' is the part that actually slides.
    private ScrollbarSlider slider;

    // This buffer is the minimal space that's always present between the edge
    // of the slider and the edge of the scrollbar.
    private static final int buffer = 5;


    /**
     * Used to calculate the maximum amount of pixels the content can move.
     * @return the amount.
     */
    public abstract int calcMaxOffset();

    /**
     * The method used to slide, each scrollbar needs one.
     */
    public abstract void slide();

    /**
     * Drawing the scrollbar. This method should call the draw method from its
     * slider as well.
     */
    public abstract void draw();


    public ScrollbarSlider getSlider() { return this.slider; }
    public void setSlider(ScrollbarSlider slider) { this.slider = slider; }

    public int getScrollbarWidth() { return this.scrollbarWidth; }
    public int getScrollbarHeight() { return this.scrollbarHeight; }
    public int getScrollbarCoordX() { return this.scrollBarCoordX; }
    public int getScrollbarCoordY() { return this.scrollBarCoordY; }
    public int getBuffer() { return buffer; }

    public void setScrollbarWidth(int width) { this.scrollbarWidth = width; }
    public void setScrollbarHeight(int height) { this.scrollbarHeight = height; }
    public void setScrollbarCoordX(int x) { this.scrollBarCoordX = x; }
    public void setScrollBarCoordY(int y) { this.scrollBarCoordY = y; }

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
