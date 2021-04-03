package html.Elements;

import gui.Objects.GUIObject;
import html.Creator;

import java.util.ArrayList;

public class Form extends ContentSpan{

    private String action;
    private ContentSpan data;

    public Form(){
        data = null; //in case of an empty form
    }

    public void setAction(String action) {
        this.action = action;
    }

    public void setData(ContentSpan data) {
        this.data = data;
    }

    public ContentSpan getData() {
        return data;
    }

    @Override
    public ArrayList<GUIObject> create(Creator c){
        return c.create(this);
    }

    public String getAction() {
        return this.action;
    }
}
