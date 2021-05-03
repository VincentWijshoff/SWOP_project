package html.Elements;

public class Iframe extends ContentSpan{

    private String src;
    private int width;
    private int height;

    public Iframe(){}

    public void setHeight(int height) {
        this.height = height;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public void setWidth(int width) {
        this.width = width;
    }
}
