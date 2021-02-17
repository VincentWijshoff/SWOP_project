package gui;

import java.awt.*;
import java.util.ArrayList;

public class DocumentArea {

    ArrayList<GUIObject> drawnObjects = new ArrayList<>();

    private int relativeYPos;

    /*
    * Class used to describe the entire Document section of our GUI.
     */
    public DocumentArea(GUI gui, int relativeYpos) {
        this.relativeYPos = relativeYpos;
    }

    public void paintDocArea(Graphics g){

    }

}

