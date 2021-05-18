package gui.Objects.ScrollBars;

public interface Scrollable {

    /**
     * @return x-coordinate of this scrollable
     */
    int getX();

    /**
     * @return y-coordinate of this scrollable
     */
    int getY();

    /**
     * @return height of this scrollable
     */
    int getHeight();

    /**
     * @return width of this scrollable
     */
    int getWidth();


    /**
     * @return xOffset of this scrollable
     */
    int getXOffset();

    /**
     * @param offset the new xOffset of the scrollable
     */
    void setXOffset(int offset);


    /**
     * @return return yOffset of this scrollable
     */
    int getYOffset();
    /**
     * @param offset the new YOffset of the scrollable
     */
    void setYOffset(int offset);


    /**
     * @return the width of the scrollable
     */
    int getAvailableWidth();
    /**
     * @return the height of the scrollable
     */
    int getAvailableHeight();


    /**
     * @return the width of the content of this scrollable
     */
    int getContentWidth();
    /**
     * @return the height of the content of this scrollable
     */
    int getContentHeight();

}
