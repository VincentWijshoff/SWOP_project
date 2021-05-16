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
        setSlider(new ScrollbarSlider(this, getSliderStart(), getSliderStartY(), getMaxSliderWidth(), getSliderHeight()));
    }

    // Slider has to be below the InputField.
    private int getSliderStartY() { return this.getInputField().coordY+this.getInputField().height+1; }



    /**
     * The width of the scrollbar slider is dependant on the width and content of the input field.
     * @return the width of the slider
     * @post    if the text fits inside the input field, the slider will have its max size.
     *          if the text doesn't fit, the slider will have a width so that the ratio between slider and scrollbar is
     *          equal to the ratio of the inputfield-width and inputfield-text-width.
     */
    public int calculateSliderWidth() {
        int inputFieldWidth = this.getInputField().width - this.getBuffer()*2;
        int textWidth = this.getInputField().getStringWidth(this.getInputField().getText());
        int maxSliderWidth = this.getMaxSliderWidth();

        // The text fits inside the inputfield:
        if (textWidth < inputFieldWidth) {
            this.getSlider().canSlide = false;
            this.getInputField().setOffset(0); // Text have no offset.
            return maxSliderWidth;
        }
        // The text doesn't fit inside:
        else {
            this.getSlider().canSlide = true;
            return (maxSliderWidth*maxSliderWidth)/textWidth;
        }
    }

    /**
     * Calculates the position of the scrollbar slider. Automatically updates
     * when the user presses keys.
     * @return the new x-coordinate of the slider.
     */
    public int calculateSliderX() {
        int offset = this.getInputField().getInputScrollOffset();

        // Moves the slider according to what text is displayed (automatic updating for KeyEvents).
        if (offset == 0) return getSliderStart();
        else if (offset == calcMaxOffset()) return getSliderEnd() - getSlider().width;
        else return getSliderStart() + (offset*(-1) * this.getInputField().width / this.getInputField().getStringWidth(this.getInputField().getText()));
    }

    /**
     * Calculates the maximum valid offset based on the length of the given string.
     * @return the maximum amount of pixels the string can be moved
     */
    public int calcMaxOffset() {
        return getSliderStart() - (Math.abs(getContentWidth() - getMaxSliderWidth()+10));
    }

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

    /**
     * Sets scrollbar properties using the input field dimensions.
     * Needed for when user resizes the window.
     * @post The width of the total scrollbar = the width of the InputField.
     * @post The x- and y-coordinate of the scrollbar = the x- and y-coordinate of the InputField.
     */
    public void setBoundaries() {
        setScrollbarWidth(this.getInputField().width);

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
