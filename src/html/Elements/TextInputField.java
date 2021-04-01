package html.Elements;

import gui.Objects.GUIObject;
import html.Creator;

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
