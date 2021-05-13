package gui.Objects;

import gui.DefaultScreen.AddressBar;

import java.awt.*;

// Horizontal
public class Scrollbar {

    // The 'scrollbar' is the gray area in which the 'scrollbar slider' can move.
    private int scrollbarWidth;
    private static final int scrollbarHeight = 15;
    private int scrollBarCoordX;
    private int scrollBarCoordY;

    // The inputField is the element the scrollbar is attached to.
    private GUIInput inputField;

    // The 'scrollbar slider' is the actual bar the user slides with.
    private ScrollbarSlider slider;
    private static int sliderHeight = 13;
    private int maxSliderWidth = 0; // Depends on the width of the inputField.
    int startCoordX, endCoordX;


    /**
     * Constructor for Scrollbar
     * @param input the input field that will use the scrollbar
     */
    public Scrollbar(GUIInput input) {
        this.inputField = input;
        this.scrollBarCoordX = input.coordX;
        this.scrollBarCoordY = input.coordY+this.getInputField().height;

        this.setBoundaries();
        this.slider = new ScrollbarSlider(this, startCoordX,
                this.getInputField().coordY+this.getInputField().height+1, this.maxSliderWidth, sliderHeight);
    }

    public void draw(Graphics g) {
        // Update the widths (they should change when the user resizes the window).
        setBoundaries();
        int initWidth = this.slider.width;
        this.slider.width = caculateSliderWidth(g);

        // Reposition the slider if it has to grow to the left (move to left and grow instead of growing outside of border)
        if ((this.slider.coordX + this.slider.width) > this.endCoordX) {
            this.slider.coordX = this.endCoordX - this.slider.width;
        } else if(this.slider.width < initWidth && initWidth > 0){
            this.slider.coordX += initWidth-this.slider.width;
        } else if(this.slider.width > initWidth && initWidth > 0){
            this.slider.coordX += this.slider.width-initWidth;
        }

        // Scrollbar outline
        g.setColor(Color.GRAY);
        g.fillRect(this.getInputField().coordX, this.getInputField().coordY+this.getInputField().height-1, this.scrollbarWidth, scrollbarHeight+2);

        // Scrollbar slider
        slider.draw(g);
    }


    /**
     * The width of the scrollbar slider is dependant on the width of the input field and the content of the input field.
     * @param g  the java drawing graphics
     * @return the width of the slider
     * post    if the text fits inside the input field, the slider will have its max size.
     *          if the text doesn't fit, the slider will have a width so that the ratio between slider and scrollbar is
     *          equal to the ratio of the inputfield-width and inputfield-text-width.
     */
    private int caculateSliderWidth(Graphics g) {
        String text = this.getInputField().getText();
        int textWidth = (int) g.getFontMetrics().getStringBounds(text, g).getWidth();
        int inputFieldWidth = this.getInputField().width;

        // The text fits inside the inputfield:
        if (textWidth < inputFieldWidth) {
            this.slider.canSlide = false;
            return this.maxSliderWidth;
        }
        // The text doesn't fit inside:
        else {
            this.slider.canSlide = true;
            return (this.maxSliderWidth*this.maxSliderWidth)/textWidth;
        }
    }

    /**
     * Check if the given coordinates collide with the position of this object
     * @param x the x coordinate
     * @param y the y coordinate
     * @return  true if the given position collides with the position of the object
     */
    public boolean isOnScrollBar(int X, int Y) {
        return (X >= this.scrollBarCoordX &&
                X <= this.scrollBarCoordX + this.scrollbarWidth &&
                Y >= this.scrollBarCoordY &&
                Y <= this.scrollBarCoordY + this.scrollbarHeight);
    }



    public void handleMouseEvent(int id, int x, int y, int clickCount) {
        if (this.slider.isOnSlider(x, y) || this.slider.isSliding) {
            int sliderXInit = this.slider.coordX;
            this.slider.handleMouseEvent(id, x, y, clickCount);
            slide(sliderXInit - this.slider.coordX);

        } else {
            System.out.println("Clicked next to scrollbar slider!");
        }
    }

    private double rest = 0;
    private void slide(int sliderMovement) {
        FontMetrics fm = this.inputField.fontMetricsHandler.getFontMetrics();

        double rel = ((double) fm.stringWidth(this.inputField.getText()))   // rel = 1.8            1.9
                / ((double) this.maxSliderWidth);

        // Dit is echt zooi (int vs double mest), zorgt ervoor dat de scrollbar beetje nauwkeuriger is
        rest += rel - Math.floor(rel);                                      // rest = 0.8           1.7
        double usableRest = Math.floor(rest);                               // usableRest = 0       1.0
        rest -= usableRest;                                                 // rest = 0.8           0.7
        rel = Math.floor(rel) + usableRest;                                 // rel = 1.0 + 0 = 1    1.0 + 1.0 = 2

        int relMovement = sliderMovement * (int) rel;
        //System.out.println("relMovement: " + relMovement + " = " + sliderMovement + "*" + rel);

        // Swiped
        if(relMovement != 0) this.getInputField().textPos += relMovement;

        // Max left offset
        if (this.slider.coordX == this.startCoordX || this.getInputField().textPos > 0) this.getInputField().textPos = 0;
        // Max right offset
        else if (this.slider.coordX + this.slider.width >= this.endCoordX)
            this.getInputField().textPos = this.startCoordX - (fm.stringWidth(this.inputField.getText()) - this.maxSliderWidth+10);

        //System.out.println("textPos: " + this.getInputField().textPos + ", rest: " + rest);
    }

    /**
     * Sets scrollbar properties using the input field dimensions
     */
    public void setBoundaries() {
        this.scrollbarWidth = this.getInputField().width;
        this.maxSliderWidth = this.getInputField().width-10;
        if (this.maxSliderWidth < 0) this.maxSliderWidth = 0;

        // Coords used to limit the movement of the scrollbar slider
        this.startCoordX = this.getInputField().coordX+5;;
        this.endCoordX = this.startCoordX + this.maxSliderWidth;
    }


    /**
     * @return the inputfield of the scrollbar
     */
    private GUIInput getInputField() {
        return this.inputField;
    }


    /**
     * @return the height of the scrollbar
     */
    public int getHeight() {
        return this.scrollbarHeight;
    }
}
