package html.Elements;

import gui.Objects.GUIObject;
import html.Creator;

import java.util.ArrayList;

/**
 * A form object
 */
public class Form extends ContentSpan{
    //some needed parameters
    private String action;
    private ContentSpan data;

    /**
     * constructor
     */
    public Form(){
        data = null; //in case of an empty form
    }

    /**
     * Set the action off this form
     * @param action    the action off this form
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * Set the data off this form
     * @param data  The data off this form
     */
    public void setData(ContentSpan data) {
        this.data = data;
    }

    /**
     * Get the data off this form
     * @return  The data off this form
     */
    public ContentSpan getData() {
        return data;
    }

    /**
     * Create a form
     * @param c The creator which will create the visible object
     * @return  the list off elements that make up the form
     */
    @Override
    public ArrayList<GUIObject> create(Creator c){
        return c.create(this);
    }

    /**
     * Get the action off this form
     * @return  the action off this form
     */
    public String getAction() {
        return this.action;
    }
}
