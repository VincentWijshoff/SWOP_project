package html;

public class HtmlText extends HtmlElement{

    private String text;

    public HtmlText(){
    }

    public HtmlText(String text) {
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
}
