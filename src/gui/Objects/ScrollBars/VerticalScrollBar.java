package gui.Objects.ScrollBars;

import java.awt.*;

public abstract class VerticalScrollBar extends ScrollBar {

    private static final int verScrollBarWidth = 15;
    private static final int sliderWidth = 13;

    public static int getSliderWidth() { return sliderWidth; }

    /**
     * Constructor of the HorizontalScrollBar. Sets the height to a constant,
     * because this should never change.
     */
    public VerticalScrollBar() {
        setScrollbarWidth(verScrollBarWidth);
        //this.setSlider(new ScrollbarSlider(this, getScrollbarCoordX()+1, getSliderStart(), this.getMaxSliderHeight(), sliderWidth));
    }

    public abstract void slide(int movement);

    public abstract void draw(Graphics g, int... paneOffsets);

    /**
     * Used to calculate the maximum height the slider of this scrollbar can have,
     * based on the total height of the scrollbar and the buffer.
     * @return the maximum slider height (should always be positive).
     */
    public int getMaxSliderHeight() { return Math.max(this.getScrollbarHeight() - this.getBuffer()*2, 0); }

    /**
     * Used to get the starting y-coordinate of the slider. Can also be
     * used as the top boundary which the slider cannot cross.
     * @return the most top valid y-coordinate of the slider.
     */
    public int getSliderStart() { return this.getScrollbarCoordY() + getBuffer(); }


    /**
     * Used to get the bottom boundary which the slider cannot cross.
     * @return the most bottom valid y-coordinate of the slider.
     */
    public int getSliderEnd() { return this.getSliderStart() + this.getMaxSliderHeight(); }
}
