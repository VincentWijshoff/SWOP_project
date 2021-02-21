package html;

public class HtmlText extends HtmlElement{

    private String text;

    public HtmlText(){
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
