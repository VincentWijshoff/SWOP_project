package gui.Objects.ScrollBars;

import java.awt.*;

public abstract class HorizontalScrollBar extends ScrollBar {

    private static final int horScrollBarHeight = 15;
    private static final int sliderHeight = 13;

    public static int getSliderHeight() { return sliderHeight; }

    /**
     * Constructor of the HorizontalScrollBar. Sets the height to a constant,
     * because this should never change.
     */
    public HorizontalScrollBar() {
        setScrollbarHeight(horScrollBarHeight);
        //this.setSlider(new ScrollbarSlider(this, getSliderStartX(), getScrollbarCoordY()+1, this.getMaxSliderWidth(), sliderHeight));
    }


    public abstract void slide(int movement);

    public abstract void draw(Graphics g);


    /**
     * Used to calculate the maximum width the slider of this scrollbar can have,
     * based on the total width of the scrollbar and the buffer.
     * @return the maximum slider width (should always be positive).
     */
    public int getMaxSliderWidth() { return Math.max(this.getScrollbarWidth() - this.getBuffer()*2, 0); }

    /**
     * Used to get the starting x-coordinate of the slider. Can also be
     * used as the left boundary which the slider cannot cross.
     * @return the most left valid x-coordinate of the slider.
     */
    public int getSliderStart() { return this.getScrollbarCoordX() + getBuffer(); }


    /**
     * Used to get the right boundary which the slider cannot cross.
     * @return the most right valid x-coordinate of the slider.
     */
    public int getSliderEnd() { return this.getSliderStart() + this.getMaxSliderWidth(); }
}