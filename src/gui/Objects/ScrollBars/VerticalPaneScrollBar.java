package gui.Objects.ScrollBars;

import gui.DefaultScreen.ChildPane;

import java.awt.*;

// Vertical
public class VerticalPaneScrollBar extends VerticalScrollBar {

    private ChildPane pane;


    /**
     * Constructor for Scrollbar
     * @param pane the ChildPane that will use the scrollbar
     */
    public VerticalPaneScrollBar(ChildPane pane) {
        this.pane = pane;

        this.setBoundaries();
        setSlider(new ScrollbarSlider(this, 0,0,0,0));
    }

    public void updateDimensions() {
        this.setBoundaries();
        getSlider().coordX = getSliderStartX();
        getSlider().coordY = getSliderStart();
        getSlider().width = getSliderWidth();
        getSlider().height = getMaxSliderHeight();
    }

    // Slider has to at the bottom of the field.
    private int getSliderStartX() { return this.getPane().x + this.getPane().width+1 - getScrollbarWidth(); }


    public void draw(Graphics g) {
        // Possible resize of the screen.
        setBoundaries();

        // Update the width of the slider.
        getSlider().height = calculateSliderHeight();

        // Reposition the slider so it matches the visible text.
        getSlider().coordY = calculateSliderY();

        // Scrollbar outline
        g.setColor(Color.GRAY);
        g.fillRect(getScrollbarCoordX()-1, getScrollbarCoordY(), getScrollbarWidth()+2, getScrollbarHeight());

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
    private int calculateSliderHeight() {
        int availableHeight = this.getPane().height;
        int contentHeight = this.getPane().getContentHeight();
        int maxSliderHeight = this.getMaxSliderHeight();

        // The objects fit inside the pane:
        if (contentHeight < availableHeight) {
            this.getSlider().canSlide = false;
            this.getPane().setYOffset(0); // No offset.
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
        return getSliderStart();
//        int offset = this.getPane().getXOffset();
//
//        // Moves the slider according to what text is displayed (automatic updating for KeyEvents).
//        if (offset == 0) return getSliderStartX();
//        else if (offset == calcMaxOffset()) return getSliderEndX() - getSlider().width;
//        else return getSliderStartX() + (offset*(-1) * this.getPane().width / this.getPane().getContentWidth());
    }

    /**
     * Calculates the maximum valid offset based on the length of the given guiObjects.
     * @return the maximum amount of pixels the objects can be moved.
     */
    public int calcMaxOffset() {
        return 0;
        //return getSliderStartX() - (Math.abs(this.getPane().getContentWidth() - getMaxSliderWidth()+10));
    }

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

    private double rest = 0;

    public void slide(int sliderMovement) {
//        double rel = ((double) this.getPane().getContentWidth())            // rel = 1.8            1.9
//                / ((double) getMaxSliderWidth());
//
//        // (int vs double mest) zorgt ervoor dat de scrollbar beetje nauwkeuriger is
//        rest += rel - Math.floor(rel);                                      // rest = 0.8           1.7
//        double usableRest = Math.floor(rest);                               // usableRest = 0       1.0
//        rest -= usableRest;                                                 // rest = 0.8           0.7
//        rel = Math.floor(rel) + usableRest;                                 // rel = 1.0 + 0 = 1    1.0 + 1.0 = 2
//
//        int relMovement = sliderMovement * (int) rel;
//
//        // Swiped
//        if(relMovement != 0) this.getPane().setXOffset(this.getPane().getXOffset() + relMovement);
//
//        // Max left offset
//        if (getSlider().coordX == getSliderStartX() || this.getPane().getXOffset() > 0) this.getPane().setXOffset(0);
//        // Max right offset
//        else if (getSlider().coordX + getSlider().width >= getSliderEndX())
//            this.getPane().setXOffset(this.calcMaxOffset());

    }

    /**
     * Sets scrollbar properties using the input field dimensions.
     * Needed for when user resizes the window.
     * @post The height of the total scrollbar = the height of the InputField.
     * @post The x- and y-coordinate of the scrollbar are set correctly (top right corner).
     */
    private void setBoundaries() {
        setScrollbarHeight(this.getPane().height - 15);

        setScrollbarCoordX(this.getPane().x + this.getPane().width - getScrollbarWidth());
        setScrollBarCoordY(this.getPane().y);
    }


    /**
     * @return the inputfield of the scrollbar
     */
    private ChildPane getPane() {
        return this.pane;
    }

}
