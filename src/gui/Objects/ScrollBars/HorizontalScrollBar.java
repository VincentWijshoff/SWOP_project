package gui.Objects.ScrollBars;

public class HorizontalScrollBar extends ScrollBar {

    public HorizontalScrollBar() {
        this.setScrollbarHeight(15);
        this.setSlider(new ScrollbarSlider(this, getSliderStartX(), getScrollbarCoordY()+1, this.getMaxSliderWidth(), sliderHeight));
    }


    public int calcMaxOffset() {
        return 0;
    }

    public void slide() {

    }

    public void draw() {

    }

    public static int sliderHeight = 13;
    public int getMaxSliderWidth() { return Math.max(this.getScrollbarWidth() - this.getBuffer()*2, 0); }

    // Defines the possible slide room (most left and right x-coords).
    public int getSliderStartX() { return this.getScrollbarCoordX() + getBuffer(); }
    public int getSliderEndX() { return this.getSliderStartX() + this.getMaxSliderWidth(); }
}