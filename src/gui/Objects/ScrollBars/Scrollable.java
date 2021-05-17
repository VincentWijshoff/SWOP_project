package gui.Objects.ScrollBars;

public interface Scrollable {

    int getX();
    int getY();
    int getHeight();
    int getWidth();


    int getOffset(boolean horizontal);
    void setOffset(int offset, boolean horizontal);

    int getAvailableWidth();
    int getAvailableHeight();
    int getContentWidth();
    int getContentHeight();

}
