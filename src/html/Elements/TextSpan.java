package html.Elements;

import gui.Objects.GUIObject;
import html.Creator;

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
     * Render the TextSpan object (add it to the DocGUIObjects list of the DocumentArea)
     *
     * @param objects   the current DocGUIObjects of the DocumentArea
     * @return          the updated DocGUIObjects

    @Override
    public ArrayList<GUIObject> render(ArrayList<GUIObject> objects) {
        HtmlRenderer.addGUIString(this.getText(), objects);
        return objects;
    }
     */

    @Override
    public ArrayList<GUIObject> create(Creator c){
        return c.create(this);
    }
}
