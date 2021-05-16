package gui.Objects.ScrollBars;

import java.awt.*;

public class HorizontalScrollBar extends ScrollBar {

    private static final int horScrollBarHeight = 15;
    private static final int sliderHeight = 13;
    private Scrollable scrollable;

    public static int getSliderHeight() { return sliderHeight; }

    /**
     * Constructor of the HorizontalScrollBar. Sets the height to a constant,
     * because this should never change.
     */
    public HorizontalScrollBar(Scrollable scrollable) {
        setScrollbarHeight(horScrollBarHeight);

        this.scrollable = scrollable;

        this.setBoundaries();
        setSlider(new ScrollbarSlider(this, 0,0,0,0));
        updateDimensions();
    }

    public void updateDimensions() {
        this.setBoundaries();
        getSlider().coordX = getSliderStart();
        getSlider().coordY = getSliderStartY();
        getSlider().width = getMaxSliderWidth();
        getSlider().height = getSliderHeight();
    }

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
     * Calculates the maximum valid offset based on the width of the given guiObjects.
     * @return the maximum amount of pixels the objects can be moved.
     */
    public int calcMaxOffset() {
        return getSliderStart() - (Math.abs(getContentWidth() - getMaxSliderWidth()+10));
    }

    public int getContentWidth() {return scrollable.getContentWidth();}

    public int getOffset() {return scrollable.getOffset();}

    public void setOffset(int offset) {scrollable.setOffset(offset);}

    public int getAvailableWidth() {return scrollable.getAvailableWidth();}

    public void slide(int sliderMovement) {
        if (sliderMovement == 0) return;

        double rel = ((double) getContentWidth())            // rel = 1.8            1.9
                / ((double) getMaxSliderWidth());

        double relMovement = sliderMovement * rel;

        //Swiped
        setOffset(getOffset() + (int) relMovement);

        // Max left offset
        if (getSlider().coordX == getSliderStart() || getOffset() > 0) setOffset(0);
            // Max right offset
        else if (getSlider().coordX + getSlider().width >= getSliderEnd())
            setOffset(this.calcMaxOffset());
    }

    public void draw(Graphics g) {
        // Possible resize of the screen.
        setBoundaries();
        updateDimensions();

        // Update the width of the slider.
        getSlider().width = calculateSliderWidth();

        // Reposition the slider so it matches the visible text.
        getSlider().coordX = calculateSliderX();

        // Scrollbar outline
        g.setColor(Color.GRAY);
        g.fillRect(getScrollbarCoordX(), getScrollbarCoordY()-1, getScrollbarWidth(), getScrollbarHeight()+2);

        // Scrollbar slider
        getSlider().draw(g);
    }

    /**
     * The width of the scrollbar slider is dependant on the width and content of the input field.
     * @return the width of the slider
     * @post    if the text fits inside the input field, the slider will have its max size.
     *          if the text doesn't fit, the slider will have a width so that the ratio between slider and scrollbar is
     *          equal to the ratio of the inputfield-width and inputfield-text-width.
     */
    public int calculateSliderWidth() {
        int availableWidth = getAvailableWidth();
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
     * Calculates the position of the scrollbar slider. Automatically updates
     * when the user presses keys.
     * @return the new x-coordinate of the slider.
     */
    public int calculateSliderX() {
        int offset = getOffset();

        // Moves the slider according to what text is displayed (automatic updating for KeyEvents).
        if (offset == 0) return getSliderStart();
        else if (offset == calcMaxOffset()) return getSliderEnd() - getSlider().width;
        else return getSliderStart() + (offset*(-1) * getAvailableWidth() / getContentWidth());
    }

    public int getX() {return scrollable.getX();}
    public int getY() {return scrollable.getY();}
    public int getHeight() {return scrollable.getHeight();}

    /**
     * Sets scrollbar properties using the input field dimensions.
     * Needed for when user resizes the window.
     * @post The width of the total scrollbar = the width of the pane.
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

    public int getSliderStartY() { return getScrollbarCoordY() + 1; }
}