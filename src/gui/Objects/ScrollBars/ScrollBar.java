package gui.Objects.ScrollBars;

public abstract class ScrollBar {

    private ScrollbarSlider slider;

    // The 'scrollbar' is the gray area in which the 'scrollbar slider' can move.
    private int scrollbarWidth;
    private int scrollbarHeight;
    private int scrollBarCoordX;
    private int scrollBarCoordY;
    private int offset;
    private static final int buffer = 5;


    public abstract int calcMaxOffset();

    public abstract void slide();

    public abstract void draw();

    public ScrollbarSlider getSlider() { return this.slider; }
    public void setSlider(ScrollbarSlider slider) { this.slider = slider; }

    public int getScrollbarWidth() { return this.scrollbarWidth; }
    public int getScrollbarHeight() { return this.scrollbarHeight; }
    public int getScrollbarCoordX() { return this.scrollBarCoordX; }
    public int getScrollbarCoordY() { return this.scrollBarCoordY; }
    public int getOffset() { return this.offset; }
    public int getBuffer() { return buffer; }

    public void setScrollbarWidth(int width) { this.scrollbarWidth = width; }
    public void setScrollbarHeight(int height) { this.scrollbarHeight = height; }
    public void setScrollbarCoordX(int x) { this.scrollBarCoordX = x; }
    public void setScrollBarCoordY(int y) { this.scrollBarCoordY = y; }
    public void setOffset(int amount) { this.offset = amount; }

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
