package html.Elements;

import gui.Objects.GUIObject;
import html.ContentSpanVisitor;

import java.util.ArrayList;

/**
 * A contentspan object
 */
public class ContentSpan {

    /**
     * Create the visible equivalent off this object
     * @param c The creator which will create the visible object
     * @return  A list off all created things
     */
    public ArrayList<GUIObject> create(ContentSpanVisitor c){
        return null;
    }

}
