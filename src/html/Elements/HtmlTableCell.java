package html.Elements;

import gui.Objects.GUIObject;
import html.Creator;

import java.util.ArrayList;

public class HtmlTableCell extends ContentSpan {

    private ContentSpan data; //The data of this object


    /**
     * Create a new HtmlTableCell object, and initialise it with the given row
     */
    public HtmlTableCell(){

    }

    /**
     * Setter of the data parameter
     */
    public void setData(ContentSpan data) {
        this.data = data;
    }

    /**
     * Getter of data parameter
     */
    public ContentSpan getData(){
        return this.data;
    }

    /**
     * Create this table
     * @param c The creator which will create the visible object
     * @return  A list off elements that make up for this table
     */
    @Override
    public ArrayList<GUIObject> create(Creator c){
        return c.create(this);
    }
}