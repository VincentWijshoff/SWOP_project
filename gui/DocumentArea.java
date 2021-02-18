package gui;

import java.awt.*;
import java.util.ArrayList;

public class DocumentArea {

    ArrayList<GUIObject> drawnObjects = new ArrayList<>();

    private int relativeYPos;
    private GUI gui;

    /*
    * Class used to describe the entire Document section of our GUI.
     */
    public DocumentArea(GUI gui, int relativeYpos) {
        this.relativeYPos = relativeYpos;
        this.gui = gui;
    }

    public void paintDocArea(Graphics g){
        for (GUIObject obj: drawnObjects ) {
            obj.draw(g);
        }
    }

    public void draw(GUIObject obj) {
        drawnObjects.add(obj);
    }

}

