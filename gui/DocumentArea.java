package gui;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

public class DocumentArea extends GUIObject {

    public Set<GUIObject> DocGUIObjects = new HashSet<>();
    private int relativeYPos;

    /*
    * Class used to describe the entire Document section of our GUI.
     */
    public DocumentArea(int relativeYpos) {
        super();

        this.relativeYPos = relativeYpos;
    }

    public void draw(Graphics g){

    }

}

