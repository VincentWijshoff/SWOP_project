package gui.Objects.ScrollBars;

import java.awt.*;

public class VerticalScrollBar extends ScrollBar {

    //width of scrollbar
    private static final int verScrollBarWidth = 15;
    //width of slider
    private static final int sliderWidth = 13;

    /**
     * Constructor of the HorizontalScrollBar. Sets the height to a constant,
     * because this should never change.
     */
    public VerticalScrollBar(Scrollable scrollable) {
        super(scrollable);
        setScrollbarWidth(verScrollBarWidth);

        this.setBoundaries();
        setSlider(new ScrollbarSlider(this, 0,0,0,0));
        updateDimensions();
    }

    /**
     * Calculate the dimensions of this scrollbar and its slider
     */
    public void updateDimensions() {
        this.setBoundaries();
        getSlider().coordX = getSliderStartX();
        getSlider().coordY = getSliderStartY();

        getSlider().width = getSliderWidth();
        getSlider().height = getMaxSliderHeight();
    }

    /**
     * Used to get the starting y-coordinate of the slider. Can also be
     * used as the down most boundary which the slider cannot cross.
     * @return the most down valid y-coordinate of the slider.
     */
    public int getSliderStartY() { return this.getScrollbarCoordY() + getBuffer(); }

    /**
     * Used to get the x-coordinate of the slider.
     * @return the left most valid x-coordinate of the slider.
     */
    public int getSliderStartX() {
        return scrollable.getX() + scrollable.getAvailableWidth() - 1;
    }

    /**
     * Used to calculate the maximum height the slider of this scrollbar can have,
     * based on the total height of the scrollbar and the buffer.
     * @return the maximum slider height (should always be positive).
     */
    public int getMaxSliderHeight() { return Math.max(this.getScrollbarHeight() - getBuffer()*2, 0); }

    /**
     * @return the width of the slider
     */
    public int getSliderWidth() { return sliderWidth; }

    /**
     * Handle a mouse event on this vertical scrollbar
     * @param id The id of the mouse event
     * @param x The x coordinate of the mouse event
     * @param y The y coordinate of the mouse event
     * @param clickCount The click count of the mouse event
     */
    public void handleMouseEvent(int id, int x, int y, int clickCount) {
        if (getSlider().isOnSlider(x, y) || getSlider().isSliding) {
            int sliderYInit = getSlider().coordY;
            getSlider().handleMouseEvent(id, x, y, clickCount);

            // Slide the difference between the old and new x:
            this.slide(sliderYInit - getSlider().coordY);
        } else {
            System.out.println("Clicked next to scrollbar slider!");
        }
    }

    /**
     * Calculates the maximum valid offset based on the heights of the contents of the scrollable.
     * @return the maximum amount of pixels the objects can be moved.
     */
    public int calcMaxOffset() {
        return (-1) * (Math.abs(getContentHeight() - this.getScrollbarHeight() + 10));
    }

    /**
     * @return the height of the content of the scrollable
     */
    public int getContentHeight() {return scrollable.getContentHeight();}

    /**
     * @return the yOffset of the scrollable
     */
    public int getOffset() {return scrollable.getYOffset();}

    /**
     * @param offset the new YOffset of the scrollable
     */
    public void setOffset(int offset) {scrollable.setYOffset(offset);}

    /**
     * @return the width of the scrollable
     */
    public int getAvailableHeight() {return scrollable.getAvailableHeight();}

    /**
     * Used to get the top boundary which the slider cannot cross.
     * @return the most top valid Y-coordinate of the slider.
     */
    public int getSliderEnd() {
        return this.getSliderStartY() + this.getMaxSliderHeight();
    }

    /**
     * Slide this scrollbar
     * @param sliderMovement how much to move the scrollbar
     */
    public void slide(int sliderMovement) {
        if (sliderMovement == 0) return;

        double rel = ((double) getContentHeight())            // rel = 1.8            1.9
                / ((double) getMaxSliderHeight());

        double relMovement = sliderMovement * rel;
        //Swiped
        setOffset(getOffset() + (int) relMovement);

        // Max left offset
        if (/*getSlider().coordX == getSliderStart() ||*/ getOffset() > 0) setOffset(0);
            // Max right offset
        else if (getSlider().coordY + getSlider().height >= getSliderEnd())
            setOffset(this.calcMaxOffset());
    }

    /**
     * draw the scrollbar
     * @param g The graphics needed to draw the scrollbar
     * @param paneOffsets offsets to apply
     */
    public void draw(Graphics g, int... paneOffsets) {
        //int xOffset = paneOffsets.length == 2 ? paneOffsets[0] : 0;
        int yOffset = paneOffsets.length == 2 ? paneOffsets[1] : 0;

        // Possible resize of the screen.
        updateDimensions();

        // Update the height of the slider.
        getSlider().height = calculateSliderHeight();

        // Reposition the slider so it matches the visible text.
        getSlider().coordY = calculateSliderY();

        // Scrollbar outline
        g.setColor(Color.GRAY);

        g.fillRect(getScrollbarCoordX()-2, getScrollbarCoordY() + yOffset, getScrollbarWidth(), getScrollbarHeight()+2);

        // Scrollbar slider
        getSlider().draw(g, 0, yOffset);
    }

    /**
     * @return the start y coordinate of the slider
     */
    @Override
    public int getSliderStart() {
        return getSliderStartY();
    }

    /**
     * The height of the scrollbarSlider is dependant of the height of the scrollable and the height of its content.
     * @return the height of the slider
     * @post    if the content fits inside the scrollable, the slider will have its max size.
     *          if the content doesn't fit, the slider will have a height such that the ratio between slider and
     *          scrollbar is equal to the ratio of the scrollable height and of its content height.
     */
    public int calculateSliderHeight() {
        int availableHeight= getAvailableHeight() - ScrollBar.getBuffer()*2;
        int contentHeight = getContentHeight();
        int maxSliderHeight = this.getMaxSliderHeight();

        // The objects fit inside the pane:
        if (contentHeight < availableHeight) {
            this.getSlider().canSlide = false;
            setOffset(0); // No offset.
            return maxSliderHeight;
        }
        // The objects don't fit inside:
        else {
            this.getSlider().canSlide = true;
            return (maxSliderHeight*maxSliderHeight)/contentHeight;
        }
    }

    /**
     * Calculates the position of the scrollbar slider. Automatically updates
     * when the user presses keys.
     * @return the new y-coordinate of the slider.
     */
    public int calculateSliderY() {
        int offset = getOffset();

        // Moves the slider according to what text is displayed (automatic updating for KeyEvents).
        if (offset == 0 /*|| getSlider().width == getMaxSliderWidth()*/) return getSliderStartY();
        else if (offset == calcMaxOffset()) return getSliderEnd() - getSlider().height;
        else return getSliderStartY() + (offset*(-1) * getAvailableHeight() / getContentHeight());
    }

    /**
     * Sets scrollbar properties using the dimensions of the scrollable.
     * Needed when user resizes the window.
     * @post The height of the total scrollbar = the (available) height of the scrollable.
     * @post The x- and y-coordinate of the scrollbar are set correctly (bottom left corner).
     */
    public void setBoundaries() {
        setScrollbarHeight(getAvailableHeight());

        setScrollbarCoordX(getX() + scrollable.getAvailableWidth());
        setScrollBarCoordY(getY());
    }

}
