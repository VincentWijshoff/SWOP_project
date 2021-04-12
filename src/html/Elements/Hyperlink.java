package html.Elements;

import gui.Objects.GUIObject;
import html.ContentSpanVisitor;

import java.util.ArrayList;

/**
 * Class representing an <a> tag
 */
public class Hyperlink extends ContentSpan {

    private String href;            //the href <a href="...">
    private final TextSpan text;    //the string representing the hyperlink

    /**
     * Creates an empty hyperlink
     */
    public Hyperlink(){
        this.href = "";
        this.text = new TextSpan("");
    }

    /**
     * Getter of the text parameter
     */
    public String getText() {
        return text.getText();
    }

    /**
     * Setter of the text parameter
     */
    public void setText(String text) {
        this.text.setText(text);
    }

    /**
     * Getter of the href parameter
     */
    public String getHref() {
        return href;
    }

    /**
     * Setter of the href parameter
     */
    public void setHref(String href) {
        this.href = href;
    }

    /**
     * Create a hyperlink
     * @param c The creator which will create the visible object
     */
    @Override
    public void accept(ContentSpanVisitor c){
        c.visitHyperlink(this);
    }
}
