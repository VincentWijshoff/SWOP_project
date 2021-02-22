package html.Elements;


/**
 * Class representing an <a> tag
 */
public class Hyperlink extends ContentSpan {

    private String href; //the href <a href="...">
    private final TextSpan text; //the string representing the hyperlink

    public Hyperlink(){
        this.href = "";
        this.text = new TextSpan("");
    }

    public String getText() {
        return text.getText();
    }

    public void setText(String text) {
        this.text.setText(text);
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    /**
     * this method creates the hyperlink in the documentArea
     */
    public void createHyperlink(){
    }

    @Override
    public int getHeight() {
        return 16;
    }

}
