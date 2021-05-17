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


    int getOffset(boolean horizontal);
    void setOffset(int offset, boolean horizontal);


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
