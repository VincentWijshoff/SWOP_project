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


    /**
     * @return the current offset (applied by scrolling) of this scrollable
     */
    int getOffset();

    /**
     * Set an offset to this scrollable
     * @param offset
     */
    void setOffset(int offset);

    /**
     * @return the width of the screen or input box
     */
    int getAvailableWidth();

    /**
     * @return the full width of the content in the screen or input box
     */
    int getContentWidth();

}
