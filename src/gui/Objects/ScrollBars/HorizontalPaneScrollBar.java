package gui.Objects.ScrollBars;

import gui.DefaultScreen.ChildPane;

import java.awt.*;

// Horizontal
public class HorizontalPaneScrollBar extends HorizontalScrollBar {

    // The inputField is the element the scrollbar is attached to.
    private ChildPane pane;

    /**
     * Constructor for Scrollbar
     * @param pane the ChildPane that will use the scrollbar
     */
    public HorizontalPaneScrollBar(ChildPane pane) {
        this.pane = pane;

        this.setBoundaries();
        setSlider(new ScrollbarSlider(this, 0,0,0,0));
        updateDimensions();
    }

    // Slider has to at the bottom of the field.
    public int getSliderStartY() { return this.getPane().y + this.getPane().height+1 - getScrollbarHeight(); }

    public int getContentWidth() {
        return this.getPane().getContentWidth();
    }

    public int getOffset() {
        return this.getPane().getXOffset();
    }

    public void setOffset(int offset) {
        this.getPane().setXOffset(offset);
    }

    public int getAvailableWidth() {
        return this.getPane().width  - 15;
    }

    /**
     * Sets scrollbar properties using the input field dimensions.
     * Needed for when user resizes the window.
     * @post The width of the total scrollbar = the width of the pane.
     * @post The x- and y-coordinate of the scrollbar are set correctly (bottom left corner).
     */
    public void setBoundaries() {
        setScrollbarWidth(getAvailableWidth());

        setScrollbarCoordX(this.getPane().x);
        setScrollBarCoordY(this.getPane().y + this.getPane().height - getScrollbarHeight());
    }

    /**
     * @return the inputfield of the scrollbar
     */
    private ChildPane getPane() {
        return this.pane;
    }

}
