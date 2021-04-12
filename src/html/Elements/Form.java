package html.Elements;

import gui.Objects.GUIObject;
import html.ContentSpanVisitor;

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
     * Set the action of this form
     * @param action    the action of this form
     */
    public void setAction(String action) {
        this.action = action;
    }

    /**
     * Set the data of this form
     * @param data  The data of this form
     */
    public void setData(ContentSpan data) {
        this.data = data;
    }

    /**
     * Get the data of this form
     * @return  The data of this form
     */
    public ContentSpan getData() {
        return data;
    }

    /**
     * Create a form
     * @param c The creator which will create the visible object
     */
    @Override
    public void accept(ContentSpanVisitor c){
        c.visitForm(this);
    }

    /**
     * Get the action of this form
     * @return  the action of this form
     */
    public String getAction() {
        return this.action;
    }
}
