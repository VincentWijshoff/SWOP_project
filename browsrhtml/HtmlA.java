package browsrhtml;


/**
 * Class representing an <a> tag
 */
public class HtmlA {

    private String href; //the href <a href="...">
    private String text; //the string representing the hyperlink

    public HtmlA(){
        this.href = "";
        this.text = "";
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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
}
