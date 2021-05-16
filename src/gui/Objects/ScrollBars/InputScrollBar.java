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
        setSlider(new ScrollbarSlider(this, 0,0,0,0));
        updateDimensions();
    }

    // Slider has to be below the InputField.
    public int getSliderStartY() { return this.getInputField().coordY+this.getInputField().height+1; }


    public int getContentWidth() {
        FontMetrics fm = this.inputField.getFontMetrics();
        return  fm.stringWidth(this.inputField.getText());
    }

    public int getOffset() {
        return this.getInputField().getInputScrollOffset();
    }

    public void setOffset(int offset) {
        this.getInputField().setOffset(offset);
    }

    public int getAvailableWidth() {
        return this.getInputField().width;
    }

    /**
     * Sets scrollbar properties using the input field dimensions.
     * Needed for when user resizes the window.
     * @post The width of the total scrollbar = the width of the InputField.
     * @post The x- and y-coordinate of the scrollbar = the x- and y-coordinate of the InputField.
     */
    public void setBoundaries() {
        setScrollbarWidth(getAvailableWidth() );

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
