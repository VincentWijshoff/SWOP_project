package html.Elements;

import gui.Objects.GUIObject;
import html.ContentSpanVisitor;

import java.util.ArrayList;

public class SubmitButton extends ContentSpan{

    /**
     * constructor of the button
     */
    public SubmitButton(){
    }

    /**
     * Create the button
     * @param c The creator which will create the visible object
     * @return  THe visible button element
     */
    @Override
    public ArrayList<GUIObject> create(ContentSpanVisitor c){
        return c.createSubmitButton(this);
    }
}
