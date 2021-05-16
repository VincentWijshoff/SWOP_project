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
    }

    public void updateDimensions() {
        this.setBoundaries();
        getSlider().coordX = getSliderStart();
        getSlider().coordY = getSliderStartY();
        getSlider().width = getMaxSliderWidth();
        getSlider().height = getSliderHeight();
    }

    // Slider has to at the bottom of the field.
    private int getSliderStartY() { return this.getPane().y + this.getPane().height+1 - getScrollbarHeight(); }


    public void draw(Graphics g) {
        // Possible resize of the screen.
        setBoundaries();

        // Update the width of the slider.
        getSlider().width = caculateSliderWidth();

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
    private int caculateSliderWidth() {
        int availableWidth = this.getPane().width;
        int contentWidth = this.getPane().getContentWidth();
        int maxSliderWidth = this.getMaxSliderWidth();

        // The objects fit inside the pane:
        if (contentWidth < availableWidth) {
            this.getSlider().canSlide = false;
            this.getPane().setXOffset(0); // No offset.
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
        int offset = this.getPane().getXOffset();

        // Moves the slider according to what text is displayed (automatic updating for KeyEvents).
        if (offset == 0) return getSliderStart();
        else if (offset == calcMaxOffset()) return getSliderEnd() - getSlider().width;
        else {
            double x = offset*(-1) * ((double) this.getPane().width / (double) this.getPane().getContentWidth());
            return getSliderStart() + (int) x;
        }
    }

    /**
     * Calculates the maximum valid offset based on the width of the given guiObjects.
     * @return the maximum amount of pixels the objects can be moved.
     */
    public int calcMaxOffset() {
        return getSliderStart() - (Math.abs(this.getPane().getContentWidth() - getMaxSliderWidth()+10));
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

    private double rest = 0;

    public void slide(int sliderMovement) {
        if (sliderMovement == 0) return;

        System.out.println(sliderMovement);
        double rel = ((double) this.getPane().getContentWidth())            // rel = 1.8            1.9
                / ((double) getMaxSliderWidth());

        double relMovement = sliderMovement * rel;

        this.getPane().setXOffset(this.getPane().getXOffset() + (int) relMovement);

        // Max left offset
        if (getSlider().coordX == getSliderStart() || this.getPane().getXOffset() > 0) this.getPane().setXOffset(0);
        // Max right offset
        else if (getSlider().coordX + getSlider().width >= getSliderEnd())
            this.getPane().setXOffset(this.calcMaxOffset());

    }

    /**
     * Sets scrollbar properties using the input field dimensions.
     * Needed for when user resizes the window.
     * @post The width of the total scrollbar = the width of the pane.
     * @post The x- and y-coordinate of the scrollbar are set correctly (bottom left corner).
     */
    private void setBoundaries() {
        setScrollbarWidth(this.getPane().width-15);

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
