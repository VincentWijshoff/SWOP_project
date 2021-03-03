package html.Elements;


import gui.GUILink;
import gui.GUIObject;

import java.util.ArrayList;

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
        return text.getHeight();
    }

    @Override
    public int getWidth() {
        return text.getWidth();
    }

    @Override
    public ArrayList<GUIObject> render(int startX, int startY, ArrayList<GUIObject> objects) {
        objects.add(new GUILink(getText(), startX, startY + getHeight(), getHref()));
        return objects;
    }
}
