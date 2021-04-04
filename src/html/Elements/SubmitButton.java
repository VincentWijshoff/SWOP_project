package html.Elements;

import gui.Objects.GUIObject;
import html.Creator;

import java.util.ArrayList;

public class SubmitButton extends ContentSpan{

    /**
     * constructor off the button
     */
    public SubmitButton(){
    }

    /**
     * Create the button
     * @param c The creator which will create the visible object
     * @return  THe visible button element
     */
    @Override
    public ArrayList<GUIObject> create(Creator c){
        return c.create(this);
    }
}
