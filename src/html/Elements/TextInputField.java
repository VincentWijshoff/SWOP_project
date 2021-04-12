package html.Elements;

import gui.Objects.GUIObject;
import html.ContentSpanVisitor;

import java.util.ArrayList;

public class TextInputField extends ContentSpan{

    private String name; // the name of the input field

    /**
     * constructor off the input field
     */
    public TextInputField(){
    }

    /**
     * Set the name of this input field
     * @param s the new name of this input field
     */
    public void setName(String s){
        this.name = s;
    }

    /**
     * Get the name of this input field
     * @return  The name of this input field
     */
    public String getName() {
        return name;
    }

    /**
     * Create this input field as visible object
     * @param c The creator which will create the visible object
     */
    @Override
    public void accept(ContentSpanVisitor c){
        c.visitTextInputField(this);
    }
}
