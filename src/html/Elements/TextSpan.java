package html.Elements;

import gui.Objects.GUIObject;
import html.ContentSpanVisitor;

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
     * Create this visible text object
     * @param c The creator which will create the visible object
     */
    @Override
    public void accept(ContentSpanVisitor c){
        c.visitTextSpan(this);
    }
}
