package html.Elements;

import gui.GUIObject;
import html.GUIRenderer;

import java.util.ArrayList;

public class SubmitButton extends ContentSpan{

    public SubmitButton(){
    }

    @Override
    public ArrayList<GUIObject> create() {
        return GUIRenderer.create(this);
    }
}
