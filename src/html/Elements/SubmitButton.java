package html.Elements;

import gui.Objects.GUIObject;
import html.Creator;

import java.util.ArrayList;

public class SubmitButton extends ContentSpan{

    public SubmitButton(){
    }

    @Override
    public ArrayList<GUIObject> create(Creator c){
        return c.create(this);
    }
}
