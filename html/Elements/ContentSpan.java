package html.Elements;

import gui.GUIObject;

import java.util.ArrayList;

public class ContentSpan {
    /**
     * Get the height of the ContentSpan object
     */
    public int getHeight() {
       return 0;
    }

    /**
     * Get the width of the ContentSpan object
     */
    public int getWidth() {
        return 0;
    }

    /**
     * Render the ContentSpan object (add it to the DocGUIObjects list of the DocumentArea)
     *
     * @param x         x-coordinate
     * @param y         y-coordinate
     * @param objects   the current DocGUIObjects of the DocumentArea
     * @return          the updated DocGUIObjects
     */
    public ArrayList<GUIObject> render(int x, int y, ArrayList<GUIObject> objects) {
        return objects;
    }
}
