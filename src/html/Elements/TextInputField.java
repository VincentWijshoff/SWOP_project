package html.Elements;

import gui.GUIObject;
import html.Creator;
import html.GUIRenderer;

import java.util.ArrayList;

public class TextInputField extends ContentSpan{

    private String name;

    public TextInputField(){
    }

    public void setName(String s){
        this.name = s;
    }

    public String getName() {
        return name;
    }

    @Override
    public ArrayList<GUIObject> create(Creator c){
        return c.create(this);
    }
}
