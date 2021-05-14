package gui.Objects.ScrollBars;

import gui.Objects.GUIInput;

import java.awt.*;

// Horizontal
public class InputScrollBar extends HorizontalScrollBar {

    // The inputField is the element the scrollbar is attached to.
    private GUIInput inputField;


    /**
     * Constructor for Scrollbar
     * @param input the input field that will use the scrollbar
     */
    public InputScrollBar(GUIInput input) {
        this.inputField = input;

        this.setBoundaries();
        setSlider(new ScrollbarSlider(this, getSliderStartX(), getSliderStartY(), getMaxSliderWidth(), sliderHeight));
    }

    private int getSliderStartY() { return this.getInputField().coordY+this.getInputField().height+1; }


    public void draw(Graphics g) {
        // Update the widths (they should change when the user resizes the window).
        setBoundaries();
        getSlider().width = caculateSliderWidth();

        // Reposition the slider if it has to grow to the left (move to left and grow instead of growing outside of border)
        getSlider().coordX = calculateSliderX();


        // Scrollbar outline
        g.setColor(Color.GRAY);
        g.fillRect(getScrollbarCoordX(), getScrollbarCoordY()-1, getScrollbarWidth(), getScrollbarHeight()+2);

        // Scrollbar slider
        getSlider().draw(g);
    }


    /**
     * The width of the scrollbar slider is dependant on the width of the input field and the content of the input field.
     * @return the width of the slider
     * post    if the text fits inside the input field, the slider will have its max size.
     *          if the text doesn't fit, the slider will have a width so that the ratio between slider and scrollbar is
     *          equal to the ratio of the inputfield-width and inputfield-text-width.
     */
    private int caculateSliderWidth() {
        int inputFieldWidth = this.getInputField().width - this.getBuffer()*2;
        int textWidth = this.getInputField().getStringWidth(this.getInputField().getText());
        int maxSliderWidth = this.getMaxSliderWidth();

        // The text fits inside the inputfield:
        if (textWidth < inputFieldWidth) {
            this.getSlider().canSlide = false;
            this.getInputField().setOffset(0);
            return maxSliderWidth;
        }
        // The text doesn't fit inside:
        else {
            this.getSlider().canSlide = true;
            return (maxSliderWidth*maxSliderWidth)/textWidth;
        }
    }

    public int calculateSliderX() {
        int offset = this.getInputField().getOffset();
        if (offset == 0) return getSliderStartX();
        else if (offset == calcMaxOffset(this.getInputField().getText())) return getSliderEndX() - getSlider().width;
        else {

            return getSliderStartX() + (offset*(-1) * this.getInputField().width / this.getInputField().getStringWidth(this.getInputField().getText()));
        }

        /*if ((getSlider().coordX + getSlider().width) > getSliderEndX()) {
            getSlider().coordX = getSliderEndX() - getSlider().width;
        }
        else if (this.getInputField().getOffset() != 0) {
            if(getSlider().width < initWidth) { // Getting smaller
                getSlider().coordX += initWidth - getSlider().width;
            }
            else if (getSlider().width > initWidth) { // Getting bigger

            }
        }
        /*else if(getSlider().width > initWidth && this.getInputField().getOffset() != 0) {
            getSlider().coordX += getSlider().width-initWidth;
        }*/
    }

    public int calcMaxOffset(String text) {
        return getSliderStartX() - (Math.abs(this.getInputField().getStringWidth(text) - getMaxSliderWidth()+10));
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
        FontMetrics fm = this.inputField.getFontMetrics();

        double rel = ((double) fm.stringWidth(this.inputField.getText()))   // rel = 1.8            1.9
                / ((double) getMaxSliderWidth());

        // (int vs double mest) zorgt ervoor dat de scrollbar beetje nauwkeuriger is
        rest += rel - Math.floor(rel);                                      // rest = 0.8           1.7
        double usableRest = Math.floor(rest);                               // usableRest = 0       1.0
        rest -= usableRest;                                                 // rest = 0.8           0.7
        rel = Math.floor(rel) + usableRest;                                 // rel = 1.0 + 0 = 1    1.0 + 1.0 = 2

        int relMovement = sliderMovement * (int) rel;

        // Swiped
        if(relMovement != 0) this.getInputField().setOffset(this.getInputField().getOffset() + relMovement);

        // Max left offset
        if (getSlider().coordX == getSliderStartX() || this.getInputField().getOffset() > 0) this.getInputField().setOffset(0);
        // Max right offset
        else if (getSlider().coordX + getSlider().width >= getSliderEndX())
            this.getInputField().setOffset(this.calcMaxOffset(this.getInputField().getText()));

    }

    /**
     * Sets scrollbar properties using the input field dimensions
     */
    public void setBoundaries() {
        setScrollbarWidth(this.getInputField().width);

        // Coords used to limit the movement of the scrollbar slider
        setScrollbarCoordX(this.getInputField().coordX);
        setScrollBarCoordY(this.getInputField().coordY + this.getInputField().height);
    }


    /**
     * @return the inputfield of the scrollbar
     */
    private GUIInput getInputField() {
        return this.inputField;
    }

}
