package html.Elements;

import gui.Objects.GUIObject;
import html.Creator;

import java.util.ArrayList;

public class ContentSpan {

    /**
     * Render the ContentSpan object (add it to the DocGUIObjects list of the DocumentArea)
     *
     * @param objects   the current DocGUIObjects of the DocumentArea
     * @return          the updated DocGUIObjects

    public ArrayList<GUIObject> render(ArrayList<GUIObject> objects) {
        return objects;
    }

    */

    public ArrayList<GUIObject> create(Creator c){
        return c.create(this);
    }

}
