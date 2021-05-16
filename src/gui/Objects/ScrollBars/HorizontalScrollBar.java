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

    public abstract int calcMaxOffset();

    public abstract int getContentWidth();

    public abstract int getOffset();

    public abstract void setOffset(int offset);

    public abstract int getAvailableWidth();

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

    public abstract void setBoundaries();

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

    public abstract int getSliderStartY();
}