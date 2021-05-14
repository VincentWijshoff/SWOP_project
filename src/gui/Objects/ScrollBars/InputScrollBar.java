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
        setSlider(new ScrollbarSlider(this, getSliderStartX(), getSliderStartY(), getMaxSliderWidth(), getSliderHeight()));
    }

    // Slider has to be below the InputField.
    private int getSliderStartY() { return this.getInputField().coordY+this.getInputField().height+1; }


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
        int offset = this.getInputField().getOffset();

        // Moves the slider according to what text is displayed (automatic updating for KeyEvents).
        if (offset == 0) return getSliderStartX();
        else if (offset == calcMaxOffset(this.getInputField().getText())) return getSliderEndX() - getSlider().width;
        else return getSliderStartX() + (offset*(-1) * this.getInputField().width / this.getInputField().getStringWidth(this.getInputField().getText()));
    }

    /**
     * Calculates the maximum valid offset based on the length of the given string.
     * @param text the text
     * @return the maximum amount of pixels the string can be moved
     */
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
