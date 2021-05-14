package gui.Objects.ScrollBars;

public class HorizontalScrollBar extends ScrollBar {

    private static final int horScrollBarHeight = 15;
    private static final int sliderHeight = 13;

    public static int getSliderHeight() { return sliderHeight; }

    /**
     * Constructor of the HorizontalScrollBar. Sets the height to a constant,
     * because this should never change. Also initialize the slider.
     * @post this.getSlider() is in its most right position and has its maximum width.
     */
    public HorizontalScrollBar() {
        this.setScrollbarHeight(horScrollBarHeight);
        this.setSlider(new ScrollbarSlider(this, getSliderStartX(), getScrollbarCoordY()+1, this.getMaxSliderWidth(), sliderHeight));
    }

    public int calcMaxOffset() { return 0; } //TODO: implement for panes

    public void slide() { } //TODO: implement for panes

    public void draw() { }  //TODO: implement for panes

    /**
     * Used to calculate the maximum width the slider of this scrollbar can have,
     * based on the total width of the scrollbar and the buffer.
     * @return the maximum slider (should always be positive).
     */
    public int getMaxSliderWidth() { return Math.max(this.getScrollbarWidth() - this.getBuffer()*2, 0); }

    // Defines the possible slide room (most left and right x-coords).

    /**
     * Used to get the starting x-coordinate of the slider. Can also be
     * used as the left boundary which the slider cannot cross.
     * @return the most left valid x-coordinate of the slider.
     */
    public int getSliderStartX() { return this.getScrollbarCoordX() + getBuffer(); }


    /**
     * Used to get the right boundary which the slider cannot cross.
     * @return the most right valid x-coordinate of the slider.
     */
    public int getSliderEndX() { return this.getSliderStartX() + this.getMaxSliderWidth(); }
}