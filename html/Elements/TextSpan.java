package html.Elements;

public class TextSpan extends ContentSpan {

    private String text;

    public TextSpan(){
    }

    public TextSpan(String text) {
        this.text = text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public int getHeight() {
        return 16;
    }

    @Override
    public int getWidth() {
        return 16*text.length();
    }
}
