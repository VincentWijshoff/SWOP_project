package gui.Objects.ScrollBars;

import java.awt.*;

public class VerticalScrollBar extends ScrollBar {

    private static final int verScrollBarWidth = 15;
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

    public int getSliderStartX() {
        return scrollable.getX() + scrollable.getAvailableWidth() - 1;
    }

    /**
     * Used to calculate the maximum height the slider of this scrollbar can have,
     * based on the total height of the scrollbar and the buffer.
     * @return the maximum slider height (should always be positive).
     */
    public int getMaxSliderHeight() { return Math.max(this.getScrollbarHeight() - getBuffer()*2, 0); }

    public int getSliderWidth() { return sliderWidth; }

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
     * Calculates the maximum valid offset based on the heights of the given guiObjects.
     * @return the maximum amount of pixels the objects can be moved.
     */
    public int calcMaxOffset() {
        return (-1) * (Math.abs(getContentHeight() - this.getScrollbarHeight() + 10));
    }

    public int getContentHeight() {return scrollable.getContentHeight();}

    public int getOffset() {return scrollable.getYOffset();}

    public void setOffset(int offset) {scrollable.setYOffset(offset);}

    public int getAvailableHeight() {return scrollable.getAvailableHeight();}

    /**
     * Used to get the right boundary which the slider cannot cross.
     * @return the most right valid x-coordinate of the slider.
     */
    public int getSliderEnd() {
        return this.getSliderStartY() + this.getMaxSliderHeight();
    }

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

    @Override
    public int getSliderStart() {
        return getSliderStartY();
    }

    /**
     * The height of the scrollbar slider is dependant on the height and content of the pane.
     * @return the height of the slider
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
     * @return the new x-coordinate of the slider.
     */
    public int calculateSliderY() {
        int offset = getOffset();

        // Moves the slider according to what text is displayed (automatic updating for KeyEvents).
        if (offset == 0 /*|| getSlider().width == getMaxSliderWidth()*/) return getSliderStartY();
        else if (offset == calcMaxOffset()) return getSliderEnd() - getSlider().height;
        else return getSliderStartY() + (offset*(-1) * getAvailableHeight() / getContentHeight());
    }

    /**
     * Sets scrollbar properties using the input field dimensions.
     * Needed for when user resizes the window.
     * @post The width of the total scrollbar = the width of the pane.
     * @post The x- and y-coordinate of the scrollbar are set correctly (bottom left corner).
     */
    public void setBoundaries() {
        setScrollbarHeight(getAvailableHeight());

        setScrollbarCoordX(getX() + scrollable.getAvailableWidth());
        setScrollBarCoordY(getY());
    }

}
