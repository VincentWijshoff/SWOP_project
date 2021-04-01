package html.Elements;

import gui.Objects.GUIObject;
import html.Creator;

import java.util.ArrayList;

public class ContentSpan {

    public ArrayList<GUIObject> create(Creator c){
        return c.create(this);
    }

}
