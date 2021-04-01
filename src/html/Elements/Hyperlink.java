package html.Elements;

import gui.Objects.GUIObject;
import html.Creator;

import java.util.ArrayList;

/**
 * Class representing an <a> tag
 */
public class Hyperlink extends ContentSpan {

    private String href;            //the href <a href="...">
    private final TextSpan text;    //the string representing the hyperlink
    private String address;

    /**
     * Creates an empty hyperlink
     */
    public Hyperlink(String address){
        this.href = "";
        this.text = new TextSpan("");
        this.address = address;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public ArrayList<GUIObject> create(Creator c){
        return c.create(this);
    }
}
