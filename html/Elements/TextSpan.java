package html.Elements;

import gui.GUIObject;
import html.HtmlRenderer;

import java.util.ArrayList;

public class TextSpan extends ContentSpan {

    //Stores the corresponding text
    private String text;

    /**
     * creates an empty TextSpan object (no text yet)
     */
    public TextSpan(){
    }

    /**
     * creates a TextSpan object and initialise it with the given text
     */
    public TextSpan(String text) {
        this.text = text;
    }

    /**
     * Setter of the text parameter
     */
    public void setText(String text) {
        this.text = text;
    }

    /**
     * Getter of the text parameter
     */
    public String getText() {
        return text;
    }

    /**
     * Returns the height of the object
     */
    @Override
    public int getHeight() {
        return 16;
    }

    /**
     * Returns the width of the object
     */
    @Override
    public int getWidth() {
        return 16*text.length();
    }

    /**
     * Render the TextSpan object (add it to the DocGUIObjects list of the DocumentArea)
     *
     * @param startX         x-coordinate
     * @param startY         y-coordinate
     * @param objects   the current DocGUIObjects of the DocumentArea
     * @return          the updated DocGUIObjects
     */
    @Override
    public ArrayList<GUIObject> render(int startX, int startY, ArrayList<GUIObject> objects) {
        HtmlRenderer.addGUIString(this.getText(), startX, startY + getHeight(), objects);
        return objects;
    }
}
