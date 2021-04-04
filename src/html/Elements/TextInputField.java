package html.Elements;

import gui.Objects.GUIObject;
import html.Creator;

import java.util.ArrayList;

public class TextInputField extends ContentSpan{

    private String name; // the name off the input field

    /**
     * constructor off the iput field
     */
    public TextInputField(){
    }

    /**
     * Set the name off this input field
     * @param s the new name off this input field
     */
    public void setName(String s){
        this.name = s;
    }

    /**
     * Get the name off this input field
     * @return  The name off this input field
     */
    public String getName() {
        return name;
    }

    /**
     * Create this input field as visible object
     * @param c The creator which will create the visible object
     * @return  The visible input field
     */
    @Override
    public ArrayList<GUIObject> create(Creator c){
        return c.create(this);
    }
}
