package html.Elements;

import gui.GUIObject;
import html.HtmlRenderer;

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
     * Returns the height of the object
     */
    @Override
    public int getHeight() {
        return text.getHeight();
    }

    /**
     * Returns the width of the object
     */
    @Override
    public int getWidth() {
        return text.getWidth();
    }

    /**
     * Render the Hyperlink object (add it to the DocGUIObjects list of the DocumentArea)
     *
     * Also call the render method on all its HtmlTableCells
     *
     * @param startX         x-coordinate
     * @param startY         y-coordinate
     * @param objects   the current DocGUIObjects of the DocumentArea
     * @return          the updated DocGUIObjects
     */
    @Override
    public ArrayList<GUIObject> render(int startX, int startY, ArrayList<GUIObject> objects) {
        HtmlRenderer.addGUILink(this.getText(), this.getHref(), startX, startY + getHeight(), objects );
        return objects;
    }
}
