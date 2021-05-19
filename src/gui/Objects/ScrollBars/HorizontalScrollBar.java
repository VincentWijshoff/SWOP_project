package gui.Objects.ScrollBars;

import java.awt.*;

public class HorizontalScrollBar extends ScrollBar {

    //height of the scrollbar
    private static final int horScrollBarHeight = 15;
    //height of the slider
    private static final int sliderHeight = 13;

    /**
     * @return the height of this scrollbar's slider
     */
    public static int getSliderHeight() { return sliderHeight; }

    /**
     * Constructor of the HorizontalScrollBar. Sets the height to a constant,
     * because this should never change.
     */
    public HorizontalScrollBar(Scrollable scrollable) {
        super(scrollable);
        setScrollbarHeight(horScrollBarHeight);

        this.setBoundaries();
        setSlider(new ScrollbarSlider(this, 0,0,0,0));
        updateDimensions();
    }

    /**
     * Calculate the dimensions of this scrollbar and its slider
     */
    public void updateDimensions() {
        this.setBoundaries();
        getSlider().coordX = getSliderStart();
        getSlider().coordY = getSliderStartY();
        getSlider().width = getMaxSliderWidth();
        getSlider().height = getSliderHeight();
    }

    /**
     * Handle a mouse event on this horizontal scrollbar
     * @param id The id of the mouse event
     * @param x The x coordinate of the mouse event
     * @param y The y coordinate of the mouse event
     * @param clickCount The click count of the mouse event
     */
    public void handleMouseEvent(int id, int x, int y, int clickCount) {
        if (getSlider().isOnSlider(x, y) || getSlider().isSliding) {
            int sliderXInit = getSlider().coordX;
            getSlider().handleMouseEvent(id, x, y, clickCount);

            // Slide the difference between the old and new x:
            this.slide(sliderXInit - getSlider().coordX);

        } else {
            System.out.println("Clicked next to scrollbar slider!");
        }
    }

    /**
     * Calculates the maximum valid offset based on the width of the contents of the scrollable.
     * @return the maximum amount of pixels the objects can be moved.
     */
    public int calcMaxOffset() {
        return (-1) * (Math.abs(getContentWidth() - this.getScrollbarWidth() + 10));
    }

    /**
     * @return the full width of the content in the scrollable
     */
    public int getContentWidth() {return scrollable.getContentWidth();}

    /**
     * @return the xOffset of the scrollable
     */
    public int getOffset() {return scrollable.getXOffset();}

    /**
     * @param offset the new xOffset of the scrollable
     */
    public void setOffset(int offset) {scrollable.setXOffset(offset);}

    /**
     * @return the width of the scrollable
     */
    public int getAvailableWidth() {return scrollable.getAvailableWidth();}

    /**
     * Slide this scrollbar
     * @param sliderMovement how much to move the scrollbar
     */
    public void slide(int sliderMovement) {
        if (sliderMovement == 0) return;

        double rel = ((double) getContentWidth())            // rel = 1.8            1.9
                / ((double) getMaxSliderWidth());

        double relMovement = sliderMovement * rel;
        //Swiped
        setOffset(getOffset() + (int) relMovement);

        // Max left offset
        if (/*getSlider().coordX == getSliderStart() ||*/ getOffset() > 0) setOffset(0);
            // Max right offset
        else if (getSlider().coordX + getSlider().width >= getSliderEnd())
            setOffset(this.calcMaxOffset());
    }

    /**
     * draw the scrollbar
     * @param g The graphics needed to draw the scrollbar
     * @param paneOffsets offsets to apply
     */
    public void draw(Graphics g, int... paneOffsets) {
        Color oldColor = g.getColor();
        int xOffset = paneOffsets.length == 2 ? paneOffsets[0] : 0;
        int yOffset = paneOffsets.length == 2 ? paneOffsets[1] : 0;

        // Possible resize of the screen.
        updateDimensions();

        // Update the width of the slider.
        getSlider().width = calculateSliderWidth();

        // Reposition the slider so it matches the visible text.
        getSlider().coordX = calculateSliderX();

        // Scrollbar outline
        g.setColor(Color.GRAY);
        g.fillRect(getScrollbarCoordX() + xOffset, getScrollbarCoordY()-1 + yOffset, getScrollbarWidth(), getScrollbarHeight()+2);
        g.setColor(oldColor);

        // Scrollbar slider
        getSlider().draw(g, xOffset, 0);
    }

    /**
     * The width of the scrollbarSlider is dependant of the width of the scrollable and the width of its content.
     * @return the width of the slider
     * @post    if the content fits inside the scrollable, the slider will have its max size.
     *          if the content doesn't fit, the slider will have a width such that the ratio between slider and
     *          scrollbar is equal to the ratio of the scrollable width and of its content width.
     */
    public int calculateSliderWidth() {
        int availableWidth = getAvailableWidth() - ScrollBar.getBuffer()*2;
        int contentWidth = getContentWidth();
        int maxSliderWidth = this.getMaxSliderWidth();

        // The objects fit inside the pane:
        if (contentWidth < availableWidth) {
            this.getSlider().canSlide = false;
            setOffset(0); // No offset.
            return maxSliderWidth;
        }
        // The objects don't fit inside:
        else {
            this.getSlider().canSlide = true;
            return (maxSliderWidth*maxSliderWidth)/contentWidth;
        }
    }

    /**
     * Calculates the position of the scrollbar slider. Automatically updates when the user presses keys.
     * @return the new x-coordinate of the slider.
     */
    public int calculateSliderX() {
        int offset = getOffset();

        // Moves the slider according to what text is displayed (automatic updating for KeyEvents).
        if (offset == 0 /*|| getSlider().width == getMaxSliderWidth()*/) return getSliderStart();
        else if (offset == calcMaxOffset()) return getSliderEnd() - getSlider().width;
        else return getSliderStart() + (offset*(-1) * getAvailableWidth() / getContentWidth());
    }

    /**
     * Sets scrollbar properties using the dimensions of the scrollable.
     * Needed when user resizes the window.
     * @post The width of the total scrollbar = the (available) width of the scrollable.
     * @post The x- and y-coordinate of the scrollbar are set correctly (bottom left corner).
     */
    public void setBoundaries() {
        setScrollbarWidth(getAvailableWidth());

        setScrollbarCoordX(getX());
        setScrollBarCoordY(getY() + getHeight() - getScrollbarHeight());
    }

    /**
     * Used to calculate the maximum width the slider of this scrollbar can have,
     * based on the total width of the scrollbar and the buffer.
     * @return the maximum slider width (should always be positive).
     */
    public int getMaxSliderWidth() { return Math.max(this.getScrollbarWidth() - getBuffer()*2, 0); }

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

    /**
     * Calculate where slider of this scrollbar should be
     * @return y-coordinate of the slider
     */
    public int getSliderStartY() { return getScrollbarCoordY() + 1; }
}