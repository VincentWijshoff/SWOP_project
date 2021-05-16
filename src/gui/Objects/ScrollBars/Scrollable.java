package gui.Objects.ScrollBars;

public interface Scrollable {

    int getX();
    int getY();
    int getHeight();
    int getWidth();


    int getOffset();
    void setOffset(int offset);

    int getAvailableWidth();
    int getContentWidth();

}
