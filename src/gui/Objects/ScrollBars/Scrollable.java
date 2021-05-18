package gui.Objects.ScrollBars;

public interface Scrollable {

    /**
     * @return return x-coordinate of this scrollable
     */
    int getX();

    /**
     * @return return y-coordinate of this scrollable
     */
    int getY();

    /**
     * @return return height of this scrollable
     */
    int getHeight();

    /**
     * @return return width of this scrollable
     */
    int getWidth();


    int getXOffset();
    void setXOffset(int offset);

    int getYOffset();
    void setYOffset(int offset);


    /**
     * @return the width of the screen or input box
     */
    int getAvailableWidth();
    int getAvailableHeight();


    /**
     * @return the full width of the content in the screen or input box
     */

    int getContentWidth();
    int getContentHeight();

}
